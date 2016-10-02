package ch.heia.mobiledev.launchactivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapFragmentActivity extends FragmentActivity
  implements LocationListFragment.LocationListFragmentListener,
             OnMapReadyCallback
{
  // used for logging
  private static final String TAG = MapFragmentActivity.class.getSimpleName();

  // data members used for the map
  private GoogleMap mGoogleMap;

  // used for permissions
  private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    Log.d(TAG, "onCreate()");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map_fragment);

    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
  }

  @Override
  public void onMapReady(GoogleMap googleMap)
  {
    mGoogleMap = googleMap;
    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    // handle permissions
    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
    {
      // Should we show an explanation?
      if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))
      {
        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.
        showMessageOKCancel(
          new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
              requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
          });
      }
      else
      {
        // No explanation needed, we can request the permission.

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
        // app-defined int constant. The callback method gets the
        // result of the request.
      }
    }
    else
    {
      mGoogleMap.setMyLocationEnabled(true);
    }
  }

  private void showMessageOKCancel(DialogInterface.OnClickListener okListener)
  {
    new AlertDialog.Builder(this)
      .setMessage("You need to allow access to Location !")
      .setPositiveButton("OK", okListener)
      .setNegativeButton("Cancel", null)
      .create()
      .show();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         @NonNull String[] permissions,
                                         @NonNull int[] grantResults)
  {
    switch (requestCode)
    {
      case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
      {
        // If request is cancelled, the result arrays are empty.
        if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
        {
          // permission was granted
          mGoogleMap.setMyLocationEnabled(true);
        }
        //else
        //{
          // permission denied
        //}
      }

      // other 'case' lines to check for other
      // permissions this app might request
    }
  }

  @Override
  public void onLocationSelected(String location)
  {
    if (mGoogleMap != null)
    {
      Geocoder geocoder = new Geocoder(getApplicationContext());

      List<Address> addressList;
      try
      {
        addressList = geocoder.getFromLocationName(location, 1);
      }
      catch (IOException e)
      {
        Log.e(TAG, "Could not find address " + location);
        return;
      }

      // place the marker on the map
      if ((addressList != null) && !addressList.isEmpty())
      {
        Address address = addressList.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(location);
        mGoogleMap.addMarker(markerOptions);

        // navigate to the point
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mGoogleMap.getCameraPosition().zoom));
      }
    }
  }
}

