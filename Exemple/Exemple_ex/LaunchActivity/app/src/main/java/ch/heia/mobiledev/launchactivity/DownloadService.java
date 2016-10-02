package ch.heia.mobiledev.launchactivity;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends IntentService
{
  // used for logging
  private static final String TAG = DownloadService.class.getSimpleName();

  // strings used for intent parameters
  public static final String ACTION_DOWNLOAD = "ch.heia.mobiledev.downloadservice.action.DOWNLOAD";
  public static final String EXTRA_PARAM_URL = "ch.heia.mobiledev.downloadservice.extra.DOWNLOAD_URL";
  public static final String EXTRA_PARAM_DOCID = "ch.heia.mobiledev.downloadservice.extra.DOCID";
  public static final String EXTRA_PARAM_PROGRESS = "ch.heia.mobiledev.downloadservice.extra.PROGRESS";

  // strings used for broadcast
  public static final String ACTION_STARTED = "ch.heia.mobiledev.downloadservice.action.started";
  public static final String ACTION_PROGRESS = "ch.heia.mobiledev.downloadservice.action.preogress";
  public static final String ACTION_DONE = "ch.heia.mobiledev.downloadservice.action.done";

  // download progress
  private int mProgress;

  // IntentService needs to be constructed with a name
  public DownloadService()
  {
    super("DownloadService");
  }

  // accessor to progress
  public int getProgress()
  {
    return mProgress;
  }

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
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public IBinder onBind(Intent intent)
  {
    Log.d(TAG, "onBind");
    return super.onBind(intent);
  }

  @Override
  public boolean onUnbind(Intent intent)
  {
    Log.d(TAG, "onUnbind");
    return super.onUnbind(intent);
  }

  @Override
  public void onDestroy()
  {
    Log.d(TAG, "onDestroy");
    super.onDestroy();
  }

  @Override
  protected void onHandleIntent(Intent intent)
  {
    if (intent != null)
    {
      final String action = intent.getAction();

      if (ACTION_DOWNLOAD.equals(action))
      {
        handleActionDownload(intent);
      }
    }
  }

  /**
   * Handle action Download in the provided background thread with the provided
   * parameters.
   */
  private void handleActionDownload(Intent intent)
  {
    mProgress = 0;

    // notify start
    {
      Intent localIntent = new Intent();
      localIntent.setAction(ACTION_STARTED);
      LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

    final String urlPath = intent.getStringExtra(EXTRA_PARAM_URL);
    final int docid = intent.getIntExtra(EXTRA_PARAM_DOCID, -1);

  // build the uri to be used for download
    Uri.Builder builder = new Uri.Builder();
    Uri uri = builder.path(urlPath).build();

    // build the output path and create the output file
    String fileName = uri.getLastPathSegment();
    File output = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
    if (output.exists())
    {
      boolean rc = output.delete();
      if (! rc)
      {
        Log.e(TAG, "could not delete output file");
        return;
      }
    }

    InputStream stream = null;
    FileOutputStream fos = null;
    try
    {
      URL url = new URL(urlPath);
      URLConnection urlConnection = url.openConnection();
      stream = urlConnection.getInputStream();
      fos = new FileOutputStream(output.getPath());

      // get the size of the file to be downloaded
      int file_size = urlConnection.getContentLength();

      int download_size = 0;
      final int bufferSize = 1024;
      byte[] buffer = new byte[bufferSize];
      int next;
      while ((next = stream.read(buffer, 0, bufferSize)) != -1)
      {
        fos.write(buffer, 0, next);

        download_size += next;

        // notify progress using broadcast
        int percent = (int) (((double) (download_size) * 100) / (double) file_size);
        if (percent > mProgress)
        {
          Intent localIntent = new Intent();
          localIntent.setAction(ACTION_PROGRESS);
          localIntent.putExtra(EXTRA_PARAM_PROGRESS, percent);
          LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

          mProgress = percent;
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

    // notify using broadcast
    Intent localIntent = new Intent();
    localIntent.setAction(ACTION_DONE);
    localIntent.putExtra(EXTRA_PARAM_DOCID, docid);
    LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
  }
}
