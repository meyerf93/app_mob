package ch.heia.mobiledev.launchactivity;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadTaskFragment extends Fragment
{
  // used for logging
  private static final String TAG = DownloadTaskFragment.class.getSimpleName();

  // Callback interface through which the fragment will report the
  // task's progress and results back to the Activity
  interface TaskCallbacks
  {
    void onPreExecute();
    void onProgressUpdate(int percent);
    void onCancelled();
    void onPostExecute();
  }

  // callback instance
  private TaskCallbacks mTaskCallbacks;

  // AsyncTask instance
  private DownloadAsyncTask mDownloadAsyncTask;

  // flag stating whether the download task is running
  private boolean mRunning = false;

  @Override
  public void onActivityCreated(Bundle savedInstanceState)
  {
    Log.d(TAG, "onActivityCreated()");
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onAttach(Context context)
  {
    Log.d(TAG, "onAttach()");
    super.onAttach(context);

    // get the reference to the callback
    try
    {
      mTaskCallbacks = (TaskCallbacks) context;
    }
    catch (ClassCastException e)
    {
      throw new ClassCastException(context.toString() + " must implement TaskCallbacks");
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    Log.d(TAG, "onCreate()");

    super.onCreate(savedInstanceState);

    // Retain this fragment across configuration changes
    setRetainInstance(true);
  }

  @Override
  public void onDetach()
  {
    Log.d(TAG, "onDetach()");
    super.onDetach();

    // reset the callback interface
    mTaskCallbacks = null;
  }

  @Override
  public void onPause()
  {
    Log.d(TAG, "onPause()");
    super.onPause();
  }

  @Override
  public void onResume()
  {
    Log.d(TAG, "onResume()");
    super.onResume();
  }

  @Override
  public void onStart()
  {
    Log.d(TAG, "onStart()");
    super.onStart();
  }

  @Override
  public void onStop()
  {
    Log.d(TAG, "onStart()");
    super.onStop();
  }

  @Override
  public void onDestroy()
  {
    if (mDownloadAsyncTask != null)
    {
      mDownloadAsyncTask.cancel(true);
      mDownloadAsyncTask = null;
    }
    super.onDestroy();
  }

  // methods used by the hosting activity
  public void start(String urlPath)
  {
    if (! mRunning)
    {
      // Create and execute the download task
      mDownloadAsyncTask = new DownloadAsyncTask();
      mDownloadAsyncTask.execute(urlPath);

      mRunning = true;
    }
  }
  // internal class implementing the download
  private class DownloadAsyncTask extends AsyncTask<String, Integer, Void>
  {
    // callers
    @Override
    protected void onPreExecute()
    {
      if (mTaskCallbacks != null)
      {
        mTaskCallbacks.onPreExecute();
      }
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
        if (!rc)
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
        while ((next = stream.read(buffer, 0, bufferSize)) != -1 && !isCancelled())
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
      if (mTaskCallbacks != null)
      {
        mTaskCallbacks.onProgressUpdate(percent[0]);
      }
    }

    @Override
    protected void onCancelled()
    {
      if (mTaskCallbacks != null)
      {
        mTaskCallbacks.onCancelled();
      }
      mRunning = false;
    }

    @Override
    protected void onPostExecute(Void ignore)
    {
      if (mTaskCallbacks != null)
      {
        mTaskCallbacks.onPostExecute();
      }
      mRunning = false;
    }
  }
}
