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


public class GreenLiving extends FragmentActivity {

    private boolean LeftListFragment = false;
    private boolean RightListFragment = false;
    private boolean MiddleUIFragment = false;


    private ArrayList<CustomResume> right_list_resume;

    private LeftListFragment leftListFragment;
    private LeftListFragment middleFragment;
   // private LeftListFragment rightListFragment;
    private MainUIFragment mainUIFragment;
    private RightListFragment rightListFragment;

    private String LOG = "log_GreenLiving";

    private FragmentManager fragmentManager;

    private static final String TAG = "GreenLiving";


    public ArrayList<CustomResume> getRight_list_resume() {
        return right_list_resume;
    }

    public void setRight_list_resume(ArrayList<CustomResume> right_list_resume) {
        this.right_list_resume = right_list_resume;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_green_living);

        fragmentManager = getFragmentManager();

        leftListFragment = new LeftListFragment();

        middleFragment = new LeftListFragment();

        Log.v(LOG,"before create right fragment");

        rightListFragment = new RightListFragment();

        Log.v(LOG,"after create right fragement");

        right_list_resume = new ArrayList<CustomResume>();
        CustomResume item = new CustomResume(
                BitmapFactory.decodeResource(getResources(), R.drawable.triple_vitrage),
                10,
                3,
                4,
                2,
                1,
                "Pleins de triple vitrage oupi !"
        );
        CustomResume item_2 = new CustomResume(
                BitmapFactory.decodeResource(getResources(), R.drawable.double_vitrage),
                20,
                3,
                4,
                10,
                3,
                "Pleins de fenêtre oupi !"
        );

        right_list_resume.add(item);
        right_list_resume.add(item_2);
        rightListFragment.setList(right_list_resume);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final FragmentTransaction ft = fragmentManager.beginTransaction();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            ft.add(R.id.left_list_fragment,leftListFragment);
            ft.add(R.id.right_list_fragment,rightListFragment);
            //ft.add(R.id.main_ui_fragment,middleFragment);
            ft.commit();
        }
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

            //ft.add(R.id.main_fragment,leftListFragment);
            ft.add(R.id.main_fragment,rightListFragment);
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
            ft.remove(middleFragment);
            ft.commit();
        }
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

            //ft.remove(leftListFragment);
            ft.remove(rightListFragment);
            ft.commit();

        }
    }
}