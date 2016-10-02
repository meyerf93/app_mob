package ch.heia.mobiledev.launchactivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;


public class LaunchActivity extends Activity
{
  // used for logging
  private final static String TAG = LaunchActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    Log.d(TAG, "onCreate()");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_launch);
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
  protected void onNewIntent(Intent intent)
  {
    Log.d(TAG, "onNewIntent()");
    super.onNewIntent(intent);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState)
  {
    Log.d(TAG, "onSaveInstanceState()");
    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState)
  {
    Log.d(TAG, "onRestoreInstanceState()");
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig)
  {
    Log.d(TAG, "onConfigurationChanged()");
    super.onConfigurationChanged(newConfig);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    // getMenuInflater().inflate(R.menu.menu_launch, menu);
    return false;
  }

  // click handlers
  public void onLaunchSimpleUpActivity(View v)
  {
    //Intent startIntent = new Intent();
    //startIntent.setClassName(getPackageName(), SimpleUpActivity.class.getName());

    Intent startIntent = new Intent(LaunchActivity.this, SimpleUpActivity.class);

    startActivity(startIntent);
  }

  public void onLaunchAndroidVersionActivity(View v)
  {
    //Intent startIntent = new Intent();
    //startIntent.setClassName(getPackageName(), SimpleUpActivity.class.getName());

    Intent startIntent = new Intent(LaunchActivity.this, AndroidVersionActivity.class);

    startActivity(startIntent);
  }

  public void onLaunchMapFragmentActivity(View v)
  {
    //Intent startIntent = new Intent();
    //startIntent.setClassName(getPackageName(), SimpleUpActivity.class.getName());

    Intent startIntent = new Intent(LaunchActivity.this, MapFragmentActivity.class);

    startActivity(startIntent);
  }
}
