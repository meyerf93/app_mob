package ch.heai.mobiledev.navigation;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class OutsideTaskActivity extends Activity {

    private static final String TAG = OutsideTaskActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "hi from onCreate, Meyer & Metraux, nexus 5");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.outside_task_activity);
    }

    @Override
    protected void onStart() {
        Log.v(TAG,"hi from onstart, Meyer & Metraux, nexus 5");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.v(TAG,"hi from onRestart, Meyer & Metraux, nexus 5");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.v(TAG,"hi from onResume, Meyer & Metraux, nexus 5");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.v(TAG,"hi from onPause, Meyer & Metraux, nexus 5");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.v(TAG,"hi from onStop, Meyer & Metraux, nexus 5");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG,"hi from onDestroy, Meyer & Metraux, nexus 5");
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.v(TAG,"hi from onSaveInstanceState, Meyer & Metraux, nexus 5");
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.v(TAG,"hi from onRestoreInstanceState, Meyer & Metraux, nexus 5");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG, "hi from onCreateOptionsMenu, Meyer & Metraux, nexus 5");
        return true;
    }

   /* public void onBackPressed() {
        Log.d(TAG, "hi from onBackPressed, Meyer & Metraux, nexus 5");
        Intent intent =  getParentActivityIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        startActivity(intent);
    }*/
}
