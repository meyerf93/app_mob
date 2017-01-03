package hes_so.greenliving;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import hes_so.greenliving.Resume.CustomResume;
import hes_so.greenliving.Resume.ResumeAdapter;

/**
 * Created by Florian.Meyer on 14.12.2016.
 */

public class RightListFragment extends Fragment {

    private ArrayList<CustomResume> list;
    private ListView right_list;

    private String LOG = "rightFragment_log";


    interface OnRightListFragmentInteractionListener{
        void OnRightListFragmentInteraction (int index);
    }

    public RightListFragment() {}


    public ArrayList<CustomResume> getList() {
        return list;
    }

    public void setList(ArrayList<CustomResume> list) {
        Log.v(LOG,"set list right fragment");

        this.list = list;
    }

    public void addItemToList(CustomResume item){
        list.add(item);
    }

    public void removeItemToList(int index){
        list.remove(index);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right_list_fragment, container,false);

        ResumeAdapter resumeAdapter = new ResumeAdapter(getContext(),R.layout.custom_resume,list);
        right_list = (ListView) view.findViewById(R.id.right_list);
        right_list.setAdapter(resumeAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
