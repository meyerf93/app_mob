package hes_so.mediaplayer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Florian.Meyer on 13.10.2016.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private SongListManager slm;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView artwork;
        public TextView artisName;
        public TextView songTitle;

        public ViewHolder (View v){
            super(v);
            artwork = (ImageView) v.findViewById(R.id.artwork);
            songTitle = (TextView) v.findViewById(R.id.song_tittle);
            artisName = (TextView) v.findViewById(R.id.artist_name);
        }
    }

    public RecyclerViewAdapter (SongListManager slm){
        this.slm = slm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_song, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.artisName.setText(slm.getAlbum(position));
        holder.songTitle.setText(slm.getTitle(position));
        if (slm.getArtwork(position) !=null){
            holder.artwork.setImageBitmap(slm.getArtwork(position));
        }
    }

    @Override
    public int getItemCount() {
        return slm.getPlayListLength();
    }
}
