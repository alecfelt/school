package edu.cmps121.app.asg3;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyService myService;
    boolean serviceBound;
    MyService.MyBinder binder;
    private static final String LOG_TAG = "MainActivity";
    TextView moved;
    Button clear, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOG_TAG, "onCreate()");
        clear = findViewById(R.id.clear_button); clear.setOnClickListener(this);
        exit = findViewById(R.id.exit_button); exit.setOnClickListener(this);
        moved = findViewById(R.id.moved);
        serviceBound = false;
        Log.i(LOG_TAG, "onCreate() finished");
    }

    @Override
    protected void onResume() {
        Log.i(LOG_TAG, "onResume()");
        if(!isServiceRunning(this, MyService.class)) {
            Log.i(LOG_TAG, "Service is not running, starting Service");
            Intent i = new Intent(getApplicationContext(), MyService.class);
            startService(i);
        }

        bindMyService();

        Log.i(LOG_TAG, "onResume() finished");
        super.onResume();
    }

    public boolean isServiceRunning(Context context, Class<?> serviceClass) {
        Log.i(LOG_TAG, "isServiceRunning()");
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i(LOG_TAG, "Service is Running");
                Log.i(LOG_TAG, "isServiceRunning() finished");
                return true;
            }
        }
        Log.i(LOG_TAG, "Service is not Running");
        Log.i(LOG_TAG, "isServiceRunning() finished");
        return false;
    }

    @Override
    public void onClick(View v) {
        Log.i(LOG_TAG, "onClick(): " + String.valueOf(((Button)v).getText()));
        if(v == exit) {
            myService.killService();
            stopService(new Intent(this, MyService.class));
            finish();
        } else if(v == clear) {
            moved.setText(R.string.not_moved);
            myService.restartService();
        }
        Log.i(LOG_TAG, "onClick() finished");
    }


    @Override
    protected void onPause() {
        Log.i(LOG_TAG, "onPause()");
        if (serviceBound) {
            Log.i(LOG_TAG, "Unbinding");
            unbindService(serviceConnection);
            serviceBound = false;
        }
        super.onPause();
        Log.i(LOG_TAG, "onPause() finished");
    }

    private boolean bindMyService() {
        Log.i(LOG_TAG, "bindMyService()");
        // We are ready to show images, and we should start getting the bitmaps
        // from the motion detection service.
        // Binds to the service.
        if(!serviceBound) {
            Log.i(LOG_TAG, "Trying to bind");
            Intent intent = new Intent(this, MyService.class);
            if(bindService(intent, serviceConnection, Context.BIND_IMPORTANT)) {
                Log.i(LOG_TAG, "bindMyService() finished");
                return true;
            }
        }
        if(myService.didItMove()) {
            Log.i(LOG_TAG, "Your phone moved");
            moved.setText(R.string.moved);
        } else {
            Log.i(LOG_TAG, "Your phone did not move");
            moved.setText(R.string.not_moved);
        }
        Log.i(LOG_TAG, "bindMyService() finished");
        return true;
    }

    // Service connection code.
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder serviceBinder) {
            // We have bound to the camera service.
            Log.i(LOG_TAG, "onServiceConnected");
            binder = (MyService.MyBinder) serviceBinder;
            myService = binder.getService();
            serviceBound = true;
            if(myService.didItMove()) {
                Log.i(LOG_TAG, "Your phone moved");
                moved.setText(R.string.moved);
            } else {
                Log.i(LOG_TAG, "Your phone did not move");
                moved.setText(R.string.not_moved);
            }
            // Let's connect the callbacks.
            Log.i("MyService", "Bind connected.");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.i(LOG_TAG, "onServiceDisconnected");
            Log.i("MyService", "Bind disconnected.");
        }
    };

}
