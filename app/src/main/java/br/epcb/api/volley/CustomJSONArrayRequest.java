package br.epcb.api.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class CustomJSONArrayRequest extends JsonArrayRequest {

    private static String LOG_TAG = "jsonParams";

    public CustomJSONArrayRequest(int method, String url, String requestBody,
                                   Response.Listener<JSONArray> listener,
                                   Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);

        //Log.v(LOG_TAG, "ERRO: CustomJSONObjectRequestCustomJSONObjectRequest");
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        //headers.put("HTWeb-Token", "HTWebKEY-WCF-XXX");
        return headers;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        // here you can write a custom retry policy
        return super.getRetryPolicy();
    }
}