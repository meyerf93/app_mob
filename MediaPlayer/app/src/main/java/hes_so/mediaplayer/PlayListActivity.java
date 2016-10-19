package hes_so.mediaplayer;

import android.content.Intent;
import android.os.Bundle;
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
            Intent playerActivityIntent =
                    new Intent(getApplicationContext(),PlayerActiviy.class);

            playerActivityIntent.putExtra("SONG_INDEX", songIndex);

            startActivity(playerActivityIntent);


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);



        init_phone_music_grid();
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
