package hes_so.greenliving;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hes_so.greenliving.Fuctionality.CustomFunctionality;
import hes_so.greenliving.Fuctionality.CustomSubFunctionality;
import hes_so.greenliving.Fuctionality.FunctionalityAdapter;
import hes_so.greenliving.Fuctionality.SubFunctionalityAdapter;


/**
 * Created by Florian.Meyer on 14.12.2016.
 */

public class LeftListFragment extends Fragment {

    ArrayList<CustomSubFunctionality> customSubFunctionalityArrayList;
    ArrayList<CustomFunctionality> customFunctionalityArrayList;
    ListView left_list;



    interface OnLeftListFragmentInteractionListener{
        void OnLeftListFragmentInteraction(int index);
    }

    public LeftListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_list_fragment, container,false);
        //generate view
        FunctionalityAdapter functionalityAdapter = init_list();
        left_list = (ListView) view.findViewById(R.id.left_list);
        left_list.setAdapter(functionalityAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private FunctionalityAdapter init_list(){
        //peuple la liste principale
        customFunctionalityArrayList = new ArrayList<CustomFunctionality>();
        SubFunctionalityAdapter subFunctionalityAdapter = init_sub_list();
        customFunctionalityArrayList.add(new CustomFunctionality(
                "Electricity",
                BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.ic_green_batterys),
                customSubFunctionalityArrayList
        ));
        FunctionalityAdapter functionalityAdapter = new FunctionalityAdapter(getContext(),
                R.layout.custom_func_cell,customFunctionalityArrayList,
                subFunctionalityAdapter);
        return  functionalityAdapter;
    }

    private SubFunctionalityAdapter init_sub_list(){
        //peuple la list de sub func
        customSubFunctionalityArrayList = new ArrayList<CustomSubFunctionality>();
        customSubFunctionalityArrayList.add(new CustomSubFunctionality(
                BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.ic_electricity),
                BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.ic_flux)));
        customSubFunctionalityArrayList.add(new CustomSubFunctionality(
                BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.ic_appliances),
                BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.ic_light)));
        // some more adds...
        SubFunctionalityAdapter subFunctionalityAdapter = new SubFunctionalityAdapter(getContext(),R.layout.custom_sub_func_cell,customSubFunctionalityArrayList);
        return subFunctionalityAdapter;
    }
}
