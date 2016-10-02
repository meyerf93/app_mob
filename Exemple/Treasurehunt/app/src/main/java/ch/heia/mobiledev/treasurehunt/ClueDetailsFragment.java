package ch.heia.mobiledev.treasurehunt;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ClueDetailsFragment extends Fragment {
    // used for logging
    private static final String TAG = ClueDetailsFragment.class.getSimpleName();

    static private final View[] past_view = {null,null,null,null};
    static private Beacon beacon;
    static private int Emplacement = 0;

    // Required empty public constructor
    public ClueDetailsFragment()
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

        View view = inflater.inflate(R.layout.clue_details_fragment, container, false);

        TextView main_line = (TextView) view.findViewById(R.id.clue_name);
        TextView ligne1 = (TextView) view.findViewById(R.id.clue_ligne_1);

        if(MainScreenActivity.listBeacon[0] == null) {
            main_line.setText(getActivity().getResources().getString(R.string.no_beacon));
            ligne1.setVisibility(View.INVISIBLE);
        }
        else if(beacon==null){
            Log.v(TAG,"no beacon selected");
            main_line.setVisibility(View.INVISIBLE);
            ligne1.setVisibility(View.INVISIBLE);
        }
        else{
            main_line.setText(getActivity().getResources().getString(beacon.clue_name));
            ligne1.setText(beacon.clue);

            main_line.setVisibility(View.VISIBLE);
            ligne1.setVisibility(View.VISIBLE);
        }
        past_view[Emplacement] = view;

        return past_view[Emplacement];
    }

    public void setText(Beacon beacon){
        Log.v(TAG,"setText");
        ClueDetailsFragment.beacon = beacon;
        setView();
    }

    private void setView(){
        TextView main_line = (TextView)getActivity().findViewById(R.id.clue_name);
        TextView ligne1 = (TextView) getActivity().findViewById(R.id.clue_ligne_1);

        main_line.setText(getActivity().getResources().getString(beacon.clue_name));
        ligne1.setText(beacon.clue);

        main_line.setVisibility(View.VISIBLE);
        ligne1.setVisibility(View.VISIBLE);
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
