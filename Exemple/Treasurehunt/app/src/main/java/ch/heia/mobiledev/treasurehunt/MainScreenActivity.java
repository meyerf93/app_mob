package ch.heia.mobiledev.treasurehunt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.*;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class MainScreenActivity extends FragmentActivity implements OnMapReadyCallback, DownloadBeaconInforFrag.DownloadCallbacks{
    // used for logging
    private final static String TAG = MainScreenActivity.class.getSimpleName();

    // used for permissions for download and localisation
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    //URL to download the information of the clue
    private final String mUrlPath[] = { "https://onedrive.live.com/download.aspx?cid=9029D0C756D0A25C&resid=9029d0c756d0a25c%21434&authkey=%21AOyXHfk56pXiPPQ&canary="
                                        ,"https://onedrive.live.com/download?cid=9029D0C756D0A25C&resid=9029D0C756D0A25C%21435&authkey=AF_xhDl4O43BH5c"
                                        ,"https://onedrive.live.com/download?cid=9029D0C756D0A25C&resid=9029D0C756D0A25C%21345&authkey=AFom4lFP0egO6Qs"
                                        ,"https://onedrive.live.com/download?cid=9029D0C756D0A25C&resid=9029D0C756D0A25C%21436&authkey=AIWa3ZShZQ-hJ8Q"
    };
    //tag used to launch a fragment
    private static final String TAG_DOWNLOAD ="download_fragement";
    private static final String TAG_DIALOG = "dialog_fragment";
    //
    private DownloadBeaconInforFrag mDownloadBeaconInforFrag;

    private int TotalStep;
    private int CurrentStep;

    //Button used ny the fragmentactivity
    private Button StepButton;
    private Button BeaconButton;
    private Button ScanButton;

    private IntentFilter mIntentFilter;

    private BroadcastReceiver mReceiver;

    private GoogleMap mGoogleMap;
    private LocationManager mLocationManager;

    //bluetooth member
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_EBABLE_BT = 10;
    private BluetoothLeScanner mBluetoothLeScanner;
    //Handelr called after the SCAN_PERIOD to cancel the scan
    private Handler mHandler;
    static final private long SCAN_PERIOD = 10000;
    private boolean mBeacon = false;
    static final Beacon[] listBeacon = {null,null,null,null};
    private static final int sigProp = 2;
    //Distance used to detect the different case of dectection for the beacon
    private static final double distRed = 10.0; //distance in meter
    private static final double distOrg =  5.0; //distance in meter
    private static final double distGreen = 1.0; //distance in meter
    //Name of the file containing the data of the applicaiton
    private final String SAVE_FILE = "SAVE_FILE.xml";

    //download
    private String url;
    private int returnEmpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment);

        mHandler = new Handler();

        //Initialisation of the list containing the instance of the beacon catched
        for(int i=0;i<4;++i){
            listBeacon[i] = null;
        }

        RestoreData();

        //Creation of the instance of the different button used by the application.
        StepButton = (Button) findViewById(R.id.step_button);
        BeaconButton = (Button) findViewById(R.id.overview_beacon_btn);
        ScanButton = (Button) findViewById(R.id.scan_btn);

        //
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        //Create the filter to catch the step counter services action
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(StepCounterService.ACTION_STEP_COUNTER);

        //Try to launch the download fragment
        FragmentManager fragmentManger = getFragmentManager();
        mDownloadBeaconInforFrag = (DownloadBeaconInforFrag) fragmentManger.findFragmentByTag(TAG_DOWNLOAD);

        if (mDownloadBeaconInforFrag == null)
        {
            mDownloadBeaconInforFrag = new DownloadBeaconInforFrag();
            fragmentManger.beginTransaction().add(mDownloadBeaconInforFrag, TAG_DOWNLOAD).commit();
        }

        //Strat the step counter services
        Intent serviceIntent = new Intent(this,StepCounterService.class);
        serviceIntent.putExtra("CurrentStep",CurrentStep);
        serviceIntent.putExtra("TotalStep",TotalStep);
        startService(serviceIntent);

        //Launch the map fragment

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_frag);
        mapFragment.getMapAsync(this);

        //Get the location manger
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

     private void RestoreData(){
        //Code to recupere the saved information ---------------------------------------------------------------------------
        try{
            FileInputStream fis = openFileInput(SAVE_FILE);
            Intent temp_int = getIntent();
            if(temp_int!=null){
                Log.v(TAG,temp_int.toString());
                if (temp_int.getStringExtra("Launching_mode").equals(MenuScreenActivity.NEW_MAIN_SREEN) || (fis == null)) {
                    Log.v(TAG,"newGame intent");
                    //case for a new game
                    TotalStep = 0;
                    CurrentStep = 0;
                    for(int i = 0; i<listBeacon.length;++i){
                        listBeacon[i] = null;
                    }
                    Beacon.prevMinor = 245;
                }
                else if(temp_int.getStringExtra("Launching_mode").equals(MenuScreenActivity.LOAD_MAIN_SCREEN)){
                    Log.v(TAG,"continueGame intent");
                    //case when we want to continue the game
                    List entries = XmlParser.restoreData(fis);

                    for(int i = 0;i<entries.size();++i){
                        Log.v(TAG,entries.toString());
                        //recupere the information of the list and but it in the right place
                        if(entries.get(i)==null) break;
                        if(i==0) TotalStep = (Integer) entries.get(i);
                        else if(i==1) CurrentStep = (Integer) entries.get(i);
                        else if(i==2) Beacon.prevMinor = (Integer) entries.get(i);
                        else {
                            listBeacon[i-3] = (Beacon) entries.get(i);
                            Log.v(TAG,"value of beacon" +(listBeacon[i-3]==null));
                        }
                    }

                }
            }else Log.v(TAG,"parent intent null");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        //------------------------------------------------------------------------------------------
    }

    private LatLng getCurrentPos(){
        try{
            if(mLocationManager.isProviderEnabled("gps")||mLocationManager.isProviderEnabled("network")) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Should we show an explanation?
                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        showMessageOKCancel(getResources().getString(R.string.permission_explanation),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                                    }
                                });
                    } else {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }
                } else {
                    Location location;
                    if (mLocationManager.isProviderEnabled("network")) {
                        location = mLocationManager.getLastKnownLocation("network");
                    } else {
                        location = mLocationManager.getLastKnownLocation("gps");
                    }
                    double lng = location.getLongitude();
                    double lat = location.getLatitude();
                    Log.v(TAG, "coordonnées : " + lng + "," + lat);
                    return new LatLng(lat, lng);
                }
            }
            else{
                Log.v(TAG,"No localisation info");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy()");

        //stop the service of step counter
        Intent serviceIntent = new Intent(this,StepCounterService.class);
        stopService(serviceIntent);
        //set the default background color for the bouton
        GradientDrawable tempDraw = (GradientDrawable) BeaconButton.getBackground();
        tempDraw.setColor(getResources().getColor(R.color.colorBtnMapFragment,null));
        tempDraw = (GradientDrawable) ScanButton.getBackground();
        tempDraw.setColor(getResources().getColor(R.color.colorBtnMapFragment,null));

        SaveData();

        mBluetoothAdapter = null;

        super.onDestroy();
    }

    private void SaveData(){
        //Save the data of the application ------------------------------------------------------------------------------
        FileOutputStream fos = null;
        Writer w = null;
        try {
            Log.v(TAG,"on try catch");
            fos = openFileOutput(SAVE_FILE,Context.MODE_PRIVATE);
            //fos = openFileOutput(SAVE_FILE, Context.MODE_PRIVATE);
            w = new OutputStreamWriter(fos,"UTF-8");
            Log.v(TAG,"start write");
            w.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            w.write("<TreasureHunt>\n");
            w.write("\t<TotalStep>"+TotalStep+"</TotalStep>\n");
            w.write("\t<CurrentStep>"+CurrentStep+"</CurrentStep>\n");
            w.write("\t<PrevMinor>"+Beacon.prevMinor+"</PrevMinor>\n");
            for (int i = 0; i < listBeacon.length; ++i) {
                Log.v(TAG,"onLoop for beacon");
                if(listBeacon[i]==null) continue;
                w.write("\t<Beacon>\n");
                w.write("\t\t<Name>"+listBeacon[i].name+"</Name>\n");
                w.write("\t\t<Clue_name>"+listBeacon[i].clue_name+"</Clue_name>\n");
                w.write("\t\t<UUID>"+listBeacon[i].uuid+"</UUID>\n");
                w.write("\t\t<Major>"+listBeacon[i].Major+"</Major>\n");
                w.write("\t\t<Minor>"+listBeacon[i].Minor+"</Minor>\n");
                w.write("\t\t<Pow>"+listBeacon[i].Pow+"</Pow>\n");
                w.write("\t\t<Dist>"+listBeacon[i].dist+"</Dist>\n");
                w.write("\t\t<Step>"+listBeacon[i].step+"</Step>\n");
                w.write("\t\t<Position>"+listBeacon[i].position+"</Position>\n");
                w.write("\t\t<Clue>"+DownloadBeaconInforFrag.contain_clue[i]+"</Clue>\n");
                w.write("\t</Beacon>\n");
            }
            w.write("</TreasureHunt>");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if (w != null) {
                try {
                    w.close();
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
        //--------------------------------------------------------------------------------------------
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();

        mReceiver = new BroadcastReceiver() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action){
                    case StepCounterService.ACTION_STEP_COUNTER:
                        CurrentStep = intent.getIntExtra("CurrentStep",-1);
                        TotalStep = intent.getIntExtra("TotalStep",-1);
                        StepButton.setText(CurrentStep + " " + getResources().getString(R.string.text_step_counter));
                }
            }
        };

        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause()");
        unregisterReceiver(mReceiver);
        super.onPause();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // handle permissions
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                showMessageOKCancel(getResources().getString(R.string.permission_explanation),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                            }
                        });
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        } else{
            setPosition();
        }
    }

    private void setPosition(){
        mGoogleMap.setMyLocationEnabled(true);
        Log.d(TAG,"onMapReadyCalled()");
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getCurrentPos(),17));
        for (int i = 0; i<listBeacon.length;++i){
            Log.v(TAG,"on loop for restore marker"+(listBeacon[i]==null));
            if (listBeacon[i] != null) setMapMarker(listBeacon[i]);
        }
    }


    //Method to set a marker on the map
    private void setMapMarker(Beacon beacon){
        mGoogleMap.addMarker(new MarkerOptions()
                .position(beacon.position)
                .title("Beacon : minor: " + beacon.Minor + " step: "+ beacon.step));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted
                   setPosition();
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

    public void onBeaconClicked(View view){
        Intent intent = new Intent(MainScreenActivity.this, OverviewBeaconActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onClueClicked(View view){
        Intent intent = new Intent(MainScreenActivity.this, OverviewClueActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onScanClicked(View view){
        mBeacon =false;
        //check if bt is enable
        if(mBluetoothAdapter==null||!mBluetoothAdapter.isEnabled()){
            if (checkSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(Manifest.permission.BLUETOOTH) && shouldShowRequestPermissionRationale(Manifest.permission.BLUETOOTH_ADMIN)) {
                    showMessageOKCancel(getResources().getString(R.string.permission_bt),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN}, REQUEST_EBABLE_BT);
                                }
                            });
                } else {
                    requestPermissions(new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN}, REQUEST_EBABLE_BT);
                }
            }
        }else {
                btEnabled();
        }
    }


    private final ScanCallback mScanCallback = new ScanCallback() {
        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            final String format_step = "%.02f";

            Beacon actBeacon = Beacon.createFromScanResult(result.getScanRecord());

            //beacon is found
            if(actBeacon!=null){
                mBluetoothLeScanner.stopScan(mScanCallback);
                mBeacon = true;

                int rssi = result.getRssi();
                int pow = (int)actBeacon.Pow;
                double top = rssi-pow;
                double bot = -10*sigProp;
                double puiss = top/bot;
                actBeacon.dist= Math.pow(10.0,puiss);

                GradientDrawable tempDraw;
                BeaconButton.setText(getResources().getString(R.string.text_beacon_btn)+ "\n"+String.format(format_step,actBeacon.dist)+"m");
                if(actBeacon.dist<=distGreen){
                    int returnEmpl = -1;
                    tempDraw = (GradientDrawable) BeaconButton.getBackground();
                    tempDraw.setColor(getResources().getColor(R.color.beaconGreen,null));
                    actBeacon.step = CurrentStep;
                    CurrentStep = 0;
                    StepButton.setText(CurrentStep + " " + getResources().getString(R.string.text_step_counter));
                    actBeacon.position = getCurrentPos();
                    setMapMarker(actBeacon);
                    if(actBeacon.Major==1 && actBeacon.Minor == 246)        {
                        returnEmpl = 0;
                        onDownload(mUrlPath[0],returnEmpl);
                    }
                    else if(actBeacon.Major==1 && actBeacon.Minor ==247)    {
                        returnEmpl = 1;
                        onDownload(mUrlPath[1],returnEmpl);
                    }
                    else if(actBeacon.Major==1 && actBeacon.Minor ==248)    {
                        returnEmpl = 2;
                        onDownload(mUrlPath[2],returnEmpl);
                    }
                    else if(actBeacon.Major==1 && actBeacon.Minor ==249)    {
                        returnEmpl = 3;
                        onDownload(mUrlPath[3],returnEmpl);
                    }
                    if(returnEmpl!=-1 && returnEmpl<listBeacon.length){
                        listBeacon[returnEmpl] = actBeacon;
                    }
                    Beacon.prevMinor++;
                }
                else if (actBeacon.dist<=distOrg){
                    tempDraw = (GradientDrawable) BeaconButton.getBackground();
                    tempDraw.setColor(getResources().getColor(R.color.beaconOrange,null));
                }
                else if (actBeacon.dist<=distRed){
                    tempDraw = (GradientDrawable) BeaconButton.getBackground();
                    tempDraw.setColor(getResources().getColor(R.color.beaconRed,null));
                }
                else{
                    tempDraw = (GradientDrawable) BeaconButton.getBackground();
                    tempDraw.setColor(getResources().getColor(R.color.colorBtnMapFragment,null));
                    BeaconButton.setText(getResources().getString(R.string.text_beacon_btn)+ "\nNone");
                }

                BeaconButton.setText(getResources().getString(R.string.text_beacon_btn)+ "\n"+String.format(format_step,actBeacon.dist)+"m");

                //display the distance
                Log.v(TAG,"distance of the rsi : "+actBeacon.dist);
            }
        }
    };

    private void btEnabled(){
        //create the bt scanner
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        //launch the scan
        GradientDrawable tempDraw = (GradientDrawable) ScanButton.getBackground();
        tempDraw.setColor(getResources().getColor(R.color.scanActive,null));
        mBluetoothLeScanner.startScan(mScanCallback);
        //launch our temp
        mHandler.postDelayed(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                mBluetoothLeScanner.stopScan(mScanCallback);
                GradientDrawable tempDraw = (GradientDrawable) ScanButton.getBackground();
                tempDraw.setColor(getResources().getColor(R.color.colorBtnMapFragment,null));
                if(mBeacon) {
                    Log.v(TAG,"Beacon found");
                }
                else{
                    Log.v(TAG,"No corresponding beacon found");
                    tempDraw = (GradientDrawable) BeaconButton.getBackground();
                    tempDraw.setColor(getResources().getColor(R.color.colorBtnMapFragment,null));
                    BeaconButton.setText(getResources().getString(R.string.text_beacon_btn)+ "\nNone");
                }
            }
    }, SCAN_PERIOD);
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

    private void onDownload(String url, int returnEmpl){
        this.url = url;
        this.returnEmpl = returnEmpl;
        performDownload();
    }

    private void performDownload(){
        mDownloadBeaconInforFrag.start(url,returnEmpl);
    }

    @Override
    public void onPreExecute() {
        Log.v(TAG,"onPreExecute()");
    }

    @Override
    public void onProgessUpdate(int percent) {
        Log.v(TAG,"onProgessUpdate() : "+percent);
    }

    @Override
    public void onCancelled() {
        Log.v(TAG,"onCancelled()");
    }

    @Override
    public void onPostExecute(int returnEmplacement) {
        Log.v(TAG,"onPostExectue()");
        Log.v(TAG,DownloadBeaconInforFrag.contain_clue[returnEmplacement]);
        android.app.DialogFragment dialog= new DialogFragment();

        listBeacon[returnEmplacement].clue = DownloadBeaconInforFrag.contain_clue[returnEmplacement];

        Bundle msg0 = new Bundle();
        msg0.putInt("Major",listBeacon[returnEmplacement].Major);
        msg0.putInt("Minor",listBeacon[returnEmplacement].Minor);
        msg0.putInt("returnValue",returnEmplacement);
        msg0.putInt("ActualStep",listBeacon[returnEmplacement].step);

        dialog.setArguments(msg0);

        dialog.show(getFragmentManager(),TAG_DIALOG);

    }
}
