package hes_so.mediaplayer_fragment;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Florian.Meyer on 03.11.2016.
 */

public class PlayerFragment extends Fragment {
    private MediaPlayer mediaplayer;
    private int currentSongIndex;
    private SongListManager songListManager;

    private ImageView album_art;
    private TextView song_tittle;
    private TextView artist_name;
    private TextView album;

    private String timeFormat = "mm:ss"; // 12:00
    private TextView timeSong;
    private TextView endSong;
    /*private Thread update;
    private boolean updateTime = true;*/

    private static final String TAG = "PlayerLog";

    private boolean Play = true;
    private boolean Pause = false;

    private SeekBar displayTime;

    private ImageButton prev_button;
    private ImageButton stop_button;
    private ImageButton play_button;
    private ImageButton next_button;

    public void setSongListManager(SongListManager songListManager){
        this.songListManager = songListManager;
    }

    public void setCurrentSongIndex(int currentSongIndex){
        this.currentSongIndex = currentSongIndex;
    }

    interface OnPlayerFragmentInteractionListener {
        void OnPlayerFragmentInteraction(int index);
    }

    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                                Bundle savInstanceState){
        View view = inflater.inflate(R.layout.player_fragment, container,false);

        initFields(view);
        initButtons(view);
        setListenerToButton();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // ---------------------------------------------------
        // Etape 2 : creation du manager de la liste de chanson
        // ----------------------------------------------------


        // Manage the index comming from the playList actiity
        playSong(currentSongIndex);

        //Listener to update time when changing the seek bar
        displayTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaplayer.seekTo(displayTime.getProgress());
                //updateTime();
            }
        });
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Lie le layout à l'activité

        // --------------------------------------------------
        // Etape 1 : creation du media player
        // --------------------------------------------------
        mediaplayer = new MediaPlayer();

        // --------------------------------------------------
        // Play continously....
        // --------------------------------------------------

        mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (currentSongIndex < songListManager.getPlayListLength() -1 ){
                    ++currentSongIndex;
                }
                    //message("End of list!");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initFields(View view){
        album_art = (ImageView) view.findViewById(R.id.player_album_art);
        song_tittle = (TextView) view.findViewById(R.id.player_song_title);
        artist_name = (TextView) view.findViewById(R.id.player_artist_name);
        album = (TextView) view.findViewById(R.id.player_album);

        timeSong = (TextView) view.findViewById(R.id.player_time_left);
        endSong = (TextView) view.findViewById(R.id.player_time_right);

        displayTime = (SeekBar) view.findViewById(R.id.progressBar);

        Play = true;
        Pause = false;

    }

    private void initButtons(View view){
        prev_button = (ImageButton) view.findViewById(R.id.previousButton);
        play_button = (ImageButton) view.findViewById(R.id.playButton);
        stop_button = (ImageButton) view.findViewById(R.id.stopButton);
        next_button = (ImageButton) view.findViewById(R.id.nextButton);

        prev_button.setImageResource(R.mipmap.ic_previous);
        play_button.setImageResource(R.mipmap.ic_pause);
        stop_button.setImageResource(R.mipmap.ic_stop);
        next_button.setImageResource(R.mipmap.ic_next);
    }

    public void onStopFroActivity(){
        mediaplayer.stop();
    }

    private void setListenerToButton() {
        /******************************
         * STOP button initialisation
         *******************************/
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStopFroActivity();
                Play = false;
                Pause = false;
                play_button.setImageResource(R.mipmap.ic_play);
            }
        });
        /******************************
         * PLAY button initialisation
         *******************************/
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Pause) {
                    Play = true;
                    Pause = false;
                    mediaplayer.start();
                    play_button.setImageResource(R.mipmap.ic_pause);
                } else if (Play) {
                    Play = false;
                    Pause = true;
                    mediaplayer.pause();
                    play_button.setImageResource(R.mipmap.ic_play);
                }
                else{
                    playSong(currentSongIndex);
                    Play = true;
                    Pause = false;
                    play_button.setImageResource(R.mipmap.ic_pause);
                }
            }
        });


        /******************************
         * PREV button initialisation
         *******************************/
        prev_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "previous index=" + currentSongIndex);
                currentSongIndex--;
                Log.v(TAG, "after index =" + currentSongIndex);
                if(currentSongIndex >= 0){
                    playSong(currentSongIndex);
                }
                else {
                    playSong(++currentSongIndex);
                }
            }
        });

        /******************************
         * NEXT button initialisation
         *******************************/
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSongIndex++;
                if(currentSongIndex<songListManager.getPlayListLength()){
                    playSong(currentSongIndex);
                }
                else {
                    playSong(--currentSongIndex);
                }
            }
        });
    }

    public void playSong(int songIndex){
        try{
            mediaplayer.reset();
            mediaplayer.setDataSource(songListManager.getSongFileName(songIndex));
            mediaplayer.prepare();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        catch (SecurityException e){
            e.printStackTrace();
        }
        catch (IllegalStateException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        mediaplayer.start();
        setSongInfo(songIndex);
    }

    private void setSongInfo(int songInfo){
        song_tittle.setText(songListManager.getTitle(currentSongIndex));
        artist_name.setText(songListManager.getArtist(currentSongIndex));
        album.setText(songListManager.getAlbum(currentSongIndex));

        if(songListManager.getArtwork(currentSongIndex)!=null){
            album_art.setImageBitmap(songListManager.getArtwork(currentSongIndex));
        }else {
            album_art.setImageResource(R.mipmap.ic_music);
        }

        endSong.setText(DateFormat.format(timeFormat,mediaplayer.getDuration()));
        displayTime.setMax(mediaplayer.getDuration());
    }
}
