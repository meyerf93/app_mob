package hes_so.greenliving;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

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
        //Log.v(LOG,"ADD button pressed id : "+listItemId+", subId : "+listSubItemId);
        CustomUI item_main_ui = first_middle_list_ui.get(0);

        CustomResume temp_item_0 = right_list_resume.get(0);
        CustomResume temp_item_1 = right_list_resume.get(1);
        CustomResume temp_item_2 = right_list_resume.get(2);

        switch (((Long)listItemId).intValue()){
            // Isolation Index  --------------------------------------------------------------------
            case hes_so.greenliving.LeftListFragment.ISOLATION_INDEX:
                switch (((Long)listSubItemId).intValue()){
                    //------------------------------------------------------------------------------
                    case hes_so.greenliving.LeftListFragment.SIMPLE_VITRAGE:
                        switch (command) {
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_ADD_LEFT:
                                item_main_ui.setLeftWindowVisible(true);
                                if(temp_item_1.getWestResume() == 0 && temp_item_2.getWestResume()==0){
                                    item_main_ui.setLeftWindow(
                                            BitmapFactory.decodeResource(getResources(),
                                                    R.drawable.simple_vitrage));
                                    item_main_ui.setLeftTypeWindow(

                                            hes_so.greenliving.LeftListFragment.SIMPLE_VITRAGE);
                                }
                                temp_item_0.setWestResume(temp_item_0.getWestResume() + 1);
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_REMOVE_LEFT:
                                if (temp_item_0.getWestResume() != 0) {
                                    temp_item_0.setWestResume(temp_item_0.getWestResume() - 1);
                                    if (temp_item_2.getWestResume() == 0) {
                                        if (temp_item_1.getWestResume() == 0) {
                                            if (temp_item_0.getWestResume() == 0) {
                                                item_main_ui.setLeftWindowVisible(false);
                                            } else {
                                                item_main_ui.setLeftWindow(
                                                        BitmapFactory.decodeResource(getResources(),
                                                                R.drawable.simple_vitrage));
                                                item_main_ui.setLeftTypeWindow(
                                                        hes_so.greenliving.LeftListFragment.SIMPLE_VITRAGE);
                                            }
                                        } else {
                                            item_main_ui.setLeftWindow(
                                                    BitmapFactory.decodeResource(getResources(),
                                                            R.drawable.double_vitrage));
                                            item_main_ui.setLeftTypeWindow(
                                                    hes_so.greenliving.LeftListFragment.DOUBLE_VITRAGE);
                                        }
                                    }
                                }
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_ADD_RIGHT:
                                item_main_ui.setRightWindowVisible(true);
                                if(temp_item_1.getEastResume() == 0 && temp_item_2.getEastResume()== 0){
                                    item_main_ui.setRightWindow(
                                            BitmapFactory.decodeResource(getResources(),
                                                    R.drawable.simple_vitrage));
                                    item_main_ui.setRightTypeWindow(
                                            hes_so.greenliving.LeftListFragment.SIMPLE_VITRAGE);
                                }

                                temp_item_0.setEastResume(temp_item_0.getEastResume()+1);
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_REMOVE_RIGHT:
                                if (temp_item_0.getEastResume() != 0) {
                                    temp_item_0.setEastResume(temp_item_0.getEastResume() - 1);
                                    if (temp_item_2.getEastResume() == 0) {
                                        if (temp_item_1.getEastResume() == 0) {
                                            if (temp_item_0.getEastResume() == 0) {
                                                item_main_ui.setRightWindowVisible(false);
                                            } else {
                                                item_main_ui.setRightWindow(
                                                        BitmapFactory.decodeResource(getResources(),
                                                                R.drawable.simple_vitrage));
                                                item_main_ui.setRightTypeWindow(
                                                        hes_so.greenliving.LeftListFragment.SIMPLE_VITRAGE);
                                            }
                                        } else {
                                            item_main_ui.setRightWindow(
                                                    BitmapFactory.decodeResource(getResources(),
                                                            R.drawable.double_vitrage));
                                            item_main_ui.setRightTypeWindow(
                                                    hes_so.greenliving.LeftListFragment.DOUBLE_VITRAGE);
                                        }
                                    }
                                }
                            //----------------------------------------------------------------------
                            default:
                                break;
                        }
                        break;
                    //------------------------------------------------------------------------------
                    case hes_so.greenliving.LeftListFragment.DOUBLE_VITRAGE:
                        switch (command) {
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_ADD_LEFT:
                                item_main_ui.setLeftWindowVisible(true);
                                if(temp_item_2.getWestResume() == 0){
                                    item_main_ui.setLeftWindow(
                                            BitmapFactory.decodeResource(getResources(),
                                                    R.drawable.double_vitrage));
                                    item_main_ui.setLeftTypeWindow(
                                            hes_so.greenliving.LeftListFragment.DOUBLE_VITRAGE);
                                }
                                temp_item_1.setWestResume(temp_item_1.getWestResume() + 1);
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_REMOVE_LEFT:
                                if (temp_item_1.getWestResume() != 0) {
                                    temp_item_1.setWestResume(temp_item_1.getWestResume() - 1);
                                    if (temp_item_2.getWestResume() == 0) {
                                        if (temp_item_1.getWestResume() == 0) {
                                            if (temp_item_0.getWestResume() == 0) {
                                                item_main_ui.setLeftWindowVisible(false);
                                            } else {
                                                item_main_ui.setLeftWindow(
                                                        BitmapFactory.decodeResource(getResources(),
                                                                R.drawable.simple_vitrage));
                                                item_main_ui.setLeftTypeWindow(
                                                        hes_so.greenliving.LeftListFragment.SIMPLE_VITRAGE);
                                            }
                                        } else {
                                            item_main_ui.setLeftWindow(
                                                    BitmapFactory.decodeResource(getResources(),
                                                            R.drawable.double_vitrage));
                                            item_main_ui.setLeftTypeWindow(
                                                    hes_so.greenliving.LeftListFragment.DOUBLE_VITRAGE);
                                        }
                                    }
                                }
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_ADD_RIGHT:
                                item_main_ui.setRightWindowVisible(true);
                                if(temp_item_2.getEastResume() == 0){
                                    item_main_ui.setRightWindow(
                                            BitmapFactory.decodeResource(getResources(),
                                                    R.drawable.double_vitrage));
                                    item_main_ui.setRightTypeWindow(
                                            hes_so.greenliving.LeftListFragment.DOUBLE_VITRAGE);
                                }
                                temp_item_1.setEastResume(temp_item_1.getEastResume() + 1);
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_REMOVE_RIGHT:
                                if (temp_item_1.getEastResume() != 0) {
                                    temp_item_1.setEastResume(temp_item_1.getEastResume() - 1);
                                    if (temp_item_2.getEastResume() == 0) {
                                        if (temp_item_1.getEastResume() == 0) {
                                            if (temp_item_0.getEastResume() == 0) {
                                                item_main_ui.setRightWindowVisible(false);
                                            } else {
                                                item_main_ui.setRightWindow(
                                                        BitmapFactory.decodeResource(getResources(),
                                                                R.drawable.simple_vitrage));
                                                item_main_ui.setRightTypeWindow(
                                                        hes_so.greenliving.LeftListFragment.SIMPLE_VITRAGE);
                                            }
                                        } else {
                                            item_main_ui.setRightWindow(
                                                    BitmapFactory.decodeResource(getResources(),
                                                            R.drawable.double_vitrage));
                                            item_main_ui.setRightTypeWindow(
                                                    hes_so.greenliving.LeftListFragment.DOUBLE_VITRAGE);
                                        }
                                    }
                                }
                                break;
                            //----------------------------------------------------------------------
                            default:
                                break;
                        }
                        break;
                    //------------------------------------------------------------------------------
                    case hes_so.greenliving.LeftListFragment.TRIPLE_VITRAGE:
                        switch (command) {
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_ADD_LEFT:
                                item_main_ui.setLeftWindowVisible(true);
                                item_main_ui.setLeftWindow(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.triple_vitrage));
                                item_main_ui.setLeftTypeWindow(hes_so.greenliving.LeftListFragment.TRIPLE_VITRAGE);
                                temp_item_2.setWestResume(temp_item_2.getWestResume() + 1);
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_REMOVE_LEFT:
                                if (temp_item_2.getWestResume() != 0) {
                                    temp_item_2.setWestResume(temp_item_2.getWestResume() - 1);
                                    if (temp_item_2.getWestResume() == 0) {
                                        if (temp_item_1.getWestResume() == 0) {
                                            if (temp_item_0.getWestResume() == 0) {
                                                item_main_ui.setLeftWindowVisible(false);
                                            } else {
                                                item_main_ui.setLeftWindow(
                                                        BitmapFactory.decodeResource(getResources(),
                                                                R.drawable.simple_vitrage));
                                                item_main_ui.setLeftTypeWindow(
                                                        hes_so.greenliving.LeftListFragment.SIMPLE_VITRAGE);
                                            }
                                        } else {
                                            item_main_ui.setLeftWindow(
                                                    BitmapFactory.decodeResource(getResources(),
                                                            R.drawable.double_vitrage));
                                            item_main_ui.setLeftTypeWindow(
                                                    hes_so.greenliving.LeftListFragment.DOUBLE_VITRAGE);
                                        }
                                    }
                                }
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_ADD_RIGHT:
                                item_main_ui.setRightWindowVisible(true);
                                item_main_ui.setRightWindow(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.triple_vitrage));
                                item_main_ui.setRightTypeWindow(hes_so.greenliving.LeftListFragment.TRIPLE_VITRAGE);
                                temp_item_2.setEastResume(temp_item_2.getEastResume() + 1);
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_REMOVE_RIGHT:
                                if (temp_item_2.getEastResume() != 0) {
                                    temp_item_2.setEastResume(temp_item_2.getEastResume() - 1);
                                    if (temp_item_2.getEastResume() == 0) {
                                        if (temp_item_1.getEastResume() == 0) {
                                            if (temp_item_0.getEastResume() == 0) {
                                                item_main_ui.setRightWindowVisible(false);
                                            } else {
                                                item_main_ui.setRightWindow(
                                                        BitmapFactory.decodeResource(getResources(),
                                                                R.drawable.simple_vitrage));
                                                item_main_ui.setRightTypeWindow(
                                                        hes_so.greenliving.LeftListFragment.SIMPLE_VITRAGE);
                                            }
                                        } else {
                                            item_main_ui.setRightWindow(
                                                    BitmapFactory.decodeResource(getResources(),
                                                            R.drawable.double_vitrage));
                                            item_main_ui.setRightTypeWindow(
                                                    hes_so.greenliving.LeftListFragment.DOUBLE_VITRAGE);
                                        }
                                    }
                                }
                                break;
                            //----------------------------------------------------------------------
                            default:
                                break;
                        }
                        break;
                    //------------------------------------------------------------------------------
                    case hes_so.greenliving.LeftListFragment.SIMPLE_ISOLATION:
                        CustomResume temp_item_3 = right_list_resume.get(3);
                        switch (command) {
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_ADD_LEFT:
                                item_main_ui.setLeftFirstIsolation(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.isolation_1));
                                item_main_ui.setLeftFirstIsolationVisible(true);
                                temp_item_3.setWestResume(temp_item_3.getWestResume() + 1);
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_REMOVE_LEFT:
                                if (temp_item_3.getWestResume() != 0){
                                    temp_item_3.setWestResume(temp_item_3.getWestResume() - 1);
                                    if(temp_item_3.getWestResume() == 0)
                                        item_main_ui.setLeftFirstIsolationVisible(false);
                                }
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_ADD_RIGHT:
                                item_main_ui.setRightFirstIsolation(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.isolation_1));
                                item_main_ui.setRightFirstIsolationVisible(true);
                                temp_item_3.setEastResume(temp_item_3.getEastResume()+1);
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_REMOVE_RIGHT:
                                if (temp_item_3.getEastResume() != 0) {
                                    temp_item_3.setEastResume(temp_item_3.getEastResume() - 1);
                                    if(temp_item_3.getEastResume() == 0)
                                        item_main_ui.setRightFirstIsolationVisible(false);
                                }
                                break;
                            //----------------------------------------------------------------------
                            default:
                                break;
                        }
                        break;
                    //------------------------------------------------------------------------------
                    case hes_so.greenliving.LeftListFragment.DOUBLE_ISOLATION:
                        CustomResume temp_item_4 = right_list_resume.get(4);
                        switch (command) {
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_ADD_LEFT:
                                item_main_ui.setLeftSecondIsolation(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.isolation_2));
                                item_main_ui.setLeftSecondIsolationVisible(true);
                                temp_item_4.setWestResume(temp_item_4.getWestResume()+1);
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_REMOVE_LEFT:
                                if(temp_item_4.getWestResume() != 0){
                                    temp_item_4.setWestResume(temp_item_4.getWestResume()-1);
                                    if(temp_item_4.getWestResume() == 0)
                                        item_main_ui.setLeftSecondIsolationVisible(false);
                                }
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_ADD_RIGHT:
                                item_main_ui.setRightSecondIsolation(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.isolation_2));
                                item_main_ui.setRightSecondIsolationVisible(true);
                                temp_item_4.setEastResume(temp_item_4.getEastResume()+1);
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_REMOVE_RIGHT:
                                if(temp_item_4.getEastResume() != 0){
                                    temp_item_4.setEastResume(temp_item_4.getEastResume()-1);
                                    if(temp_item_4.getEastResume() == 0)
                                        item_main_ui.setRightSecondIsolationVisible(false);
                                }
                                break;
                            //----------------------------------------------------------------------
                            default:
                                break;
                        }
                        break;
                    //------------------------------------------------------------------------------
                    case hes_so.greenliving.LeftListFragment.TRIPLE_ISOLATION:
                        CustomResume temp_item_5 = right_list_resume.get(5);
                        switch (command) {
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_ADD_LEFT:
                                item_main_ui.setLeftThirdIsolation(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.isolation_3));
                                item_main_ui.setLeftThirdIsolationVisible(true);
                                temp_item_5.setWestResume(temp_item_5.getWestResume()+1);
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_REMOVE_LEFT:
                                if(temp_item_5.getWestResume() != 0){
                                    temp_item_5.setWestResume(temp_item_5.getWestResume()-1);
                                    if(temp_item_5.getWestResume() == 0)
                                        item_main_ui.setLeftThirdIsolationVisible(false);
                                }
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_ADD_RIGHT:
                                item_main_ui.setRightThirdIsolation(
                                        BitmapFactory.decodeResource(getResources(),
                                                R.drawable.isolation_3));
                                item_main_ui.setRightThirdIsolationVisible(true);
                                temp_item_5.setEastResume(temp_item_5.getEastResume()+1);
                                break;
                            //----------------------------------------------------------------------
                            case FunctionalityAdapter.MSG_REMOVE_RIGHT:
                                if(temp_item_5.getEastResume() != 0){
                                    temp_item_5.setEastResume(temp_item_5.getEastResume()-1);
                                    if(temp_item_5.getEastResume() == 0)
                                    item_main_ui.setRightThirdIsolationVisible(false);
                                }
                                break;
                            //----------------------------------------------------------------------
                            default:
                                break;
                        }
                        break;
                    //------------------------------------------------------------------------------
                    default:
                        break;
                }
                break;
             //-------------------------------------------------------------------------------------
            case hes_so.greenliving.LeftListFragment.CHAUFFAGE_INDEX:
                break;
            case hes_so.greenliving.LeftListFragment.PRODUCTION_INDEX:
                break;
            case hes_so.greenliving.LeftListFragment.ELECTROMENAGER_INDEX:
                break;
            default:
                break;
        }
        updateMainUIFragment();
        updateRightListFragment();
    }

    private void updateMainUIFragment(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            fragmentManager.beginTransaction().detach(mainUIFragment).commit();
            fragmentManager.beginTransaction().attach(mainUIFragment).commit();
        }
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

            fragmentManager.beginTransaction().detach(mainUIFragment).commit();
            fragmentManager.beginTransaction().attach(mainUIFragment).commit();
        }
    }

    private void updateRightListFragment(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            fragmentManager.beginTransaction().detach(rightListFragment).commit();
            fragmentManager.beginTransaction().attach(rightListFragment).commit();
        }
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

            fragmentManager.beginTransaction().detach(rightListFragment).commit();
            fragmentManager.beginTransaction().attach(rightListFragment).commit();
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
        final CustomResume item_1 = new CustomResume(
                BitmapFactory.decodeResource(getResources(), R.drawable.simple_vitrage),
                0,
                0,
                0,
                0,
                0,
                "Pleins de triple vitrage oupi !"
        );
        final CustomResume item_2 = new CustomResume(
                BitmapFactory.decodeResource(getResources(), R.drawable.double_vitrage),
                0,
                0,
                0,
                0,
                0,
                "Pleins de fenêtre oupi !"
        );
        final CustomResume item_3 = new CustomResume(
                BitmapFactory.decodeResource(getResources(), R.drawable.triple_vitrage),
                0,
                0,
                0,
                0,
                0,
                "Pleins de fenêtre oupi !"
        );
        final CustomResume item_4 = new CustomResume(
                BitmapFactory.decodeResource(getResources(), R.drawable.isolation_1_small),
                0,
                0,
                0,
                0,
                0,
                "Pleins de fenêtre oupi !"
        );
        final CustomResume item_5 = new CustomResume(
                BitmapFactory.decodeResource(getResources(), R.drawable.isolation_2_small),
                0,
                0,
                0,
                0,
                0,
                "Pleins de fenêtre oupi !"
        );
        final CustomResume item_6
                = new CustomResume(
                BitmapFactory.decodeResource(getResources(), R.drawable.isolation_3_small),
                0,
                0,
                0,
                0,
                0,
                "Pleins de fenêtre oupi !"
        );

        right_list_resume.add(item_1);
        right_list_resume.add(item_2);
        right_list_resume.add(item_3);
        right_list_resume.add(item_4);
        right_list_resume.add(item_5);
        right_list_resume.add(item_6);
        rightListFragment.setList(right_list_resume);
        //------------------------------------------------------------------------------------------
        // Init the middle list for mainUIFragment--------------------------------------------------
        first_middle_list_ui = new ArrayList<>();
        //cération d'un deuxième éléments d'exmple dans la list principale
        final CustomUI item1 = new CustomUI(
                BitmapFactory.decodeResource(getResources(),R.drawable.bas_maison),
                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_3),true,
                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_2),true,
                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_1),true,

                BitmapFactory.decodeResource(getResources(),R.drawable.double_vitrage),true,
                BitmapFactory.decodeResource(getResources(),R.drawable.double_vitrage),true,
                BitmapFactory.decodeResource(getResources(),R.drawable.double_vitrage),true,

                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_1),true,
                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_2),true,
                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_3),true,

                hes_so.greenliving.LeftListFragment.DOUBLE_VITRAGE,
                hes_so.greenliving.LeftListFragment.DOUBLE_VITRAGE,
                hes_so.greenliving.LeftListFragment.DOUBLE_VITRAGE
        );

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
                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_3),false,

                hes_so.greenliving.LeftListFragment.SIMPLE_VITRAGE,
                hes_so.greenliving.LeftListFragment.DOUBLE_VITRAGE,
                hes_so.greenliving.LeftListFragment.TRIPLE_VITRAGE
        );
        //------------------------------------------------------------------------------------------
        //
        first_middle_list_ui.add(item2);
        first_middle_list_ui.add(item1);

        mainUIFragment.setFirstlist(first_middle_list_ui);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
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