package ch.heia.mobiledev.launchactivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class AndroidVersionActivity extends Activity
{
  // used for logging
  private static final String TAG = AndroidVersionActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    Log.d(TAG, "onCreate()");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_android_version);
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
    super.onDestroy();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    return false;
  }

}
