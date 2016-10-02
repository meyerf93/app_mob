package ch.heia.mobiledev.launchactivity;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class OSListFragment extends ListFragment
{
  // used for logging
  private static final String TAG = OSListFragment.class.getSimpleName();

  // variables used for display
  private final String[] AndroidOS = new String[] { "Cupcake","Donut","Eclair","Froyo","Gingerbread","Honeycomb","Ice Cream Sandwich","Jelly Bean","KitKat", "Lollipop", "Marshmallow" };
  private final String[] Version = new String[]{"1.5","1.6","2.0-2.1","2.2","2.3","3.0-3.2","4.0","4.1-4.3","4.4", "5.0-5.1", "6.0"};

  // Required empty public constructor
  public OSListFragment()
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

    View view = inflater.inflate(R.layout.list_fragment, container, false);

    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, AndroidOS);
    setListAdapter(adapter);

    return view;
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id)
  {
    Log.d(TAG, "onListItemClick()");

    TextFragment txt = (TextFragment) getFragmentManager().findFragmentById(R.id.text_fragment);
    txt.change(AndroidOS[position], "Version : " + Version[position]);

    getListView().setSelector(android.R.color.holo_blue_dark);
    getListView().invalidateViews();
  }
}
