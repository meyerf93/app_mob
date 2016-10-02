package ch.heia.mobiledev.treasurehunt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class HelpScreenActivity extends Activity {
    private static final String TAG = HelpScreenActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.help_screen));
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void onStart() {
        Log.v(TAG, "Hi from onStart HelpScreen activity");
        super.onStart();
    }


    @Override
    protected void onResume() {
        Log.v(TAG, "Hi from onResume HelpScreen activity");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.v(TAG, "Hi from onPause HelpScreen activity");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, "Hi from onDestroy HelpScreen activity");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.v(TAG, "Hi from onRestart HelpScreen activity");
        super.onRestart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.v(TAG, "Hi from onSaveInst HelpScreen activity");
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.v(TAG, "Hi from onRestoreInst HelpScreen activity");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v(TAG, "hi from onOptionsItem");

        switch (item.getItemId()){
            case android.R.id.home :
                Intent intent = getParentActivityIntent();
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}