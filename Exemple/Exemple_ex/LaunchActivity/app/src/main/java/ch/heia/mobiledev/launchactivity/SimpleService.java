package ch.heia.mobiledev.launchactivity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SimpleService extends Service
{
  // used for logging
  private static final String TAG = SimpleService.class.getSimpleName();

  // used for stopping the thread of the service
  public static volatile boolean shouldContinue = true;

  @Override
  public void onCreate()
  {
    Log.d(TAG, "onCreate");
    super.onCreate();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId)
  {
    Log.d(TAG, "onStartCommand");

    final int currentId = startId;

    // uncomment the lines below for adding threading
    Runnable r = new Runnable()
    {
      public void run()
      {
        for (int i = 0; i < 3; i++)
        {
          Log.i(TAG, "Service running with id " + currentId);
          long endTime = System.currentTimeMillis() + 10 * 1000;
          while (System.currentTimeMillis() < endTime && shouldContinue)
          {
            synchronized (this)
            {
              try
              {
                wait(endTime - System.currentTimeMillis());
              }
              catch (Exception e)
              {
                e.printStackTrace();
              }
            }
          }
        }
        Log.i(TAG, "Service thread exiting with id " + currentId);
        stopSelf(currentId);
      }
    };

    Thread t = new Thread(r);
    t.start();

    Log.i(TAG, "Service exiting onStartCommand");

    // see http://android-developers.blogspot.ch/2010/02/service-api-changes-starting-with.html
    // for a good explanation on what the return code means
    return Service.START_STICKY;
  }

  @Override
  public IBinder onBind(Intent intent)
  {
    // we do not allow binding
    Log.d(TAG, "onBind");
    return null;
  }

  @Override
  public boolean onUnbind(Intent intent)
  {
    // should not be called
    Log.d(TAG, "onUnbind");
    return super.onUnbind(intent);
  }

  @Override
  public void onDestroy()
  {
    Log.d(TAG, "onDestroy");
    super.onDestroy();
  }
}
