package com.wbapps.samik.getonwake;

import android.content.Context;

import com.wbapps.samik.getonwake.engine.SensorManagerWorker;

/**
 * Created by samik on 18.05.2017.
 */

public class Singleton {
    private static Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    public Singleton() {
    }

    private Context mainContext;
    private SensorManagerWorker sensorManagerWorker;

    public SensorManagerWorker getSensorManagerWorker() {
        return sensorManagerWorker;
    }

    public void setSensorManagerWorker(SensorManagerWorker sensorManagerWorker) {
        this.sensorManagerWorker = sensorManagerWorker;
    }

    public Context getMainContext() {
        return mainContext;
    }

    public void setMainContext(Context mainContext) {
        this.mainContext = mainContext;
    }
}
