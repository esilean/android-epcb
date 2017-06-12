package br.epcb.api.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by leandro.bevilaqua on 07/03/2016.
 */
public class CustomJSONCall implements Response.Listener, Response.ErrorListener {

    public static final String LOG_TAG = CustomJSONCall.class.getSimpleName();
    public static final String REQUEST_TAG = "CustomJSONCall";
    public static final String mMemberAreaApiRest = "http://vcemec.com/api/v1/";

    private RequestQueue mRequestQueue;
    private Context mContext;
    private CustomJSONArrayRequest jsonArrayRequest;

    public CustomJSONCall(Context context) {
        mContext = context;
    }

    public void execute(String functionName, int method, HashMap<String, String> parameters) {
        String url;

        mRequestQueue = CustomVolleyRequestQueue.getInstance(mContext).getRequestQueue();

        url = mMemberAreaApiRest + functionName;
        //Log.v(LOG_TAG, "JSON URL called: " + url);

        if (jsonArrayRequest == null)
            jsonArrayRequest = new CustomJSONArrayRequest(method, url, new JSONObject(parameters).toString(), this, this);
        jsonArrayRequest.setTag(REQUEST_TAG);

        if (mRequestQueue != null)
            mRequestQueue.add(jsonArrayRequest);

        //Log.v(LOG_TAG, "JSON Request was initialized");
    }

    public void execute(String functionName, int method) {
        String url;

        mRequestQueue = CustomVolleyRequestQueue.getInstance(mContext).getRequestQueue();

        url = mMemberAreaApiRest + functionName;
        //Log.v(LOG_TAG, "JSON URL called: " + url);

        if (jsonArrayRequest == null)
            jsonArrayRequest = new CustomJSONArrayRequest(method, url, null, this, this);
        jsonArrayRequest.setTag(REQUEST_TAG);

        if (mRequestQueue != null)
            mRequestQueue.add(jsonArrayRequest);

        //Log.v(LOG_TAG, "JSON Request was initialized");
    }


    public void cancel() {
        if (mRequestQueue != null)
            mRequestQueue.cancelAll(REQUEST_TAG);

        //Log.v(LOG_TAG, "JSON Request was cancelled");
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        //Log.e(LOG_TAG, "JSON Error: " + error.getMessage());
    }

    @Override
    public void onResponse(Object response) {
        //Log.i(LOG_TAG, "Response: " + response);
    }
}
