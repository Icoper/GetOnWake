package com.wbapps.samik.getonwake.engine;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.wbapps.samik.getonwake.Singleton;

import static android.content.Context.SENSOR_SERVICE;


public class SensorManagerWorker implements SensorEventListener {
    private static final String LOG_TAG = "SensorManagerWorker";

    private static SensorManager sensorManager;
    private static boolean proximityState;
    private Context context;
    private Sensor sensorAccelerometer;
    private Sensor sensorProximity;


    public SensorManagerWorker(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
    }

    public SensorManagerWorker() {
    }

    /**
     * This method is called ones when the user starts the service.
     * In this method, we initialize the sensors, and subscribe them to the listener.
     */
    public void startRecording() {
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        // Если датчик приближения недоступен, проверка недоступна, значсит proximityState всегда равен true
        if (sensorProximity == null) {
            proximityState = true;
        }

        regAccelSensorListener();
        regProximitySensorListener();
    }

    /**
     * This method is called ones when the user stop the service.
     * In this method un subscribe all listeners.
     */
    public void stopRecording() {
        unregProximitySensorListener();
        unregAccelSensorListener();
    }

    /**
     * Check whether the smartphone is in your pocket or not.
     *
     * @return false, if proximity data is 1.0
     */
    public boolean checkProximitySensor() {
        Log.d(LOG_TAG, "proximitySensor - " + proximityState);
        return proximityState;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(LOG_TAG, "onSensorChanged");

        final ArrayAdapterCustom adapterCustom = new ArrayAdapterCustom();

        // We register and send each sensor reading in ArrayAdapterCustom
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.d(LOG_TAG, "event - accelerometer");
            adapterCustom.addItem(
                    event.values[0],
                    event.values[1],
                    event.values[2]
            );

            // We monitor the state of the proximity sensor, and in case it is not closed,
            // proximityState set true
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            Log.d(LOG_TAG, "event - proximity " + event.values[0]);

            // boolean
            proximityState = (event.values[0] == 1.0);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void regAccelSensorListener() {
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void unregAccelSensorListener() {
        sensorManager.unregisterListener(this, sensorAccelerometer);
    }

    private void regProximitySensorListener() {
        sensorManager.registerListener(this, sensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void unregProximitySensorListener() {
        sensorManager.unregisterListener(this, sensorProximity);
    }

}
