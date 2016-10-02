package ch.heia.mobiledev.launchactivity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.FragmentManager;
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
import java.text.NumberFormat;
import java.util.List;


public class SimpleUpActivity extends Activity
  implements SensorEventListener,
             DownloadTaskFragment.TaskCallbacks
{
  // used for logging
  private final static String TAG = SimpleUpActivity.class.getSimpleName();

  // data members used for the light sensor
  private SensorManager mSensorManager;
  private Sensor mLightSensor;

  // url used for download
  // private final String mUrlPath = "http://s3-eu-west-1.amazonaws.com/dartfishtveurope/IAK1myXlN2YafEYSsA1wz6_q11iWNqLGrdxjVrv7HsVl3_150108142836574/KZHYSGn7irSRnzo4rBoez1.mp4";
  // private final String mUrlPath = "http://s3-eu-west-1.amazonaws.com/dartfishtveurope/qWnIz3X7IjawVtH3EneRf4_ai2xC8ELcsoLtdF2gILth6_140213103100972/kk92q6ANOegkRWsN6CJhB4.mp4";
  private final String mUrlPath = "http://s3-eu-west-1.amazonaws.com/dartfishtveurope/sW5NyldXa0tYQoE1Qo9wE6_37YFgZrHiI5fN60MuMNjX6_160420215404841/WFffVTnXhYuvsVyEFX84X0.mp4";

  // data members used for performing download with an async task
  private DownloadAsyncTask mDownloadAsyncTask;

  // data members used for performing download with a service
  private DownloadServiceResponseReceiver mDownloadStateReceiver;
  private IntentFilter mStatusIntentFilter;

  // data members used with the download task fragment
  private static final String TAG_DOWNLOAD_TASK_FRAGMENT = "download_task_fragment";
  private static final String TAG_DOWNLOAD_CURRENT_PROGRESS = "CurrentProgress";
  private DownloadTaskFragment mDownloadTaskFragment;
  private int mProgress = 0;

  // datam members used for services
  private Intent mServiceIntent;

  // data members used for handling permissions
  private static final int PERMISSIONS_FOR_DOWNLOAD_WITH_TASK = 0;
  private static final int PERMISSIONS_FOR_DOWNLOAD_WITH_TASK_FRAGMENT = 1;
  private static final int PERMISSIONS_FOR_DOWNLOAD_WITH_SERVICE = 2;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    Log.d(TAG, "onCreate()");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_simple_up);

    if (getActionBar() != null)
    {
      getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // create the sensor manager for instantiating the light sensor and for event listener registration
    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    // create the sensor instance
    mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    // used for registering to service broadcasts
    // The filter's action is ACTION_DONE
    mStatusIntentFilter = new IntentFilter();
    mStatusIntentFilter.addAction(DownloadService.ACTION_DONE);
    mStatusIntentFilter.addAction(DownloadService.ACTION_PROGRESS);
    mStatusIntentFilter.addAction(DownloadService.ACTION_STARTED);

    // Instantiates a new DownloadStateReceiver
    mDownloadStateReceiver = new DownloadServiceResponseReceiver();

    // Registers the DownloadStateReceiver and its intent filters
    LocalBroadcastManager.getInstance(this).registerReceiver(mDownloadStateReceiver, mStatusIntentFilter);

    // create the fragment owning the AsyncTask
    FragmentManager fragmentManager = getFragmentManager();
    mDownloadTaskFragment = (DownloadTaskFragment) fragmentManager.findFragmentByTag(TAG_DOWNLOAD_TASK_FRAGMENT);

    // If the Fragment is non-null, then it is currently being
    // retained across a configuration change.
    // otherwise, we need to create it
    if (mDownloadTaskFragment == null)
    {
      mDownloadTaskFragment = new DownloadTaskFragment();
      fragmentManager.beginTransaction().add(mDownloadTaskFragment, TAG_DOWNLOAD_TASK_FRAGMENT).commit();
    }
    // restore current download and progress
    if (savedInstanceState != null)
    {
      Log.d(TAG, "Restoring state");
      mProgress = savedInstanceState.getInt(TAG_DOWNLOAD_CURRENT_PROGRESS, 0);
      if (mProgress == 0)
      {
        TextView progressText = (TextView) findViewById(R.id.downloadProgress);
        progressText.setText(R.string.download_not_started);
      }
      else
      {
        onProgressUpdate(mProgress);
      }
    }
    else
    {
      TextView progressText = (TextView) findViewById(R.id.downloadProgress);
      progressText.setText(R.string.download_not_started);
    }
  }

  @Override
  protected void onStart()
  {
    Log.d(TAG, "onStart()");
    super.onStart();
  }

  @Override
  protected void onResume()
  {
    Log.d(TAG, "onResume()");

    // register for light sensor events
    if (mSensorManager != null)
    {
      mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    super.onResume();
  }

  @Override
  protected void onRestart()
  {
    Log.d(TAG, "onRestart()");
    super.onRestart();
  }

  @Override
  protected void onPause()
  {
    Log.d(TAG, "onPause()");

    // unregister for light sensor events
    if (mSensorManager != null)
    {
      mSensorManager.unregisterListener(this, mLightSensor);
    }

    super.onPause();
  }

  @Override
  protected void onStop()
  {
    Log.d(TAG, "onStop()");
    super.onStop();
  }

  @Override
  protected void onDestroy()
  {
    Log.d(TAG, "onDestroy()");
    mSensorManager = null;

    if (mDownloadAsyncTask != null)
    {
      mDownloadAsyncTask.cancel(true);

      mDownloadAsyncTask = null;
    }

    if (mDownloadStateReceiver != null)
    {
      LocalBroadcastManager.getInstance(this).unregisterReceiver(mDownloadStateReceiver);
      mDownloadStateReceiver = null;
    }
    
    super.onDestroy();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig)
  {
    Log.d(TAG, "onConfigurationChanged()");
    super.onConfigurationChanged(newConfig);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState)
  {
    Log.d(TAG, "onSaveInstanceState()");
    super.onSaveInstanceState(outState);

    // save the current download progress
    outState.putInt(TAG_DOWNLOAD_CURRENT_PROGRESS, mProgress);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState)
  {
    Log.d(TAG, "onRestoreInstanceState()");
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    return false;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch (item.getItemId())
    {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        try
        {
          Intent upIntent = getParentActivityIntent();
          if (upIntent != null)
          {
            upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (shouldUpRecreateTask(upIntent))
            {
              Log.v(TAG, "recreateTask");

              // This activity is NOT part of this app's task, so create a new task
              // when navigating up, with a synthesized back stack.
              TaskStackBuilder.create(this)
                // Add all of this activity's parents to the back stack
                .addNextIntentWithParentStack(upIntent)
                  // Navigate up to the closest parent
                .startActivities();
            }
            else
            {
              Log.v(TAG, "navigateUp");
              // This activity is part of this app's task, so simply
              // navigate up to the logical parent activity.
              navigateUpTo(upIntent);
            }
          }
        }
        catch (NullPointerException e)
        {
          Log.d(TAG, "NullPointerException: " + e.getMessage());
        }
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  // handler for click on button Send
  public void onSendMessage(View v)
  {
    // get the email address from the edit text
    EditText toAddress = (EditText) findViewById(R.id.textAddress);

    // create the implicit intent
    Intent intent = new Intent();

    // set the action and type
    intent.setAction(Intent.ACTION_SEND);
    intent.setType("text/html");

    // set pararameters as extra
    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { toAddress.getText().toString() });
    intent.putExtra(Intent.EXTRA_SUBJECT, "Important");
    intent.putExtra(Intent.EXTRA_TEXT, "Message de mon application");

    // launch the activity
    startActivity(intent);
    // startActivity(Intent.createChooser(intent, "Send Email"));
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy)
  {
    if (sensor == mLightSensor)
    {
      Log.d(TAG, "Accuracy of light sensor has changed: " + accuracy);
    }
  }

  @Override
  public void onSensorChanged(SensorEvent event)
  {
    if (event.sensor == mLightSensor)
    {
      // get the light sensor value (in SI Lux)
      int luxValue = (int) event.values[0];

      // modify the color of the button based on the value
      Button lightButton = (Button) findViewById(R.id.btnLight);
      final int maxLuxValue = 200;
      // 0xFF = 255 for maxLuxValue, 0x00 for 0
      int value = Math.min(255, (luxValue * 255)/ maxLuxValue);
      int color = (0xFF << 24) | (value << 16) | (value << 8) | (value);
      // Log.d(TAG, "New sensor value for light: " + luxValue + " SI lux -> color is 0x" + Integer.toHexString(color));
      lightButton.setBackgroundColor(color);
    }
  }

  // handler for click on button DownloadWithTask
  public void onDownloadWithTask(View v)
  {
    // make sure that permissions for writing to external storage are granted
    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
    {
      checkPermissions(PERMISSIONS_FOR_DOWNLOAD_WITH_TASK);
    }
    else
    {
      performDownloadWithTask();
    }
  }

  // handler for click on button DownloadWithTask
  public void onDownloadWithTaskFragment(View v)
  {
    // make sure that permissions for writing to external storage are granted
    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
    {
      checkPermissions(PERMISSIONS_FOR_DOWNLOAD_WITH_TASK_FRAGMENT);
    }
    else
    {
      performDownloadWithTaskFragment();
    }
  }

  // handler for click on button DownloadWithService
  public void onDownloadWithService(View v)
  {
    // make sure that permissions for writing to external storage are granted
    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
    {
      checkPermissions(PERMISSIONS_FOR_DOWNLOAD_WITH_SERVICE);
    }
    else
    {
      performDownloadWithService();
    }
  }

  private void checkPermissions(final int flag)
  {
    // Should we show an explanation?
    if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
    {
      // Show an explanation to the user *asynchronously* -- don't block
      // this thread waiting for the user's response! After the user
      // sees the explanation, try again to request the permission.
      showMessageOKCancel(getResources().getString(R.string.permission_explanation),
        new DialogInterface.OnClickListener()
        {
          @Override
          public void onClick(DialogInterface dialog, int which)
          {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, flag);
          }
        });
    }
    else
    {
      // No explanation needed, we can request the permission.
      requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, flag);
      // flag is an app-defined int constant. The callback method gets the result of the request.
    }
  }

  // handler for click on StartService
  public void onStartService(View view)
  {
    SimpleService.shouldContinue = true;
    Intent serviceIntent = new Intent(SimpleUpActivity.this, SimpleService.class);
    startService(serviceIntent);
  }

  // handler for click on StopService
  public void onStopService(View view)
  {
    SimpleService.shouldContinue = false;
    Intent serviceIntent = new Intent(SimpleUpActivity.this, SimpleService.class);
    stopService(serviceIntent);
  }

  private void performDownloadWithTask()
  {
    // use asyncTask
    mDownloadAsyncTask = new DownloadAsyncTask();
    mDownloadAsyncTask.execute(mUrlPath);
  }

  private void performDownloadWithTaskFragment()
  {
    // use asyncTask
    mDownloadTaskFragment.start(mUrlPath);
  }

  private void performDownloadWithService()
  {
    final Intent intent = new Intent(this, DownloadService.class);
    intent.setAction(DownloadService.ACTION_DOWNLOAD);
    intent.putExtra(DownloadService.EXTRA_PARAM_URL, mUrlPath);
    intent.putExtra(DownloadService.EXTRA_PARAM_DOCID, 1);
    startService(intent);
  }

  private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener)
  {
    new AlertDialog.Builder(this)
      .setMessage(message)
      .setPositiveButton("OK", okListener)
      .setNegativeButton("Cancel", null)
      .create()
      .show();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         @NonNull String[] permissions,
                                         @NonNull int[] grantResults)
  {
    switch (requestCode)
    {
      case PERMISSIONS_FOR_DOWNLOAD_WITH_TASK:
      {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
          // permission was granted
          performDownloadWithTask();
        }
        //else
        //{
          // permission denied
          // should tell something to the user
        //}
      }
      break;

      case PERMISSIONS_FOR_DOWNLOAD_WITH_TASK_FRAGMENT:
      {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
          // permission was granted
          performDownloadWithTaskFragment();
        }
        //else
        //{
        // permission denied
        // should tell something to the user
        //}
      }
      break;

      case PERMISSIONS_FOR_DOWNLOAD_WITH_SERVICE:
      {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
          // permission was granted
          performDownloadWithService();
        }
        //else
        //{
        // permission denied
        // should tell something to the user
        //}
      }
      break;

      // other 'case' lines to check for other permissions this app might request
    }
  }

  // internal class implementing the download
  private class DownloadAsyncTask extends AsyncTask<String, Integer, Void>
  {
    // callers
    @Override
    protected void onPreExecute()
    {
      TextView progressText = (TextView) findViewById(R.id.downloadProgress);
      progressText.setText(R.string.download_started);
    }

    @Override
    protected Void doInBackground(String... urls)
    {
      if (urls.length != 1)
      {
        Log.e(TAG, "Not implemented");
        return null;
      }

      // build the uri to be used for download
      Uri.Builder builder = new Uri.Builder();
      Uri uri = builder.path(urls[0]).build();

      // build the output path and create the output file
      String fileName = uri.getLastPathSegment();
      File output = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
      if (output.exists())
      {
        boolean rc = output.delete();
        if (! rc)
        {
          Log.e(TAG, "could not delete output file");
          return null;
        }
      }

      // start download and save the results to the output file
      InputStream stream = null;
      FileOutputStream fos = null;
      try
      {
        // open the url connection
        URL url = new URL(urls[0]);
        URLConnection urlConnection = url.openConnection();
        stream = urlConnection.getInputStream();

        // create the output sream
        fos = new FileOutputStream(output.getPath());

        // get the size of the file to be downloaded
        int file_size = urlConnection.getContentLength();

        // download using a buffer of buffer_size bytes
        int old_percent = 0;
        int download_size = 0;
        final int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int next;
        while ((next = stream.read(buffer, 0, bufferSize)) != -1 && ! isCancelled())
        {
          fos.write(buffer, 0, next);

          download_size += next;

          int percent = (int) (((double) (download_size) * 100) / (double) file_size);
          if (percent > old_percent)
          {
            publishProgress(percent);
            old_percent = percent;
          }
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      finally
      {
        if (stream != null)
        {
          try
          {
            stream.close();
          }
          catch (IOException e)
          {
            e.printStackTrace();
          }
        }
        if (fos != null)
        {
          try
          {
            fos.close();
          }
          catch (IOException e)
          {
            e.printStackTrace();
          }
        }
      }

      return null;
    }

    @Override
    protected void onProgressUpdate(Integer... percent)
    {
      TextView progressText = (TextView) findViewById(R.id.downloadProgress);
      NumberFormat percentFormat = NumberFormat.getPercentInstance();
      progressText.setText(percentFormat.format((double) percent[0] / 100));
    }

    @Override
    protected void onCancelled()
    {
      // task cancelled
      Log.d(TAG, "AsyncTask cancelled");

      // call the mother class
      super.onCancelled();
    }

    @Override
    protected void onPostExecute(Void ignore)
    {
      TextView progressText = (TextView) findViewById(R.id.downloadProgress);
      progressText.setText(R.string.download_done);
    }
  }

  // Broadcast receiver for receiving status updates from the DownloadService
  private class DownloadServiceResponseReceiver extends BroadcastReceiver
  {
    // Prevents instantiation
    private DownloadServiceResponseReceiver()
    {
    }

    // Called when the BroadcastReceiver gets an Intent it's registered to receive
    @Override
    public void onReceive(Context context, Intent intent)
    {
      if (mStatusIntentFilter != null)
      {
        final String action = intent.getAction();

        if (mStatusIntentFilter.getAction(0).equals(action))
        {
          // download done
          TextView progressText = (TextView) findViewById(R.id.downloadProgress);
          progressText.setText(R.string.download_done);
        }
        else if (mStatusIntentFilter.getAction(1).equals(action))
        {
          // progress notification
          final int percent = intent.getIntExtra(DownloadService.EXTRA_PARAM_PROGRESS, 0);
          NumberFormat percentFormat = NumberFormat.getPercentInstance();
          TextView progressText = (TextView) findViewById(R.id.downloadProgress);
          progressText.setText(percentFormat.format((double) percent / 100));
          mProgress = percent;
        }
        else if (mStatusIntentFilter.getAction(2).equals(action))
        {
          // download started
          TextView progressText = (TextView) findViewById(R.id.downloadProgress);
          progressText.setText(R.string.download_started);
        }
      }
    }
  }

  // methods implementing DownloadTaskFragment.TaskCallbacks
  @Override
  public void onPreExecute()
  {
    TextView progressText = (TextView) findViewById(R.id.downloadProgress);
    progressText.setText(R.string.download_started);
  }

  @Override
  public void onProgressUpdate(int percent)
  {
    TextView progressText = (TextView) findViewById(R.id.downloadProgress);
    NumberFormat percentFormat = NumberFormat.getPercentInstance();
    progressText.setText(percentFormat.format((double) percent / 100));
    mProgress = percent;
  }

  @Override
  public void onCancelled()
  {
    TextView progressText = (TextView) findViewById(R.id.downloadProgress);
    progressText.setText(R.string.download_cancelled);
  }

  @Override
  public void onPostExecute()
  {
    TextView progressText = (TextView) findViewById(R.id.downloadProgress);
    progressText.setText(R.string.download_done);
  }
}
