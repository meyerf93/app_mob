package ch.heia.mobiledev.uribeacon;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class UriBeaconScanner extends Fragment{

    private final static String TAG = UriBeaconScanner.class.getSimpleName();

    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_EBABLE_BT = 10;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    private BluetoothLeScanner mBluetoothLeScanner;
    private Handler mHandler;
    static final private long SCAN_PERIOD = 10000;
    private boolean mBeacon = false;

    private boolean mRunning = false;

    private int mReturnEmplacment = -1;

    static final public UriBeacon actBeacon[] = {null,null,null};


    private UriBeaconScannerCallback mDownloadCallbacks;

    private DownloadAsyncTask  mDownloadAsyncTask;


    interface UriBeaconScannerCallback{
        void onPreExecute();
        void onProgessUpdate(int percent);
        void onCancelled();
        void onPostExecute(int returnEmplacement);
    }

    public void Initialize(Context context){
        final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        //create the bt scanner
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        mReturnEmplacment = -1;
        mHandler = new Handler();

    }


    public void onScanClicked(Context context){
        Log.v(TAG,"onScacnClicked");
        mBeacon=false;
        //check if bt is enable
        if(mBluetoothAdapter==null||!mBluetoothAdapter.isEnabled()){
            Log.v(TAG,"adapter not disp");
            if (context.checkSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED &&context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(Manifest.permission.BLUETOOTH) &&shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)&& shouldShowRequestPermissionRationale(Manifest.permission.BLUETOOTH_ADMIN)) {
                    showMessageOKCancel(getResources().getString(R.string.permission_bt),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(new String[]{Manifest.permission.BLUETOOTH,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_ADMIN}, REQUEST_EBABLE_BT);
                                }
                            });
                } else {
                    requestPermissions(new String[]{Manifest.permission.BLUETOOTH,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_ADMIN}, REQUEST_EBABLE_BT);
                }
            }
        }else {
            btEnabled();
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener)
    {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



    private void btEnabled(){
        Log.v(TAG,"onbtEnabled()");
        //launch the scan;
        mBluetoothLeScanner.startScan(mScanCallback);
        //launch our temp
        mHandler.postDelayed(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                mBluetoothLeScanner.stopScan(mScanCallback);
                if(mBeacon) {
                    Log.v(TAG,"Beacon found");
                }
                else{
                    Log.v(TAG,"No corresponding beacon found");
                }
            }
        }, SCAN_PERIOD);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    btEnabled();
                }
                break;
            }
            case REQUEST_EBABLE_BT: {
                if((grantResults.length >0) &&(grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED)){
                    btEnabled();
                }
                break;
            }
        }
    }

    private final ScanCallback mScanCallback = new ScanCallback() {
        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            UriBeacon temp_actBeacon = UriBeacon.createFromScanResult(result.getScanRecord());
            start(temp_actBeacon.getURL(),mReturnEmplacment++);
        }
    };

    public void start(String urlPath,int returnEmpl){
        if(! mRunning){

            mDownloadAsyncTask = new DownloadAsyncTask();
            mDownloadAsyncTask.execute(urlPath);

            mReturnEmplacment =  returnEmpl;

            mRunning = true;
        }
    }

    @Override
    public void onDestroy() {
        mBluetoothLeScanner.stopScan(mScanCallback);

        mBluetoothLeScanner=null;
        mBluetoothAdapter = null;

        super.onDestroy();
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

            /*if (urls.length != 1) {
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
                                actBeacon[mReturnEmplacment]+= temp_line+"\n";
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
*/
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
