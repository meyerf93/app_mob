package ch.heia.mobiledev.uribeacon;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BeaconDetailsFragment extends Fragment {
    // used for logging
    final private static String TAG = BeaconDetailsFragment.class.getSimpleName();

    // views created by inflate() call in onCreateView()
    private TextView mFlags;
    private TextView mPow;
    private TextView mURL;
    private ImageView mImage;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.beacon_details_fragment, container, false);

        mFlags = (TextView) view.findViewById(R.id.flags_tx);
        mPow = (TextView) view.findViewById(R.id.power_tx);
        mURL = (TextView) view.findViewById(R.id.url_tx);
        mImage = (ImageView) view.findViewById(R.id.image_tx);
        return view;
    }
    public void change(String flag, String pow,String url,ImageView image)
    {
        Log.d(TAG, "change");

        if (mFlags != null)
        {
            mFlags.setText(flag);
        }
        if (mPow != null)
        {
            mPow.setText(pow);
        }
        if(mURL!=null){
            mURL.setText(url);
        }
        if(mImage !=null){
            mImage = image;
        }
    }
}
