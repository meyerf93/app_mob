package hes_so.mediaplayer_service;

import android.Manifest;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.Messenger;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PlayerMainActivity extends AppCompatActivity implements
        SongListFragment.OnListFragmentInteractionListener {

    private boolean playerFragmentFlag = false;
    private boolean songListFragmentFlag = false;

    private SongListFragment songListFragment;
    private PlayerFragment playerFragment;
    private FragmentManager fragmentManager;

    private SongListManager songListManager;

    private PlayerService mService;
    private Messenger serviceMesenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //----------------------Start service ------------------------------------
        mService = new PlayerService();

        final Intent serviceIntent = new Intent(PlayerMainActivity.this,PlayerService.class);
        startService(serviceIntent);

        ServiceConnection sConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                serviceMesenger = new Messenger(service);
                playerFragment.setServiceMesenger(serviceMesenger);


                /* service unidirectionelle
                mService = ((PlayerService.PlayerBinder) service)
                        .getService();
                        //.onBind(serviceIntent);
                */

                playerFragment.sendMessageToService(PlayerService.MSG_REGISTER_CLIENT, 0);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

                mService = null;
                serviceMesenger = null;
            }
        };
        bindService(serviceIntent,sConnection, Context.BIND_AUTO_CREATE);
        //--------------------------------------------------------------------------

        fragmentManager = getFragmentManager();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            songListManager = new SongListManager(this);
            playerFragment = new PlayerFragment();
            playerFragment.setPlayerService(mService);
            playerFragment.setServiceMesenger(serviceMesenger);
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


    public void OnListFragmentInteraction(int index) {
        playerFragment.setCurrentSongIndex(index);
        playerFragment.setPlayerService(mService);
        playerFragment.setServiceMesenger(serviceMesenger);

        fragmentManager.beginTransaction().replace(R.id.container_fragment,playerFragment).commit();
        playerFragmentFlag= true;
        songListFragmentFlag = true;
    }

    public void onBackPressed() {

        fragmentManager.beginTransaction().replace(R.id.container_fragment, songListFragment).commit();
    }


}

