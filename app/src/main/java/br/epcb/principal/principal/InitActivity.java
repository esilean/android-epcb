package br.epcb.principal.principal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import br.epcb.api.volley.CustomJSONCall;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class InitActivity extends BaseActivity {

    SharedPreferences prefs;

    private boolean mAuthenticated = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_init);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        letsRockBabe();

    }

    private void letsRockBabe() {
        // Download de data

        String username = prefs.getString("UN_EPCB", "");
        String password = prefs.getString("UP_EPCB", "");

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("username", username);
        parameters.put("password", password);

        ProcessEPCBLogin processEPCBLogin = new ProcessEPCBLogin(this);
        processEPCBLogin.execute("userapp/login", Request.Method.POST, parameters);

        //Log.v(LOG_TAG, "EPCBLogin processed.");
    }

    public class ProcessEPCBLogin extends CustomJSONCall {

        public ProcessEPCBLogin(Context context) {
            super(context);
        }

        @Override
        public void execute(String functionName, int method, HashMap<String, String> parameters) {
            super.execute(functionName, method, parameters);
        }

        @Override
        public void cancel() {
            super.cancel();
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);

            //Chama a login
            callLoginActivity();

            //Log.e(LOG_TAG, "JSON Error: " + error.getMessage());
        }

        @Override
        public void onResponse(Object response) {
            super.onResponse(response);

            getJSONAuth(response);

        }

        private void getJSONAuth(Object response) {
            try {

                //final String EPCB_USERNAME = "username";
                final String EPCB_AUTH = "authenticated";

                JSONArray itemsArray = (JSONArray) response;
                JSONObject jsonEPCB = itemsArray.getJSONObject(0);

                mAuthenticated = jsonEPCB.getBoolean(EPCB_AUTH);

                SystemClock.sleep(2000);

                if (mAuthenticated)
                    callModuleActivity();
                else
                    callLoginActivity();

            } catch (Exception jsone) {
                //Log.e(LOG_TAG, "JSON Error: > " + jsone.getMessage());
            }
        }
    }
}
