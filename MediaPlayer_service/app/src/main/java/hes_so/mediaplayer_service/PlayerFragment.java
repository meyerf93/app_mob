package hes_so.mediaplayer_service;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * Created by Florian.Meyer on 03.11.2016.
 */

public class PlayerFragment extends Fragment {
    //player managed by fragment
    private int currentSongIndex;
    private SongListManager songListManager;

    private ImageView album_art;
    private TextView song_tittle;
    private TextView artist_name;
    private TextView album;


    public static final int MSG_UPDATE = 1;
    public static final int MSG_GET_END_TIME = 2;
    public static final int MSG_GET_BEGIN_TIME= 3;
    public static final int MSG_UPDATE_BAR = 4;

    private int timeEnd;
    private int timeDuration;

    private Messenger serviceMesenger;

    private Messenger AMessenger = new Messenger(new IncomingHandler());


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

    private PlayerService mService;

    public void setSongListManager(SongListManager songListManager){
        this.songListManager = songListManager;
    }

    public void setCurrentSongIndex(int currentSongIndex){
        this.currentSongIndex = currentSongIndex;
    }

    public void setPlayerService(PlayerService service){
        mService = service;
    }

    public  void setServiceMesenger(Messenger serviceMesenger){
        this.serviceMesenger = serviceMesenger;
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
        sendMessageToService(PlayerService.MSG_PLAY,currentSongIndex);

        // Manage the index comming from the playList actiity
        playSong(PlayerService.MSG_PLAY,currentSongIndex);

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void setListenerToButton() {
        /******************************
         * STOP button initialisation
         *******************************/
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onStopFroActivity();
                sendMessageToService(PlayerService.MSG_STOP,currentSongIndex);
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
                     playSong(PlayerService.MSG_PLAY,currentSongIndex);
                    play_button.setImageResource(R.mipmap.ic_pause);
                } else if (Play) {
                    Play = false;
                    Pause = true;
                    playSong(PlayerService.MSG_PAUSE,currentSongIndex);
                    play_button.setImageResource(R.mipmap.ic_play);
                }
                else{
                    playSong(PlayerService.MSG_PLAY,currentSongIndex);
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
                    playSong(PlayerService.MSG_PLAY,currentSongIndex);
                }
                else {
                    playSong(PlayerService.MSG_PLAY,++currentSongIndex);
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
                    playSong(PlayerService.MSG_PLAY,currentSongIndex);
                }
                else {
                    playSong(PlayerService.MSG_PLAY,--currentSongIndex);
                }
            }
        });
    }

    public void playSong(int cmd,int songIndex){
        sendMessageToService(cmd,songIndex);
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
        sendMessageToService(PlayerService.MSG_GET_BEGIN_TIME,currentSongIndex);
        sendMessageToService(PlayerService.MSG_GET_END_TIME,currentSongIndex);

        endSong.setText(DateFormat.format(timeFormat,timeEnd));
        displayTime.setMax(timeDuration);
    }

    public void sendMessageToService ( int command, int songIndex){
        try{
            Message msg = Message.obtain(null, command, songIndex, 0);
            msg.replyTo = AMessenger;
            serviceMesenger.send(msg);
        }
        catch (RemoteException e){
            e.printStackTrace();
        }
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_UPDATE:
                    CharSequence timeMin = String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(msg.arg1),
                            TimeUnit.MILLISECONDS.toSeconds(msg.arg1)-
                            TimeUnit.MILLISECONDS.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(msg.arg1)));
                    timeSong.setText(timeMin);
                    break;
                case MSG_GET_BEGIN_TIME:
                    timeDuration = msg.arg1;
                    break;
                case MSG_GET_END_TIME:
                    CharSequence time = String.format("-%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(msg.arg1),
                            TimeUnit.MILLISECONDS.toSeconds(msg.arg1) -
                                    TimeUnit.MINUTES.toSeconds(
                                            TimeUnit.MILLISECONDS.toMinutes(msg.arg1)));
                    endSong.setText(time);
                    break;
                case MSG_UPDATE_BAR:
                    displayTime.setProgress(msg.arg1);
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }
}
