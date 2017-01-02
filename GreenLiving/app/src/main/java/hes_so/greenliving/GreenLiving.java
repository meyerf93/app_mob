package hes_so.greenliving;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class GreenLiving extends FragmentActivity {

    private boolean LeftListFragment = false;
    private boolean RightListFragment = false;
    private boolean MiddleUIFragment = false;

    private LeftListFragment leftListFragment;
    private LeftListFragment middleFragment;
    private LeftListFragment rifthFragment;
    /*private MainUIFragment mainUIFragment;
    private RightListFragment rightListFragment;*/

    private FragmentManager fragmentManager;

    private static final String TAG = "GreenLiving";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_living);

        fragmentManager = getFragmentManager();
        final FragmentTransaction ft = fragmentManager.beginTransaction();

        leftListFragment = new LeftListFragment();

        middleFragment = new LeftListFragment();

        rifthFragment = new LeftListFragment();

        ft.add(R.id.left_list_fragment,leftListFragment);

        //ft.add(R.id.right_list_fragment,rifthFragment);

        //ft.add(R.id.main_ui_fragment,middleFragment);

        ft.commit();

    }

}
