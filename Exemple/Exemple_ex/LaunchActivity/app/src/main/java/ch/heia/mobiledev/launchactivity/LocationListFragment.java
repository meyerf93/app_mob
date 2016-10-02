package ch.heia.mobiledev.launchactivity;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LocationListFragment extends ListFragment
{
  // registered listener
  private LocationListFragmentListener mListener;

  // for providing a list of locations
  private final String[] Location = new String[] { "Haute école d'ingénierie et d'architecture de Fribourg, Bd de Pérolles 80, Fribourg, Switzerland, 1705",
                                                   "Boulevard de Pérolles 1, Fribourg, Switzerland, 1700",
                                                   "Bahnhofstrasse 1, Zermatt, Switzerland, 3920"};

  // interface to be implemented by the activity hosting the fragment
  public interface LocationListFragmentListener
  {
    // for updating the location on the map
    void onLocationSelected(String location);
  }

  // Required empty public constructor
  public LocationListFragment()
  {
  }

  @Override
  public void onAttach(Context context)
  {
    super.onAttach(context);
    try
    {
      if (context instanceof Activity)
      {
        mListener = (LocationListFragmentListener) context;
      }
    }
    catch (ClassCastException e)
    {
      throw new ClassCastException(context.toString() + " must implement LocationListFragmentListener");
    }
  }

  @Override
  public void onDetach()
  {
    mListener = null;
    super.onDetach();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState)
  {
    View view = inflater.inflate(R.layout.location_list_fragment, container, false);

    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Location);
    setListAdapter(adapter);

    return view;
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id)
  {
    // move to the appropriate location on the map

    // get the map fragment
    if (mListener != null)
    {
      mListener.onLocationSelected(Location[position]);
    }
  }
}

