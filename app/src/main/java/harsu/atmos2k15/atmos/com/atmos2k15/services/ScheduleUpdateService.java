package harsu.atmos2k15.atmos.com.atmos2k15.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.AppConfig;
import app.ControllerConstants;
import app.VolleySingleton;
import harsu.atmos2k15.atmos.com.atmos2k15.adapter.ScheduleAdapter;
import helper.EventTableManager;
import helper.ScheduleTableManager;


public class ScheduleUpdateService extends IntentService {

    private ResultReceiver mReceiver;

    public ScheduleUpdateService() {
        super("ScheduleUpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            mReceiver = intent.getParcelableExtra(AppConfig.RECEIVER);

        }
        sendRequest();
    }

    private void sendRequest() {
        final EventTableManager tableManager = new EventTableManager(this);
        final ScheduleTableManager scheduleTableManager=new ScheduleTableManager(this);
        StringRequest request = new StringRequest(Request.Method.POST, ControllerConstants.URL_Events, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("Schedule.class", s);
                try {
                    Long updatedAt=0l;
                    JSONArray array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        //todo update updated time
                       if(object.getLong("updated_at")>updatedAt)
                           updatedAt=object.getLong("updated_at");
                        tableManager.updateSchedule(object.getInt("Event_id"), object.getLong("Start_time"), object.getString("venue"));
                        scheduleTableManager.addEntry(object.getInt("Event_id"),object.getInt("tag"),object.getString("Event_Name"),object.getLong("Start_time")*1000,object.getString("venue"));
                    }
                    SharedPreferences preferences=getApplicationContext().getSharedPreferences(AppConfig.PACKAGE_NAME,MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putLong(AppConfig.LastUpdated, updatedAt);
                    deliverResultToReceiver(1, "Refreshed");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                deliverResultToReceiver(0, "Check Internet Connection");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> temp = new HashMap<>();
                temp.put("tag", "check_time");
                SharedPreferences prefs=getApplicationContext().getSharedPreferences(AppConfig.PACKAGE_NAME,MODE_PRIVATE);
                temp.put("check_time", Long.toString(prefs.getLong(AppConfig.LastUpdated,0l)));
                return temp;
            }
        };
        VolleySingleton.getInstance().getRequestQueue().add(request);
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString("1", message);

        if (mReceiver != null)
            mReceiver.send(resultCode, bundle);
    }

}