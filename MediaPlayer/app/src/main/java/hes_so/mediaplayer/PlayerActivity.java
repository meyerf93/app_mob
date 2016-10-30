package hes_so.mediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Florian.Meyer on 13.10.2016.
 */

public class PlayerActivity extends AppCompatActivity{

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
    private Thread update;
    private boolean updateTime = true;

    private boolean Play = true;
    private boolean Pause = false;

    private SeekBar displayTime;

    private ImageButton prev_button;
    private ImageButton stop_button;
    private ImageButton play_button;
    private ImageButton next_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Lie le layout à l'activité
        setContentView(R.layout.activity_player);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initFields();
        initButtons();

        // --------------------------------------------------
        // Etape 1 : creation du media player
        // --------------------------------------------------
        songListManager = new SongListManager(this);
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
                else{
                    //message("End of list!");
                }
            }
        });

        // ---------------------------------------------------
        // Etape 2 : creation du manager de la liste de chanson
        // ----------------------------------------------------

        setListenerToButton();

        // Manage the index comming from the playList actiity
        Intent playListIntent = getIntent();
        currentSongIndex = playListIntent.getIntExtra("SONG_INDEX",0);
        playSong(currentSongIndex);

        //Listener to update time when changing the seek bar
        displayTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaplayer.seekTo(displayTime.getProgress());
                updateTime();
            }
        });

        update = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateTime();
                                Log.d("test:","Still alive !");
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        update.start();
    }


    private void initFields(){
        album_art = (ImageView) findViewById(R.id.player_album_art);
        song_tittle = (TextView) findViewById(R.id.player_song_title);
        artist_name = (TextView) findViewById(R.id.player_artist_name);
        album = (TextView) findViewById(R.id.player_album);

        timeSong = (TextView) this.findViewById(R.id.player_time_left);
        endSong = (TextView) this.findViewById(R.id.player_time_right);

        displayTime = (SeekBar) this.findViewById(R.id.progressBar);

        Play = true;
        Pause = false;

    }

    private void initButtons(){
        prev_button = (ImageButton) findViewById(R.id.previousButton);
        play_button = (ImageButton) findViewById(R.id.playButton);
        stop_button = (ImageButton) findViewById(R.id.stopButton);
        next_button = (ImageButton) findViewById(R.id.nextButton);

        prev_button.setImageResource(R.mipmap.ic_previous);
        play_button.setImageResource(R.mipmap.ic_pause);
        stop_button.setImageResource(R.mipmap.ic_stop);
        next_button.setImageResource(R.mipmap.ic_next);

        setListenerToButton();
    }

    @Override
    public void onBackPressed() {

        mediaplayer.stop();
        update.interrupt();
        try {
            update.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mediaplayer.stop();
        finish();
    }

    public void updateTime(){
        int time = mediaplayer.getCurrentPosition();

        timeSong.setText(DateFormat.format(timeFormat, time));
        displayTime.setProgress(time);
    }

    private void setListenerToButton() {
        /******************************
         * STOP button initialisation
         *******************************/
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        /******************************
         * PLAY button initialisation
         *******************************/
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Pause == true) {
                    Play = true;
                    Pause = false;
                    mediaplayer.start();
                    play_button.setImageResource(R.mipmap.ic_pause);
                } else if (Play == true) {
                    Play = false;
                    Pause = true;
                    mediaplayer.pause();
                    play_button.setImageResource(R.mipmap.ic_play);
                }
            }
        });


        /******************************
         * PREV button initialisation
         *******************************/
        prev_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSongIndex--;
                if(currentSongIndex > 0){
                    playSong(currentSongIndex);
                }
                else {
                    playSong(currentSongIndex++);
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
                    playSong(currentSongIndex--);
                }
            }
        });
    }

    public void playSong(int songIndex){
        setSongInfo(songIndex);

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
        updateTime();
    }

}
