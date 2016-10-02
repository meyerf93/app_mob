package ch.heai.mobiledev.lauchactivity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Florian.Meyer on 06.04.2016.
 */
public class SimpleService extends Service {

    private static final String TAG = SimpleService.class.getSimpleName();

    public static volatile boolean shouldContinue = true;

    @Override
    public void onDestroy() {
        Log.v(TAG,"onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG,"onStartCommand");

        final int currentID = startId;

        Runnable r = new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<3;i++){
                    Log.v(TAG,"Service running with id " + currentID);
                    long endTime = System.currentTimeMillis()+1*1000;
                    while (System.currentTimeMillis() < endTime && shouldContinue){
                        synchronized (this){
                            try {
                                wait(endTime-System.currentTimeMillis());
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                stopSelf(currentID);
            }
        };

        Thread t = new Thread(r);
        t.start();

        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.v(TAG, "oncreate");
        super.onCreate();
    }
}
