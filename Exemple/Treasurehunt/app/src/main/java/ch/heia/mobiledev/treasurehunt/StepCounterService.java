package ch.heia.mobiledev.treasurehunt;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

public class StepCounterService extends Service implements SensorEventListener {

    public static final String ACTION_STEP_COUNTER = "ch.heia.mobiledev.treasurehunt.StepCounterService.action.STEP";

    private SensorManager mSensorManager;
    private Sensor mStepSensor;

    // used for logging
    private static final String TAG = StepCounterService.class.getSimpleName();

    private int CurrentStep;
    private int TotalStep;

    @Override
    public void onCreate() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mStepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        Log.d(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(TAG, "onStartCommand");
        if(mSensorManager != null){
            mSensorManager.registerListener(this,mStepSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }

        int temp_total = 0;
        int temp_current = 0;
        if (intent != null){
            temp_total = intent.getIntExtra("TotalStep",0);
            temp_current = intent.getIntExtra("CurrentStep",0);
        }
        if(temp_current!=-1) CurrentStep = temp_current;
        if (temp_total!=-1) TotalStep = temp_total;

        // see http://android-developers.blogspot.ch/2010/02/service-api-changes-starting-with.html
        // for a good explanation on what the return code means
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // we do not allow binding
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // should not be called
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        mSensorManager.unregisterListener(this,mStepSensor);
        mSensorManager = null;

        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "onSensorChanged()");
        if(event.sensor == mStepSensor){
            CurrentStep++;
            TotalStep++;

            Intent retIntent = new Intent(ACTION_STEP_COUNTER);
            retIntent.putExtra("CurrentStep",CurrentStep);
            retIntent.putExtra("TotalStep",TotalStep);
            sendBroadcast(retIntent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if(sensor == mStepSensor){
            Log.d(TAG, "Accuracy of step sensor has changed: " + accuracy);
        }
    }
}
