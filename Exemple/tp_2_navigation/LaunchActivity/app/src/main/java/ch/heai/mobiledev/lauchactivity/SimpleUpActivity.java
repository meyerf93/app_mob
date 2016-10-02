package ch.heai.mobiledev.lauchactivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class SimpleUpActivity extends Activity implements SensorEventListener {

    private static final String TAG = SimpleUpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Hi from onCreate Simple up activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_up_activity);

        if(getActionBar() !=null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mSensorManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        onSimpleUpActivity();
    }

    protected void onSimpleUpActivity(){
        Button bt = (Button) findViewById(R.id.send_bt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendMessage();
            }
        });
    }

    protected void onSendMessage(){
        EditText tx = (EditText) findViewById(R.id.edit_text);

        Intent intent = new Intent();

        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/html");

        intent.putExtra(Intent.EXTRA_EMAIL, tx.getText());
        intent.putExtra(Intent.EXTRA_SUBJECT,"my app sibject");
        intent.putExtra(Intent.EXTRA_TEXT,"salut depuis une petite app");

        startActivity(intent);
    }

    @Override
    protected void onStart() {
        Log.v(TAG,"Hi from onStart Simple up activity");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.v(TAG,"Hi from onRestart Simple up activity");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.v(TAG,"Hi from onResume Simple up activity");

        if(mSensorManager !=null){
            mSensorManager.registerListener(this,mLightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.v(TAG,"Hi from onPause Simple up activity");

        if(mSensorManager !=null){
            mSensorManager.unregisterListener(this,mLightSensor);
        }

        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.v(TAG,"Hi from onStop Simple up activity");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG,"Hi from onDestroy Simple up activity");
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.v(TAG,"Hi from onSaveInst Simple up activity");
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.v(TAG,"Hi from onRestoreInst Simple up activity");
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
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

   @Override
    public void onBackPressed(){
        Log.v(TAG,"hi from onBackPressed");

        Intent intent = getParentActivityIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    // code pour le capteur de lux ----------------------------------------------------------
    private SensorManager mSensorManager;
    private Sensor mLightSensor;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == mLightSensor){
            int luxValue = (int) event.values[0];

            Button lightButton = (Button) findViewById(R.id.btnLight);
            final int maxLuxValue = 200;

            int value = Math.min(255,(luxValue*255)/maxLuxValue);
            int color = (0xff << 24) | (value << 16) | (value <<8) |(value);
            lightButton.setBackgroundColor(color);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if(sensor == mLightSensor){
            Log.d(TAG, "Accuracy of light sensor has changed: " + accuracy);
        }
    }

    private DownloadAsyncTask mDownloadAsyncTask;
    private DownloadServiceResponseReceiver mDownloadStateReceiver;
    private IntentFilter mStatusIntentFilter;

    //-----------------------------------------------------------------------------------------
     SimpleService
    public void onStartService(View view){
        Log.v(TAG,"onStartService");
        Intent serviceIntent = new Intent(SimpleUpActivity.this,SimpleService.class);
        startService(serviceIntent);
    }

    public void onStopService(View view){
        Log.v(TAG,"onStopService");
        Intent serviceIntent = new Intent(SimpleUpActivity.this,SimpleService.class);
        stopService(serviceIntent);
    }
}
