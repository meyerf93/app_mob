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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class PlayListActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewAdapter mAdapter;
    private SongListManager songListManager;

    private RecyclerItemClickListener.OnItemClickListener musicGridListener
            = new RecyclerItemClickListener.OnItemClickListener() {

        public void onItemClick(View v, int position){
            System.gc();

            int songIndex = position;
            Intent playerActivityIntent =
                    new Intent(PlayListActivity.this,PlayerActivity.class);

            playerActivityIntent.putExtra("SONG_INDEX", songIndex);

            startActivity(playerActivityIntent);
        }
    };


    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch(requestCode){
            case 0:{
                // If request is cancelled, the result arrays are empty. 
                if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    songListManager = new SongListManager(PlayListActivity.this);
                }
                else {
                    finish();
                }
                return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            songListManager = new SongListManager(this);
        }
        else {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        //init_holderList();
        init_recycleViewList();
    }

    private void init_recycleViewList(){
        setContentView(R.layout.activity_recycler_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter(songListManager);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerItemClickListener temp = new RecyclerItemClickListener(this,musicGridListener);
        Log.v(TAG,"listener not null : "+ String.valueOf(temp == null));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,musicGridListener));

        Log.v(TAG,"Clicabkle : "+mRecyclerView.isClickable());
    }

    private void init_holderList(){
        setContentView(R.layout.activity_play_list);

        SongAdapter adapter = new SongAdapter(this,R.layout.custom_song,songListManager);
        ListView songListView = (ListView) findViewById(R.id.playListView);
        songListView.setAdapter(adapter);

        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlayListActivity.this, PlayerActivity.class);
                intent.putExtra("SONG_INDEX",position);
                startActivity(intent);
            }
        });

    }
}
