package ch.heia.mobiledev.uribeacon;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class UriBeaconActivity extends Activity implements UriBeaconScanner.UriBeaconScannerCallback {
    //logging
    private static final String TAG = UriBeaconActivity.class.getSimpleName();

    private UriBeaconScanner mScanner;
    private UriBeacon beacons[];

    private static final String TAG_DOWNLOAD ="download_fragement";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uri_beacon);

        //Try to launch the download fragment
        FragmentManager fragmentManger = getFragmentManager();
        mScanner = (UriBeaconScanner) fragmentManger.findFragmentByTag(TAG_DOWNLOAD);

        if (mScanner == null)
        {
            mScanner = new UriBeaconScanner();
            mScanner.Initialize(this);
            fragmentManger.beginTransaction().add(mScanner, TAG_DOWNLOAD).commit();
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
        super.onResume();
        mScanner.onScanClicked(this);
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
    public void onPreExecute() {
        Log.d(TAG, "onPreExecute()");
    }

    @Override
    public void onProgessUpdate(int percent) {
        Log.d(TAG, "onProgessUpdate()");
    }

    @Override
    public void onCancelled() {
        Log.d(TAG, "onCancelled()");
    }

    @Override
    public void onPostExecute(int returnEmplacement) {
        Log.d(TAG, "onPostExecute()");
        beacons[returnEmplacement] = UriBeaconScanner.actBeacon[returnEmplacement];
    }
}
