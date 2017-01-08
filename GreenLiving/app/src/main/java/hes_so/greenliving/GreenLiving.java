package hes_so.greenliving;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.ArrayList;

import hes_so.greenliving.Fuctionality.FunctionalityAdapter;
import hes_so.greenliving.Resume.CustomResume;
import hes_so.greenliving.UI.CustomUI;


public class GreenLiving extends FragmentActivity
        implements FunctionalityAdapter.OnLeftAdapterIntecationListener {

    private boolean LeftListFragment = false;
    private boolean RightListFragment = false;
    private boolean MiddleUIFragment = false;


    private ArrayList<CustomResume> right_list_resume;
    private ArrayList<CustomUI> first_middle_list_ui;

    private LeftListFragment leftListFragment;
    private MainUIFragment mainUIFragment;
    private RightListFragment rightListFragment;

    private String LOG = "log_GreenLiving";

    private FragmentManager fragmentManager;

    private static final String TAG = "GreenLiving";


    @Override
    public void OnLeftAdapterInteraction(int command,long listItemId, long listSubItemId) {
        switch (command){
            case FunctionalityAdapter.MSG_ADD:
                Log.v(LOG,"ADD button pressed id : "+listItemId+", subId : "+listSubItemId);
                switch (((Long)listItemId).intValue()){
                    case hes_so.greenliving.LeftListFragment.ISOLATION_INDEX:
                        switch (((Long)listSubItemId).intValue()){
                            case hes_so.greenliving.LeftListFragment.SIMPLE_VITRAGE_INDEX:
                                Log.v(LOG,"on simple vitrage case");
                                first_middle_list_ui.get(0).setLeftWindow(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.simple_vitrage));
                                first_middle_list_ui.get(0).setLeftWindowVisible(true);
                                updateMainUIFragment();
                                break;
                            case hes_so.greenliving.LeftListFragment.DOUBLE_VITRAGE_INDEX:
                                first_middle_list_ui.get(0).setLeftWindow(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.double_vitrage));
                                first_middle_list_ui.get(0).setLeftWindowVisible(true);

                                break;
                            case hes_so.greenliving.LeftListFragment.TRIPLE_VITRAGE_INDEX:
                                first_middle_list_ui.get(0).setLeftWindow(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.triple_vitrage));
                                first_middle_list_ui.get(0).setLeftWindowVisible(true);
                                break;
                            case hes_so.greenliving.LeftListFragment.SIMPLE_ISOLATION_INDEX:
                                first_middle_list_ui.get(0).setLeftFirstIsolation(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.isolation_1));
                                first_middle_list_ui.get(0).setLeftFirstIsolationVisible(true);
                                break;
                            case hes_so.greenliving.LeftListFragment.DOUBLE_ISOLATION_INDEX:
                                first_middle_list_ui.get(0).setLeftSecondIsolation(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.isolation_2));
                                first_middle_list_ui.get(0).setLeftSecondIsolationVisible(true);
                                break;
                            case hes_so.greenliving.LeftListFragment.TRIPLE_ISOLATION_INDEX:
                                first_middle_list_ui.get(0).setLeftThirdIsolation(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.isolation_3));
                                first_middle_list_ui.get(0).setLeftThirdIsolationVisible(true);
                                break;
                            default:
                                break;
                        }
                        break;
                    case hes_so.greenliving.LeftListFragment.CHAUFFAGE_INDEX:
                        break;
                    case hes_so.greenliving.LeftListFragment.PRODUCTION_INDEX:
                        break;
                    case hes_so.greenliving.LeftListFragment.ELECTROMENAGER_INDEX:
                        break;
                    default:
                        break;
                }
                break;
            case FunctionalityAdapter.MSG_REMOVE:

                break;
            default:
                break;
        }
    }

    private void updateMainUIFragment(){
        final FragmentTransaction ft = fragmentManager.beginTransaction();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            ft.replace(R.id.main_ui_fragment,mainUIFragment);
            ft.commit();
        }
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

            //ft.add(R.id.main_fragment,leftListFragment);
            //ft.add(R.id.main_fragment,rightListFragment);
            ft.replace(R.id.main_fragment,mainUIFragment);
            ft.commit();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_green_living);

        fragmentManager = getFragmentManager();

        leftListFragment = new LeftListFragment();

        mainUIFragment = new MainUIFragment();

        rightListFragment = new RightListFragment();


        // Init the right list for fragment --------------------------------------------------------
        right_list_resume = new ArrayList<>();
        final CustomResume item = new CustomResume(
                BitmapFactory.decodeResource(getResources(), R.drawable.triple_vitrage),
                10,
                3,
                4,
                2,
                1,
                "Pleins de triple vitrage oupi !"
        );
        final CustomResume item_2 = new CustomResume(
                BitmapFactory.decodeResource(getResources(), R.drawable.double_vitrage),
                20,
                3,
                4,
                1,
                3,
                "Pleins de fenêtre oupi !"
        );

        right_list_resume.add(item);
        right_list_resume.add(item_2);
        rightListFragment.setList(right_list_resume);
        //------------------------------------------------------------------------------------------
        // Init the middle list for mainUIFragment--------------------------------------------------
        first_middle_list_ui = new ArrayList<>();
        //cération d'un deuxième éléments d'exmple dans la list principale
        /*final CustomUI item1 = new CustomUI(
                BitmapFactory.decodeResource(getResources(),R.drawable.bas_maison),
                null,false,
                null,true,
                null,true,

                BitmapFactory.decodeResource(getResources(),R.drawable.double_vitrage),true,
                null,false,
                null,true,

                null,true,
                null,false,
                null,false
        ); */

        final CustomUI item2 = new CustomUI(
                BitmapFactory.decodeResource(getResources(),R.drawable.bas_maison),

                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_3),false,
                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_2),false,
                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_1),false,

                BitmapFactory.decodeResource(getResources(),R.drawable.simple_vitrage),false,
                BitmapFactory.decodeResource(getResources(),R.drawable.double_vitrage),false,
                BitmapFactory.decodeResource(getResources(),R.drawable.triple_vitrage),false,

                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_1),false,
                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_2),false,
                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_3),false
        );
        //------------------------------------------------------------------------------------------
        //
        //first_middle_list_ui.add(item1);
        first_middle_list_ui.add(item2);
        mainUIFragment.setFirstlist(first_middle_list_ui);
        Log.v(LOG,"on get view called");

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        Log.v(LOG,"on resume called");

        super.onResume();

        final FragmentTransaction ft = fragmentManager.beginTransaction();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            ft.add(R.id.left_list_fragment,leftListFragment);
            ft.add(R.id.right_list_fragment,rightListFragment);
            ft.add(R.id.main_ui_fragment,mainUIFragment);
            ft.commit();
        }
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

            //ft.add(R.id.main_fragment,leftListFragment);
            //ft.add(R.id.main_fragment,rightListFragment);
            ft.add(R.id.main_fragment,mainUIFragment);
            ft.commit();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        final FragmentTransaction ft = fragmentManager.beginTransaction();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            ft.remove(leftListFragment);
            ft.remove(rightListFragment);
            ft.remove(mainUIFragment);
            ft.commit();
        }
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

            ft.remove(leftListFragment);
            ft.remove(rightListFragment);
            ft.remove(mainUIFragment);
            ft.commit();

        }
    }
}