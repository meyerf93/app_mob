package hes_so.greenliving;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import hes_so.greenliving.UI.CustomUI;
import hes_so.greenliving.UI.UIAdapter;

/**
 * Created by Florian.Meyer on 14.12.2016.
 */

public class MainUIFragment extends Fragment {

    private ArrayList<CustomUI> firstlist;

    interface OnMainUIFragmentInteractionListenerListener{
        void OnMainUIFragmentInteraction(int index);
    }

    public MainUIFragment() {}

    // Getter/setter -------------------------------------------------------------------------------

    public ArrayList<CustomUI> getFirstlist() {
        return firstlist;
    }

    public void setFirstlist(ArrayList<CustomUI> firstlist) {
        this.firstlist = firstlist;
    }

    //----------------------------------------------------------------------------------------------

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v("Main_fragment_ui","on create view called");
        View view = inflater.inflate(R.layout.main_ui_fragment,container,false);

        UIAdapter firstUiAdapter = new UIAdapter(getContext(),R.layout.custom_ui,firstlist);
        ListView first_middle_list = (ListView) view.findViewById(R.id.first_list_main_ui);
        first_middle_list.setAdapter(firstUiAdapter);

        return view;
    }

}
