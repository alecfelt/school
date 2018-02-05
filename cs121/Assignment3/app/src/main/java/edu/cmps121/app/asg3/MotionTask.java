package edu.cmps121.app.asg3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Handler;
import android.os.Message;
import android.util.FloatMath;
import android.util.Log;

import static android.content.Context.SENSOR_SERVICE;
import static android.os.Process.THREAD_PRIORITY_BACKGROUND;
import static android.os.Process.setThreadPriority;

/**
 * Created by alecfelt on 11/26/17.
 */

public class MotionTask implements Runnable {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SensorEventListener listener;
    boolean alive;
    private final String LOG_TAG = "MotionTask";
    Handler handler;

    private float[] mGravity;
    private float mAccelCurrent;
    private float mAccelLast;
    float delta;


    public MotionTask(Context c, Handler h) {
        Log.i(LOG_TAG, "MotionTask()");
        handler = h;
        mSensorManager = (SensorManager) c.getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        delta = 0;
        listener = new SensorEventListener() {
            public void onSensorChanged (SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                    mGravity = event.values.clone();
                    // Shake detection
                    float x = mGravity[0];
                    float y = mGravity[1];
                    mAccelLast = mAccelCurrent;
                    mAccelCurrent = (float)Math.sqrt(x*x + y*y);
                    float delta = mAccelCurrent - mAccelLast;
                    if(delta > 0.1 || delta < -0.1) {
                        Message message = handler.obtainMessage();
                        message.obj = "movement";
                        handler.sendMessage(message);
                        Log.i(LOG_TAG, "Message sent from Task to Service");
                    }
                    // Adjust value based on desired sensitivity
                    Log.i(LOG_TAG, String.valueOf(delta));
                }
            }

            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        mSensorManager.registerListener(listener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void run() {
        alive = true;
        Log.i(LOG_TAG, "MotionTask is alive");
        setThreadPriority(THREAD_PRIORITY_BACKGROUND);
        while(alive) {
            ;
        }
        Log.i(LOG_TAG, "MotionTask is dead");
    }

    public void stopWork() {
        Log.i(LOG_TAG, "stopWork()");
        alive = false;
        mSensorManager.unregisterListener(listener);
    }
}
