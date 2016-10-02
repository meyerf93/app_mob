package ch.heia.mobiledev.treasurehunt;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class BeaconOverviewFragment extends Fragment {
    // used for logging
    private static final String TAG = BeaconOverviewFragment.class.getSimpleName();

    // Required empty public constructor
    public BeaconOverviewFragment()
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
        View actual_view = inflater.inflate(R.layout.beacon_overview_fragment, container, false);

        Button button[] = {(Button)actual_view.findViewById(R.id.beacon_1),(Button)actual_view.findViewById(R.id.beacon_2)
                ,(Button)actual_view.findViewById(R.id.beacon_3),(Button)actual_view.findViewById(R.id.beacon_4)};

        int length = MainScreenActivity.listBeacon.length;

        for(int i = 0; i<length;++i){
            if(MainScreenActivity.listBeacon[i]==null) button[i].setVisibility(View.INVISIBLE);
            else{
                button[i].setVisibility(View.VISIBLE);
                final int j = i;
                button[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BeaconDetailsFragment temp =(BeaconDetailsFragment) getActivity().getFragmentManager().findFragmentById(R.id.beacon_details_frag);
                        temp.setEmplacementView(j);
                        temp.setText(MainScreenActivity.listBeacon[j]);
                    }
                });
            }
        }

        return  actual_view;
    }
}
