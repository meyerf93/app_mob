package ch.heia.mobiledev.treasurehunt;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BeaconDetailsFragment extends Fragment {
    // used for logging
    private static final String TAG = BeaconDetailsFragment.class.getSimpleName();

    static private final View[] past_view = {null,null,null,null};
    static private Beacon beacon;
    static private int Emplacement = 0;

    // Required empty public constructor
    public BeaconDetailsFragment()
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
        Log.d(TAG, "onResume() emplacement : "+Emplacement);
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

        View view = inflater.inflate(R.layout.beacon_details_fragment, container, false);

        TextView main_line = (TextView) view.findViewById(R.id.beacon_name);
        TextView UUID = (TextView) view.findViewById(R.id.beacon_ligne_1);
        TextView Major = (TextView) view.findViewById(R.id.beacon_ligne_2);
        TextView Minor = (TextView) view.findViewById(R.id.beacon_ligne_3);
        TextView Pow = (TextView) view.findViewById(R.id.beacon_ligne_4);
        TextView Dist = (TextView) view.findViewById(R.id.beacon_ligne_5);
        TextView Step = (TextView) view.findViewById(R.id.beacon_ligne_6);
        TextView Position = (TextView) view.findViewById(R.id.beacon_ligne_7);

        if(MainScreenActivity.listBeacon[0] == null) {
            main_line.setText(getActivity().getResources().getString(R.string.no_beacon));
            UUID.setVisibility(View.INVISIBLE);
            Major.setVisibility(View.INVISIBLE);
            Minor.setVisibility(View.INVISIBLE);
            Pow.setVisibility(View.INVISIBLE);
            Dist.setVisibility(View.INVISIBLE);
            Step.setVisibility(View.INVISIBLE);
            Position.setVisibility(View.INVISIBLE);
        }
        else if(beacon==null){
            Log.v(TAG,"no beacon selected");
            main_line.setVisibility(View.INVISIBLE);
            UUID.setVisibility(View.INVISIBLE);
            Major.setVisibility(View.INVISIBLE);
            Minor.setVisibility(View.INVISIBLE);
            Pow.setVisibility(View.INVISIBLE);
            Dist.setVisibility(View.INVISIBLE);
            Step.setVisibility(View.INVISIBLE);
            Position.setVisibility(View.INVISIBLE);
        }
        else{
            main_line.setText(getActivity().getResources().getString(beacon.name));
            UUID.setText(getActivity().getResources().getString(R.string.beacon_ligne_1_text)+beacon.uuid.toString());
            Major.setText(getActivity().getResources().getString(R.string.beacon_ligne_2_text)+Integer.toString(beacon.Major));
            Minor.setText(getActivity().getResources().getString(R.string.beacon_ligne_3_text)+Integer.toString(beacon.Minor));
            Pow.setText(getActivity().getResources().getString(R.string.beacon_ligne_4_text)+Integer.toString((int)beacon.Pow));
            Dist.setText(getActivity().getResources().getString(R.string.beacon_ligne_5_text)+Double.toString(beacon.dist));
            Step.setText(getActivity().getResources().getString(R.string.beacon_ligne_6_text)+Integer.toString(beacon.step));
            Position.setText(getActivity().getResources().getString(R.string.beacon_ligne_7_text)+beacon.position.toString());

            main_line.setVisibility(View.VISIBLE);
            UUID.setVisibility(View.VISIBLE);
            Major.setVisibility(View.VISIBLE);
            Minor.setVisibility(View.VISIBLE);
            Pow.setVisibility(View.VISIBLE);
            Dist.setVisibility(View.VISIBLE);
            Step.setVisibility(View.VISIBLE);
            Position.setVisibility(View.VISIBLE);
        }
        past_view[Emplacement] = view;

        return past_view[Emplacement];
    }

    public void setText(Beacon beacon){
        Log.v(TAG,"setText");
        BeaconDetailsFragment.beacon = beacon;
        setView();
    }

    private void setView(){
        TextView main_line = (TextView)getActivity().findViewById(R.id.beacon_name);
        TextView UUID = (TextView) getActivity().findViewById(R.id.beacon_ligne_1);
        TextView Major = (TextView) getActivity().findViewById(R.id.beacon_ligne_2);
        TextView Minor = (TextView) getActivity().findViewById(R.id.beacon_ligne_3);
        TextView Pow = (TextView)  getActivity().findViewById(R.id.beacon_ligne_4);
        TextView Dist = (TextView) getActivity().findViewById(R.id.beacon_ligne_5);
        TextView Step = (TextView) getActivity().findViewById(R.id.beacon_ligne_6);
        TextView Position = (TextView) getActivity().findViewById(R.id.beacon_ligne_7);

        main_line.setText(getActivity().getResources().getString(beacon.name));
        UUID.setText(getActivity().getResources().getString(R.string.beacon_ligne_1_text)+beacon.uuid.toString());
        Major.setText(getActivity().getResources().getString(R.string.beacon_ligne_2_text)+Integer.toString(beacon.Major));
        Minor.setText(getActivity().getResources().getString(R.string.beacon_ligne_3_text)+Integer.toString(beacon.Minor));
        Pow.setText(getActivity().getResources().getString(R.string.beacon_ligne_4_text)+Integer.toString((int)beacon.Pow));
        Dist.setText(getActivity().getResources().getString(R.string.beacon_ligne_5_text)+Double.toString(beacon.dist));
        Step.setText(getActivity().getResources().getString(R.string.beacon_ligne_6_text)+Integer.toString(beacon.step));
        Position.setText(getActivity().getResources().getString(R.string.beacon_ligne_7_text)+beacon.position.toString());

        main_line.setVisibility(View.VISIBLE);
        UUID.setVisibility(View.VISIBLE);
        Major.setVisibility(View.VISIBLE);
        Minor.setVisibility(View.VISIBLE);
        Pow.setVisibility(View.VISIBLE);
        Dist.setVisibility(View.VISIBLE);
        Step.setVisibility(View.VISIBLE);
        Position.setVisibility(View.VISIBLE);
    }

    public void setEmplacementView(int i){
        Log.v(TAG,"setEmplacementView");
        Emplacement = i;
    }

    static public void cleanVar(){
        Emplacement=0;
        beacon = null;
        past_view[0]=null;
        past_view[1]=null;
        past_view[2]=null;
        past_view[3]=null;
    }
}
