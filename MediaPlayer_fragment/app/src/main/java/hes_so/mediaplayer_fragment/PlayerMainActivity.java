package hes_so.mediaplayer_fragment;

import android.Manifest;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PlayerMainActivity extends AppCompatActivity implements
        PlayerFragment.OnPlayerFragmentInteractionListener,
        SongListFragment.OnListFragmentInteractionListener {

    private boolean playerFragmentFlag = false;
    private boolean songListFragmentFlag = false;

    private SongListFragment songListFragment;
    private PlayerFragment playerFragment;
    private FragmentManager fragmentManager;

    private SongListManager songListManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = getFragmentManager();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            songListManager = new SongListManager(this);
            playerFragment = new PlayerFragment();
            playerFragment.setSongListManager(songListManager);

            songListFragment = new SongListFragment();
            songListFragment.setSongListManager(songListManager);

            fragmentManager.beginTransaction()
                    .add(R.id.container_fragment,songListFragment).commit();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }
    
    @Override
    public void OnListFragmentInteraction(int index) {
        playerFragment.setCurrentSongIndex(index);
        fragmentManager.beginTransaction().replace(R.id.container_fragment,playerFragment).commit();
        playerFragmentFlag= true;
        songListFragmentFlag = true;
    }

    @Override
    public void OnPlayerFragmentInteraction(int index) {
        //playerFragment.setCurrentSongIndex(index);
    }

    public void onBackPressed() {
        playerFragment.onStopFroActivity();
        fragmentManager.beginTransaction().replace(R.id.container_fragment,songListFragment).commit();
    }

}
