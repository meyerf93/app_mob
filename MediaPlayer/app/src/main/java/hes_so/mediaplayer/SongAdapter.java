package hes_so.mediaplayer;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.AndroidCharacter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Florian.Meyer on 13.10.2016.
 */

public class SongAdapter extends ArrayAdapter<Song>{

    private SongListManager slm;
    private Context context;
    private int viewRes;
    private Resources res;

    public SongAdapter(Context context, int textViewRessourceId,
                        SongListManager slm) {
        super(context, textViewRessourceId);
        this.context = context;
        this.viewRes = textViewRessourceId;
        this.res = context.getResources();
        this.slm = slm;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        //RecyclerViewAdapter.ViewHolder holder;
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            //holder = new RecyclerViewAdapter.ViewHolder();
            //holder.songTitle = (TextView) view.findViewById(R.id.song_tittle);
            //holder.artwork = (ImageView) view.findViewById(R.id.artwork);
            //holder.artisName = (TextView) view.findViewById(R.id.artist_name);
            view = layoutInflater.inflate(viewRes,parent,false);
        }

        return view;
    }

    public int getCount(){
        return slm.getPlayListLength();
    }

    /*private viewHolder {

    }*/


}


