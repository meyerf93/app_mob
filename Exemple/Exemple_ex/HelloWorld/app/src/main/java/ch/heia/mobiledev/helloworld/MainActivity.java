package ch.heia.mobiledev.helloworld;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity implements SensorEventListener
{
  // data members
  private SensorManager mSensorManager;
  private Sensor mLightSensor;
  private final static String TAG = MainActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Point realSize = new Point();
    getWindowManager().getDefaultDisplay().getRealSize(realSize);

    Log.d("HelloWorld", "Screen real size (without scale factor) is " + realSize.x + " x " + realSize.y);

    // The DisplayMetrics.density field specifies the scale factor
    // one must use to convert dp units to pixels,
    DisplayMetrics displayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    float densityPxPerDp = displayMetrics.density;
    float densityPxPerSp = displayMetrics.scaledDensity;
    float densityBucketDpi = displayMetrics.densityDpi;
    float xdpi = displayMetrics.xdpi;
    float ydpi = displayMetrics.ydpi;
    int displayWidth = displayMetrics.widthPixels;
    int displayHeight = displayMetrics.heightPixels;
    float dpWidth  = displayWidth / densityPxPerDp;
    float dpHeight = displayHeight / densityPxPerDp;

    Log.d("HelloWorld", "xdpi is " + xdpi + " and ypdi is " + ydpi);
    Log.d("HelloWorld", "Screen density bucket is " + densityBucketDpi + " dpi");
    Log.d("HelloWorld", "Screen density in px per dp is " + densityPxPerDp + " px/dp");
    Log.d("HelloWorld", "Screen density in px per sp is " + densityPxPerSp + " px/sp");
    Log.d("HelloWorld", "Screen size is " + displayWidth + " px x " + displayHeight + " px");
    Log.d("HelloWorld", "dpWidth in dp is " + dpWidth + " dp");
    Log.d("HelloWorld", "dpHeight in dp is " + dpHeight + " dp");
    Log.d("HelloWorld", "Screen width in dp (from resource qualifier) is " + getResources().getConfiguration().screenWidthDp + " dp");
    Log.d("HelloWorld", "Screen height in dp (from resource qualifier) is " + getResources().getConfiguration().screenHeightDp + " dp");
    Log.d("HelloWorld", "ratio " + densityBucketDpi/xdpi + " ratio " + dpWidth/getResources().getConfiguration().screenWidthDp );
    Log.d("HelloWorld", "ratio " + densityBucketDpi / ydpi + " ratio " + dpHeight / getResources().getConfiguration().screenHeightDp);

    initDisplayButton();
    initClearButton();

    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    return false;
  }

  private void initDisplayButton()
  {
    Button displayButton = (Button) findViewById(R.id.displayButton);
    displayButton.setOnClickListener(new View.OnClickListener()
                                     {
                                       @Override
                                       public void onClick(View arg)
                                       {
                                         EditText editName = (EditText) findViewById(R.id.editText);
                                         TextView textDisplay = (TextView) findViewById(R.id.textHello);
                                         String newText = String.format(getResources().getString(R.string.hello_format), editName.getText().toString());
                                         textDisplay.setText(newText);
                                       }
                                     }
    );
  }

  private void initClearButton()
  {
    Button clearButton = (Button) findViewById(R.id.clearButton);
    clearButton.setOnClickListener(new View.OnClickListener()
                                   {
                                     @Override
                                     public void onClick(View arg)
                                     {
                                       EditText editName = (EditText) findViewById(R.id.editText);
                                       editName.setText("");

                                       TextView textDisplay = (TextView) findViewById(R.id.textHello);
                                       String nameToDisplay = getResources().getString(R.string.hello_world);
                                       textDisplay.setText(nameToDisplay);
                                     }
                                   }
    );
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy)
  {
    if (sensor == mLightSensor)
    {
      Log.d(TAG, "Accuracy changed for light sensor: " + accuracy);
    }
  }

  @Override
  public void onSensorChanged(SensorEvent event)
  {
    if (event.sensor == mLightSensor)
    {
      Log.d(TAG, "New value received for light sensor: " + event.values[0] + "  SI lux");
    }
  }
}
