package ch.heai.mobiledev.lauchactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class LaunchActivity extends Activity {
    private static final String TAG = LaunchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);

        Log.v(TAG, "Hi from onCreate launch activity");

        onLaunchActivity();

    }

    protected void onStart() {
        Log.v(TAG, "Hi from onStart launch activity");
        super.onStart();
    }


    @Override
    protected void onResume() {
        Log.v(TAG, "Hi from onResume launch activity");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.v(TAG, "Hi from onPause launch activity");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, "Hi from onDestroy launch activity");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.v(TAG, "Hi from onRestart launch activity");
        super.onRestart();
    }

    public void onLaunchActivity() {

        Log.v(TAG, "hi from onLaunchActivity launch activity");

        final Button launch_button = (Button) findViewById(R.id.launch_button);
        launch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mClass = ".SimpleUpActivity";
                /*Intent launchIntent = new Intent(this,SimpleUpActivity.class);
                startActivity(launchIntent);*/

                Intent launchIntent = new Intent();
                launchIntent.setClassName(getApplicationContext(), getPackageName() + mClass);
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(launchIntent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.v(TAG, "Hi from onSaveInst launch activity");
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.v(TAG, "Hi from onRestoreInst launch activity");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

}
