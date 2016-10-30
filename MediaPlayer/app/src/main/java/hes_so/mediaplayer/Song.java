package hes_so.mediaplayer;

import android.graphics.Bitmap;

/**
 * Created by Florian.Meyer on 13.10.2016.
 */

public class Song {
    private String songTitle;
    private String artist_Name;
    private String album;
    private Bitmap artwork;

    public Song(String songTitle, String artist_Name, String album, Bitmap artwork){
        this.songTitle = songTitle;
        this.artist_Name = artist_Name;
        this.album = album;
        this.artwork = artwork;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getArtistName() {
        return artist_Name;
    }

    public void setArtistName(String artist_Name) {
        this.artist_Name = artist_Name;
    }

    public void setAlbum (String album){
        this.album = album;
    }

    public String getAlbum (){
        return  album;
    }

    public void setArtwork (Bitmap artwork){
        this.artwork = artwork;
    }

    public Bitmap getArtwork (){
        return  artwork;
    }
}
