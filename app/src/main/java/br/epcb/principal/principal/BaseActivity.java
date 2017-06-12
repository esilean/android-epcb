package br.epcb.principal.principal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    public static final String MODULE_TRANSFER = "MODULE_TRANSFER";
    public static final String COURSE_TRANSFER = "COURSE_TRANSFER";
    public static final String VIDEO_TRANSFER = "VIDEO_TRANSFER";

    protected Toolbar activateToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.app_bar);
            if (mToolbar != null)
                setSupportActionBar(mToolbar);
        }

        return mToolbar;
    }

    protected Toolbar activateToolbarWithHomeEnabled() {
        activateToolbar();
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return mToolbar;

    }

    protected void callLoginActivity() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    protected void callModuleActivity() {

        Intent intent = new Intent(this, ModuleActivity.class);
        startActivity(intent);
        finish();
    }

    protected void clearSharedPreferences() {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("UN_EPCB", "");
        editor.putString("UP_EPCB", "");
        editor.putString("CD_EPCB", "");
        editor.apply();
    }

}
