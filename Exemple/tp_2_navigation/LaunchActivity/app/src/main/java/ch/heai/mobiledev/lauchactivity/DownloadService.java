package ch.heai.mobiledev.lauchactivity;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.support.v4.content.LocalBroadcastManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Florian.Meyer on 06.04.2016.
 */
public class DownloadService extends IntentService {

    private static final String TAG = DownloadService.class.getSimpleName();

    public static final String ACTION_DOWNLOAD = "ch.heia.mobiledev.downloadservice.action.DOWNLOAD";
    public static final String EXTRA_PARAM_URL = "ch.heia.mobiledev.dowloadservice.extra.DOWNLOAD_URL";
    public static final String EXTRA_PARAM_DOCID= "ch.heia.mobiledev.downloadservice.extra.DOCID";

    public static final String ACTION_DONE = "ch.heia.mobildev.downloadservice.action.DONE";

    public DownloadService(){
        super("DowdloadService");
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreat");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    protected void onHandleIntent(Intent intent){
        if(intent != null){
            final String action = intent.getAction();
            if(ACTION_DOWNLOAD.equals(action)){
                handleActionDownload(intent);
            }
        }
    }

    private void handleActionDownload(Intent intent){
        final String urlPath = intent.getStringExtra(EXTRA_PARAM_URL);
        final int docid = intent.getIntExtra(EXTRA_PARAM_DOCID, -1);

        Uri.Builder builder = new Uri.Builder();
        Uri uri = builder.path(urlPath).build();

        String fileName = uri.getLastPathSegment();
        File output = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        if(output.exists()){
            boolean rc = output.delete();
            if(!rc){
                Log.e(TAG,"could not delete output file");
                return;
            }
        }

        InputStream stream = null;
        FileOutputStream fos = null;
        try{
            URL url = new URL(urlPath);
            URLConnection urlConnection = url.openConnection();
            stream = urlConnection.getInputStream();
            fos = new FileOutputStream(output.getPath());

            int download_size = 0;
            final int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int next;
            while((next=stream.read(buffer,0,bufferSize))!=-1){
                fos.write(buffer,0,next);

                download_size += next;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(stream!=null){
                try{
                    stream.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(fos !=null){
                try{
                    fos.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        Intent localIntent = new Intent();
        localIntent.setAction(ACTION_DONE);
        localIntent.putExtra(EXTRA_PARAM_DOCID, docid);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

    }
}
