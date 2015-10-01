package harsu.atmos2k15.atmos.com.atmos2k15.services;

import android.app.IntentService;
import android.content.Intent;
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
                    JSONArray array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        //todo update updated time
                        tableManager.updateSchedule(object.getInt("Event_id"), object.getLong("Start_time"), object.getString("venue"));
                        scheduleTableManager.addEntry(object.getInt("Event_id"),object.getInt("tag"),object.getString("Event_Name"),object.getLong("Start_time")*1000,object.getString("venue"));
                    }
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
                temp.put("check_time", "0");
                //todo put time
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