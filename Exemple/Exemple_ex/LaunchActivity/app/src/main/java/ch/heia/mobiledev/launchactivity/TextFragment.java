package ch.heia.mobiledev.launchactivity;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextFragment extends Fragment
{
  // used for logging
  final private static String TAG = TextFragment.class.getSimpleName();

  // views created by inflate() call in onCreateView()
  private TextView mTextView;
  private TextView mVersionView;

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

    View view = inflater.inflate(R.layout.text_fragment, container, false);

    mTextView = (TextView) view.findViewById(R.id.AndroidOs);
    mVersionView = (TextView)view.findViewById(R.id.Version);

    return view;
  }

  public void change(String txt, String version)
  {
    Log.d(TAG, "change");

    if (mTextView != null)
    {
      mTextView.setText(txt);
    }
    if (mVersionView != null)
    {
      mVersionView.setText(version);
    }
  }

}
