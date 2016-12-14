package hes_so.greenliving;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GreenLiving extends AppCompatActivity implements
        LeftListFragment.OnLeftListFragmentInteractionListener,
        MainUIFragment.OnMainUIFragmentInteractionListenerListener,
        RightListFragment.OnRightListFragmentInteractionListener {

    private boolean LeftListFragment = false;
    private boolean RightListFragment = false;
    private boolean MiddleUIFragment = false;

    private LeftListFragment leftListFragment;
    private MainUIFragment mainUIFragment;
    private RightListFragment rightListFragment;

    private FragmentManager fragmentManager;

    private static final String TAG = "GreenLiving";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_living);

        fragmentManager = getFragmentManager();

        leftListFragment = new LeftListFragment();

        mainUIFragment = new MainUIFragment();

        rightListFragment = new RightListFragment();


    }

    @Override
    public void OnLeftListFragmentInteraction(int index) {

    }

    @Override
    public void OnMainUIFragmentInteraction(int index) {

    }

    @Override
    public void OnRightListFragmentInteraction(int index) {

    }
}
