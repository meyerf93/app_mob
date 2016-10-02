package ch.heia.mobiledev.uribeacon;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.nio.charset.StandardCharsets;

public class BeaconListFragment extends ListFragment{

    // used for logging
    private static final String TAG = BeaconListFragment.class.getSimpleName();

    // variables used for display
    private final String Beacon_name[] = {"","",""};
    private final UriBeacon Beacons[] = {null,null,null};

    // Required empty public constructor
    public BeaconListFragment()
    {

    }

    @Override
    public void onAttach(Context context)
    {
        Log.d(TAG, "onAttach()");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
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
        Log.d(TAG, "onStop()");
        super.onStop();
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
    public void onDestroyView()
    {
        Log.d(TAG, "onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        Log.d(TAG, "onDetach()");
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView()");

        View view = inflater.inflate(R.layout.beacon_list_fragment, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Beacon_name);
        setListAdapter(adapter);

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Log.d(TAG, "onListItemClick()");

        String temp_flag = new String(Beacons[position].getFlags(), StandardCharsets.US_ASCII);
        String temp_pow = new String(Beacons[position].getPow(),StandardCharsets.US_ASCII);
        BeaconDetailsFragment txt = (BeaconDetailsFragment) getFragmentManager().findFragmentById(R.id.beacon_details_fragment);
        txt.change(temp_flag,temp_pow,Beacons[position].getURL(),Beacons[position].getImage());

        getListView().setSelector(android.R.color.holo_blue_dark);
        getListView().invalidateViews();
    }
}
