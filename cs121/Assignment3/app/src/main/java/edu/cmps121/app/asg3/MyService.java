package edu.cmps121.app.asg3;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.util.Objects;

import static android.app.NotificationChannel.DEFAULT_CHANNEL_ID;

public class MyService extends Service {

    private static final String LOG_TAG = "MyService";
    Thread myThread;
    MotionTask myTask;
    PowerManager.WakeLock wakeLock;
    private IBinder myBinder;
    static long startTime;
    static long moveTime;
    Handler handler;

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    // Empty Constructor
    public MyService() {}

    // Returns the binder to the Activity once the Service is created
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "onBind()");
        return myBinder;
    }

    // Run every time the Application starts this Service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "onStartCommand()");
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        // handle Intent
        // keep the sensors on while sleeping
        if(wakeLock != null) {
            PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    "MyWakelockTag");
            wakeLock.acquire();
        }
        Log.i(LOG_TAG, "Received start id " + startId + ": " + intent);
        // We start the task thread.
        if (!myThread.isAlive()) {
            myThread.start();
        }
        Log.i(LOG_TAG, "onStartCommand() finished");
        return START_STICKY;
    }

    // Called when the Service is first created
    @Override
    public void onCreate() {
        Log.i(LOG_TAG, "onCreate()");
        startTime = new Date().getTime();
        moveTime = 0;
        myBinder = new MyBinder();
        handler = new LocalHandler();
        myTask = new MotionTask(this, handler);
        myThread = new Thread(myTask);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// The id of the channel.
        String id = "my_channel_01";
// The user-visible name of the channel.
// The user-visible description of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, "application channel", importance);
// Configure the notification channel.
        mChannel.setDescription("Default Notification Channel");
        mChannel.enableLights(true);
// Sets the notification light color for notifications posted to this
// channel, if the device supports this feature.
        mChannel.enableVibration(true);
        mNotificationManager.createNotificationChannel(mChannel);


        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        Notification notification =
                new Notification.Builder(this, id)
                        .setContentTitle("Motion Detection Service")
                        .setContentText("Detecting Motion...")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentIntent(pendingIntent)
                        .build();
        startForeground(1337, notification);

        Log.i(LOG_TAG, "onStartCommand() finished");
    }

    @Override
    public void onDestroy() {
        Log.i(LOG_TAG, "onDestroy()");
    }

    public boolean killService() {
        Log.i(LOG_TAG, "killService()");
        myTask.stopWork();
        myThread.interrupt();
        myThread = null;
        if(wakeLock != null) wakeLock.release();
        stopSelf();
        return true;
    }

    public void restartService() {
        Log.i(LOG_TAG, "restartService()");
        startTime = new Date().getTime();
        moveTime = (long)0.0;
    }

    public boolean didItMove() {
        Log.i(LOG_TAG, "didItMove()");
        long t = new Date().getTime();
        return ( !Objects.equals((long)0.0, moveTime) && ((t - moveTime) / 1000.0) > 30 );
    }

    static class LocalHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.i(LOG_TAG, "Message received from MotionTask");
            long t = new Date().getTime();
            if( ((t - startTime) / 1000.0) > 30.0 && moveTime == 0) {
                Log.i(LOG_TAG, "moveTime set");
                moveTime = t;
            }
        }
    }

}
