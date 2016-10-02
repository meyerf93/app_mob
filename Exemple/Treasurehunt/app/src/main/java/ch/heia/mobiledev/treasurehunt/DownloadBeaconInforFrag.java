package ch.heia.mobiledev.treasurehunt;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class DownloadBeaconInforFrag extends Fragment{

    private static final String TAG = DownloadBeaconInforFrag.class.getSimpleName();

    public static final String[] contain_clue = {"","","",""};

    interface DownloadCallbacks{
        void onPreExecute();
        void onProgessUpdate(int percent);
        void onCancelled();
        void onPostExecute(int returnEmplacement);
    }

    private DownloadCallbacks mDownloadCallbacks;

    private DownloadAsyncTask  mDownloadAsyncTask;

    private boolean mRunning = false;

    private int mReturnEmplacment = -1;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG,"onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        Log.d(TAG,"onAttach()");
        super.onAttach(context);

        try{
            mDownloadCallbacks = (DownloadCallbacks) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " must implement DownloadCallback");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate()");
        for(int i = 0;i<contain_clue.length;++i){
            contain_clue[i] = "";
        }
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onDetach() {
        Log.d(TAG,"onDetach()");
        super.onDetach();

        mDownloadCallbacks = null;
    }

    @Override
    public void onPause() {
        Log.d(TAG,"onPause()");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d(TAG,"onResume()");
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.d(TAG,"onStart()");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.d(TAG,"onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.v(TAG,"ondestroy()");
        if(mDownloadAsyncTask != null){
            mDownloadAsyncTask.cancel(true);
            mDownloadAsyncTask = null;
        }
        super.onDestroy();
    }

    public void start(String urlPath,int returnEmpl){
        if(! mRunning){

            mDownloadAsyncTask = new DownloadAsyncTask();
            mDownloadAsyncTask.execute(urlPath);

            mReturnEmplacment =  returnEmpl;

            mRunning = true;
        }
    }

    private class DownloadAsyncTask extends AsyncTask<String,Integer,Void> {

        @Override
        protected void onPreExecute() {
            if (mDownloadCallbacks != null) {
                mDownloadCallbacks.onPreExecute();
            }
        }

        @Override
        protected Void doInBackground(String... urls) {
            if (urls.length != 1) {
                Log.e(TAG, "Note implement");
                return null;
            }

            Uri.Builder builder = new Uri.Builder();
            Uri uri = builder.path(urls[0]).build();

            String fileName = uri.getLastPathSegment();
            InputStream stream = null;
            FileOutputStream fos = null;
            try {
                URL url = new URL(urls[0]);
                URLConnection urlConnection = url.openConnection();
                stream = urlConnection.getInputStream();

                fos = getContext().openFileOutput(fileName,Context.MODE_PRIVATE);

                final int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int next;
                while ((next = stream.read(buffer, 0, bufferSize)) != -1 && !isCancelled()) {
                    fos.write(buffer, 0, next);
                }
                try{
                    FileInputStream foi =getContext().openFileInput(fileName);
                    InputStreamReader isr = new InputStreamReader(foi);
                    BufferedReader bf = new BufferedReader(isr);
                    String temp_line;
                    while ((temp_line = bf.readLine())!=null){
                        if(!temp_line.contains("minor")){
                            if(!(temp_line.trim().length()==0)){
                                contain_clue[mReturnEmplacment]+= temp_line+"\n";
                            }
                        }
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... percent) {
            if (mDownloadCallbacks != null) {
                mDownloadCallbacks.onProgessUpdate(percent[0]);
            }
        }

        @Override
        protected void onCancelled() {
            if (mDownloadCallbacks != null) {
                mDownloadCallbacks.onCancelled();
            }
            mRunning = false;
            mReturnEmplacment = -1;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(mDownloadCallbacks != null){
                mDownloadCallbacks.onPostExecute(mReturnEmplacment);
            }
            mRunning = false;
            mReturnEmplacment = -1;
        }
    }
}


