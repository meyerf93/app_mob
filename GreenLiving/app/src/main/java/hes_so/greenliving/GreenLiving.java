package hes_so.greenliving;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.ArrayList;

import hes_so.greenliving.Resume.CustomResume;
import hes_so.greenliving.UI.CustomUI;


public class GreenLiving extends FragmentActivity {

    private boolean LeftListFragment = false;
    private boolean RightListFragment = false;
    private boolean MiddleUIFragment = false;


    private LeftListFragment leftListFragment;
    private MainUIFragment mainUIFragment;
    private RightListFragment rightListFragment;

    private String LOG = "log_GreenLiving";

    private FragmentManager fragmentManager;

    private static final String TAG = "GreenLiving";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_green_living);

        fragmentManager = getFragmentManager();

        leftListFragment = new LeftListFragment();

        mainUIFragment = new MainUIFragment();

        rightListFragment = new RightListFragment();


        // Init the right list for fragment
        ArrayList<CustomResume> right_list_resume = new ArrayList<>();
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
        ArrayList<CustomUI> first_middle_list_ui = new ArrayList<>();
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

                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_3),true,
                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_2),true,
                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_1),true,

                BitmapFactory.decodeResource(getResources(),R.drawable.simple_vitrage),false,
                BitmapFactory.decodeResource(getResources(),R.drawable.double_vitrage),false,
                BitmapFactory.decodeResource(getResources(),R.drawable.triple_vitrage),false,

                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_1),true,
                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_2),true,
                BitmapFactory.decodeResource(getResources(),R.drawable.isolation_3),true
        );
        //------------------------------------------------------------------------------------------
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