package br.epcb.principal.principal;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Leandro Bevilaqua on 10/08/2016.
 */

public class VideoDescriptionActivity extends BaseActivity {

    private static final String LOG_TAG = VideoDescriptionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_description);

        activateToolbarWithHomeEnabled();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btLogout) {

            clearSharedPreferences();
            callLoginActivity();

            return true;
        }

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
