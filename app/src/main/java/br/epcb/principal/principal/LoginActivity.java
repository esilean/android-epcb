package br.epcb.principal.principal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import br.epcb.api.volley.CustomJSONCall;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;


public class LoginActivity extends BaseActivity {

    SharedPreferences prefs;

    private String mUsername = "";
    private String mPassword = "";
    private boolean mAuthenticated = false;

    // UI references.
    private View mLoginForm;
    private EditText tvEmail;
    private EditText tvPassword;
    private View mProgressView;
    private View mUserFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        tvEmail = (EditText) findViewById(R.id.tvEmail);
        tvPassword = (EditText) findViewById(R.id.tvPassword);
        tvPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginForm = findViewById(R.id.loginForm);
        mUserFormView = findViewById(R.id.userForm);
        mProgressView = findViewById(R.id.login_progress);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        tvEmail.setError(null);
        tvPassword.setError(null);

        // Store values at the time of the login attempt.
        mUsername = tvEmail.getText().toString();
        mPassword = tvPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(mPassword)) {
            tvPassword.setError(getString(R.string.errorEmptyPassword));
            focusView = tvPassword;
            cancel = true;
        }

        if (!TextUtils.isEmpty(mPassword) && !isPasswordValid(mPassword)) {
            tvPassword.setError(getString(R.string.errorIncorrectPassword));
            focusView = tvPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mUsername)) {
            tvEmail.setError(getString(R.string.errorEmptyEmail));
            focusView = tvEmail;
            cancel = true;
        } else if (!isEmailValid(mUsername)) {
            tvEmail.setError(getString(R.string.errorInvalidEmail));
            focusView = tvEmail;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(tvPassword.getWindowToken(), 0);

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put("username", mUsername);
            parameters.put("password", mPassword);

            ProcessEPCBLogin processEPCBLogin = new ProcessEPCBLogin(this);
            processEPCBLogin.execute("userapp/login", Request.Method.POST, parameters);

        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return (password.length() > 1);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mUserFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mUserFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mUserFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mUserFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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
            showProgress(false);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            super.onErrorResponse(error);
            //Log.e(LOG_TAG, "JSON Error: " + error.getMessage());
            showProgress(false);
            tvEmail.setError("E-Mail e/ou Senha Incorreto.");
            tvPassword.setError("E-Mail e/ou Senha Incorreto.");
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
                final String EPCB_CREATIONDATE = "creationDate";

                JSONArray itemsArray = (JSONArray) response;
                JSONObject jsonEPCB = itemsArray.getJSONObject(0);

                mAuthenticated = jsonEPCB.getBoolean(EPCB_AUTH);
                String sCreationDate = jsonEPCB.getString(EPCB_CREATIONDATE);

                showProgress(false);

                if (mAuthenticated) {

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("UN_EPCB", mUsername);
                    editor.putString("UP_EPCB", mPassword);
                    editor.putString("CD_EPCB", sCreationDate);
                    editor.apply();

                    callModuleActivity();
                } else {

                    tvEmail.setError("E-Mail e/ou Senha Incorreto.");
                    tvPassword.setError("E-Mail e/ou Senha Incorreto.");
                }


            } catch (Exception jsone) {
                //Log.e(LOG_TAG, "JSON Error: > " + jsone.getMessage());
            }
        }
    }
}

