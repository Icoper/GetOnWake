package com.wbapps.samik.getonwake.engine;

import android.util.Log;

import com.wbapps.samik.getonwake.data.SharedPreferencesWorker;
import com.wbapps.samik.getonwake.data.Singleton;

/**
 * Created by samik on 18.05.2017.
 */

public class AccelerometerDataListener {
    private static final String LOG_TAG = "AccelerometerDataListener";
    private SharedPreferencesWorker sharedPreferencesWorker;
    private double y_data = 0;
    private double z_data = 0;

    public AccelerometerDataListener() {
        Log.d(LOG_TAG, " is called");

        sharedPreferencesWorker = new SharedPreferencesWorker(Singleton.getInstance().getMainContext());
        double[] data = sharedPreferencesWorker.getSensitivity();
        y_data = data[0];
        z_data = data[1];

    }

    /**
     * Этот метод отвечает за обработку данных акселерометраа и решает,
     * будить устройство или нет
     */
    public void updateDataArray(float valueX, float valueY, float valueZ) {
        if (valueY >= y_data && valueZ <= z_data) {
            PowerManagerCustom powerManagerCustom = new PowerManagerCustom(Singleton.getInstance().getMainContext());
            powerManagerCustom.wakeUp();
        }

        Log.d(LOG_TAG, "Y = " + valueY + "\nZ = " + valueZ);
    }


}
