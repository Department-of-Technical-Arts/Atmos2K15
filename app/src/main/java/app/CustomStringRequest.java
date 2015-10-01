package app;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by user on 12-04-2015.
 */
public class CustomStringRequest extends StringRequest {
    private Priority priority = Priority.LOW;

    public CustomStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public CustomStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }


    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}

