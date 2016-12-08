package hes_so.mediaplayer_service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.TimedMetaData;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by Florian.Meyer on 17.11.2016.
 */

public class PlayerService extends Service {

    private MediaPlayer mediaplayer;
    private int currentSongIndex;
    private SongListManager songListManager;

    private static final String TAG = "PlayerService";

    public static final int MSG_REGISTER_CLIENT = 1;
    public static final int MSG_PAUSE = 2;
    public static final int MSG_PLAY = 3;
    public static final int MSG_STOP = 4;
    public static final int MSG_DESTROY = 5;
    public static final int MSG_GET_END_TIME = 6;
    public static final int MSG_GET_BEGIN_TIME = 7;

    private ProgessTask progessTask;

    private final IBinder binder = new PlayerBinder();

    private Messenger mMessenger = new Messenger(new IncomingHandler());

    private void sendMessageToApplication(int command, int intValueToSend) {
        try {
            Message msg = Message.obtain(null, command, intValueToSend, 0);
            msg.replyTo = mMessenger;
            mMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public PlayerService() {
        mMessenger = new Messenger(new IncomingHandler());
    }


    @Override
    public void onCreate() {
        super.onCreate();
        songListManager = new SongListManager(this);

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
                if (currentSongIndex < songListManager.getPlayListLength() - 1) {
                    ++currentSongIndex;
                }
                //message("End of list!");
            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return mMessenger.getBinder();
        //return binder;
    }

    public class PlayerBinder extends Binder {
        PlayerService getService() {
            return PlayerService.this;
        }
    }

    class IncomingHandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REGISTER_CLIENT:
                    mMessenger = msg.replyTo;
                    Log.v(TAG, "Service : register client");
                    break;
                case MSG_PAUSE:
                    pause();
                    break;
                case MSG_PLAY:
                    play(msg.arg1);
                    break;
                case MSG_STOP:
                    stop();
                    break;
                case MSG_DESTROY:
                    removeNotification();
                    break;
                case MSG_GET_END_TIME:
                    sendMessageToApplication(PlayerFragment.MSG_GET_END_TIME, mediaplayer.getDuration());
                    break;
                case MSG_GET_BEGIN_TIME:
                    sendMessageToApplication(PlayerFragment.MSG_GET_BEGIN_TIME, mediaplayer.getDuration());
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private void play(int songIndex) {
        try {
            mediaplayer.reset();
            mediaplayer.setDataSource(songListManager.getSongFileName(songIndex));
            mediaplayer.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaplayer.start();
        progessTask = new ProgessTask();
        progessTask.execute();

    }

    private void pause() {
        mediaplayer.pause();
    }

    private void stop() {
        mediaplayer.stop();
    }

    private void removeNotification() {

    }

    private class ProgessTask extends AsyncTask<Void, Integer, Void> {

        int total = 0;
        int remainingTime = 0;

        @Override
        protected Void doInBackground(Void... params) {
            if (mediaplayer != null) {
                total = mediaplayer.getDuration();
            }

            while (mediaplayer != null && mediaplayer.getCurrentPosition()<
                    mediaplayer.getDuration()){
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    Log.v(TAG,e.toString());
                }catch (Exception e){
                    Log.v(TAG,e.toString());
                }
                if(mediaplayer != null){
                    publishProgress(mediaplayer.getCurrentPosition());
                }
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            sendMessageToApplication(PlayerFragment.MSG_UPDATE_BAR,values[0]);
            remainingTime = mediaplayer.getDuration() - values[0];

            sendMessageToApplication(PlayerFragment.MSG_UPDATE,values[0]);

            sendMessageToApplication(PlayerFragment.MSG_GET_END_TIME,remainingTime);
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }

}

