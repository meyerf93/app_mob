package hes_so.mediaplayer;

import android.content.Context;
import android.content.res.Resources;
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
        ViewHolder holder;
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(viewRes,parent,false);

            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.song_tittle);
            holder.artwork = (ImageView) view.findViewById(R.id.artwork);
            holder.description = (TextView) view.findViewById(R.id.artist_name);

            //Assigne notre view holder à la view
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.title.setText(slm.getTitle(position));
        holder.description.setText(slm.getArtist(position));


        if (slm.getArtwork(position) != null){
            holder.artwork.setImageBitmap(slm.getArtwork(position));
        }else{
            holder.artwork.setImageResource(R.mipmap.ic_music);
        }

        return view;
    }

    public int getCount(){
        return slm.getPlayListLength();
    }


    static class ViewHolder {
        TextView title;
        TextView description;
        ImageView artwork;
    }
}


