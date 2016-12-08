package hes_so.mediaplayer_service;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Florian.Meyer on 03.11.2016.
 */

public class SongListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewAdapter mAdapter;
    private SongListManager songListManager;

    private static final String TAG = "ListFragment";

    private int currentSongIndex;

    public void setSongListManager(SongListManager songListManager){
        this.songListManager = songListManager;
    }

    interface OnListFragmentInteractionListener {
        void OnListFragmentInteraction(int index);
    }

    private RecyclerItemClickListener.OnItemClickListener musicGridListener
            = new RecyclerItemClickListener.OnItemClickListener() {

        public void onItemClick(View v, int position){
            System.gc();

            currentSongIndex = position;

            PlayerMainActivity mainActivity = (PlayerMainActivity) getActivity();
            mainActivity.OnListFragmentInteraction(currentSongIndex);
        }
    };



    @NonNull
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch(requestCode){
            case 0:{
                // If request is cancelled, the result arrays are empty. 
                if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    songListManager = new SongListManager(getActivity());
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG,"listfragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG,"listfragment onCreateView");
        View view = inflater.inflate(R.layout.activity_recycler_view, container,false);
        init_recycleViewList(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    private void init_recycleViewList(View view){
        //setContentView(R.layout.activity_recycler_view);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext()) ;
        mRecyclerView.setLayoutManager(mLayoutManager);

        Log.v(TAG,"On ini recycler view : "+ songListManager );

        mAdapter = new RecyclerViewAdapter(songListManager);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerItemClickListener temp = new RecyclerItemClickListener(getContext(),musicGridListener);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),musicGridListener));
    }
}
