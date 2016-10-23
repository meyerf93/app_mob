package hes_so.mediaplayer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class PlayListActivity extends AppCompatActivity {

    private ArrayList<Song> songList = new ArrayList<Song>();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewAdapter mAdapter;
    private SongListManager songListManager;

    private RecyclerItemClickListener.OnItemClickListener musicGridListener
            = new RecyclerItemClickListener.OnItemClickListener() {

        public void onItemClick(View v, int position){
            System.gc();

            int songIndex = position;
            /*Intent playerActivityIntent =
                    new Intent(getApplicationContext(),PlayerActiviy.class);

            playerActivityIntent.putExtra("SONG_INDEX", songIndex);

            startActivity(playerActivityIntent);*/
        }
    };


    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch(requestCode){
            case 0:{
                // If request is cancelled, the result arrays are empty. 
                if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    init_phone_music_grid();
                }
                else {
                    //message("Permission to access your media not granted!"); 
                    finish();
                }
                return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            init_phone_music_grid();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    private void init_phone_music_grid() {

        SongListManager songListManager = new SongListManager(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter(songListManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,musicGridListener));
    }
}
