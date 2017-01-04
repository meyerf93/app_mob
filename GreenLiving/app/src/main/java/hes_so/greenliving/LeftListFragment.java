package hes_so.greenliving;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import hes_so.greenliving.Fuctionality.CustomFunctionality;
import hes_so.greenliving.Fuctionality.CustomSubFunctionality;
import hes_so.greenliving.Fuctionality.FunctionalityAdapter;

/**
 * Created by Florian.Meyer on 14.12.2016.
 */

public class LeftListFragment extends Fragment {

    // déclaration des sous liste de fonctionalité
    private ArrayList<CustomSubFunctionality> subList1;
    private ArrayList<CustomSubFunctionality> subList2;
    private ArrayList<CustomSubFunctionality> subList3;
    private ArrayList<CustomSubFunctionality> subList4;

    private ArrayList<CustomFunctionality> list;
    private ExpandableListView left_list;

    String LOG = "sublistLog";

    interface OnLeftListFragmentInteractionListener{
        void OnLeftListFragmentInteraction(int index);
    }

    public LeftListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<CustomFunctionality>();

        subList1 = new ArrayList<CustomSubFunctionality>();
        subList2 = new ArrayList<CustomSubFunctionality>();
        subList3 = new ArrayList<CustomSubFunctionality>();
        subList4 = new ArrayList<CustomSubFunctionality>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_list_fragment, container,false);
        //generate view
        FunctionalityAdapter functionalityAdapter = init_list(list);
        left_list = (ExpandableListView) view.findViewById(R.id.left_list);
        left_list.setIndicatorBounds(0,100);
        left_list.setAdapter(functionalityAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private FunctionalityAdapter init_list(ArrayList<CustomFunctionality> list){
        /* contenu de la liste de fonctionalité
        <string name="func_1">Chauffage</string>
        <string name="func_2">Isolation</string>
        <string name="func_3">Production</string>
        <string name="func_4">Electroménager</string>
        <string name="func_5">Ventilation</string>*/

        //crée la première sous liste

        init_sub_list(subList1);
        //ajoute la première fonctionalité custom
        list.add(new CustomFunctionality(
                ((Integer) 1).longValue(),
                getString(R.string.func_1),
                BitmapFactory.decodeResource(getContext().getResources(),R.drawable.temperature),
                subList1
        ));
        //crée la deuxième sous liste
        init_sub_isolation(subList2);
        //ajoute la deuxième fonctionalité custom
        list.add(new CustomFunctionality(
                ((Integer) 2).longValue(),
                getString(R.string.func_2),
                BitmapFactory.decodeResource(getContext().getResources(),R.drawable.isolation_3),
                subList2
        ));
        //crée la troisième sous liste
        init_sub_list(subList3);
        //ajoute la troisième fonctionalité customs
        list.add(new CustomFunctionality(
                ((Integer) 3).longValue(),
                getString(R.string.func_3),
                BitmapFactory.decodeResource(getContext().getResources(),R.drawable.electricity),
                subList3
        ));
        //crée la quatrième sous liste
        init_sub_list(subList4);
        //ajoute la troisième fonctionalité customs
        list.add(new CustomFunctionality(
                ((Integer) 4).longValue(),
                getString(R.string.func_4),
                BitmapFactory.decodeResource(getContext().getResources(),R.drawable.appliances),
                subList4
        ));
        //ajoute crée l'adapter pour la liste complète
        FunctionalityAdapter functionalityAdapter = new FunctionalityAdapter(getContext(),
                R.layout.custom_func_cell,list);
        return  functionalityAdapter;
    }

    private void init_sub_list(ArrayList<CustomSubFunctionality> subList){
        //ajoute le premier éléments à la sous liste
        subList.add(new CustomSubFunctionality(
                ((Integer) 1).longValue(),
                BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.add_elements),
                BitmapFactory.decodeResource(getContext().getResources(),R.drawable.flux)));
        //ajoute le deuxième éléments a la sous lsite
        subList.add(new CustomSubFunctionality(
                ((Integer) 2).longValue(),
                BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.add_elements),
                BitmapFactory.decodeResource(getContext().getResources(),R.drawable.light)));
    }

    private void init_sub_isolation(ArrayList<CustomSubFunctionality> subList){
        //ajoute le premier éléments à la sous liste
        subList.add(new CustomSubFunctionality(
                ((Integer) 1).longValue(),
                BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.add_elements),
                BitmapFactory.decodeResource(getContext().getResources(),R.drawable.isolation_1)));
        //ajoute le deuxième éléments a la sous liste
        subList.add(new CustomSubFunctionality(
                ((Integer) 2).longValue(),
                BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.add_elements),
                BitmapFactory.decodeResource(getContext().getResources(),R.drawable.isolation_2)));
        //ajout d'un élément a la liste
        subList.add(new CustomSubFunctionality(
                ((Integer) 3).longValue(),
                BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.add_elements),
                BitmapFactory.decodeResource(getContext().getResources(),R.drawable.isolation_3)));
        //ajout d'un élément a la liste
        subList.add(new CustomSubFunctionality(
                ((Integer) 4).longValue(),
                BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.add_elements),
                BitmapFactory.decodeResource(getContext().getResources(),R.drawable.simple_vitrage)));
        //ajout d'un élément a la liste
        subList.add(new CustomSubFunctionality(
                ((Integer) 5).longValue(),
                BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.add_elements),
                BitmapFactory.decodeResource(getContext().getResources(),R.drawable.double_vitrage)));
        //ajout d'un élément a la liste
        subList.add(new CustomSubFunctionality(
                ((Integer) 5).longValue(),
                BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.add_elements),
                BitmapFactory.decodeResource(getContext().getResources(),R.drawable.triple_vitrage)));
    }
}
