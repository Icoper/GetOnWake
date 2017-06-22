package com.wbapps.samik.getonwake.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by samik on 11.06.2017.
 */

public class SharedPreferencesWorker {
    private static final String APP_PREFERENCES = "get_on_wake_pref";
    private static final String APP_PREFERENCES_SENSITIVITY_Y = "sensitivity_y";
    private static final String APP_PREFERENCES_SENSITIVITY_Z = "sensitivity_z";
    private static final String APP_PREFERENCES_SENSITIVITY_STEP = "sensitivity_step";
    private static final String APP_PREFERENCES_QUNLOCK = "qunlock";
    private static final String APP_PREFERENCES_REQUEST_FREQUENCY = "request_frequency";


    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferencesWorker(Context context) {
        this.context = context;
    }

    public void saveSensitivity(double[] data) {
        float y = (float) data[0];
        float z = (float) data[1];
        float step = (float) data[2];
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putFloat(APP_PREFERENCES_SENSITIVITY_Y, y);
        editor.putFloat(APP_PREFERENCES_SENSITIVITY_Z, z);
        editor.putFloat(APP_PREFERENCES_SENSITIVITY_STEP, step);
        editor.apply();
    }

    public double[] getSensitivity() {
        float y = 0;
        float z = 0;
        float step;
        double[] data = new double[3];

        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(APP_PREFERENCES_SENSITIVITY_Z)
                && sharedPreferences.contains(APP_PREFERENCES_SENSITIVITY_Y)) {

            y = sharedPreferences.getFloat(APP_PREFERENCES_SENSITIVITY_Y, 0);
            z = sharedPreferences.getFloat(APP_PREFERENCES_SENSITIVITY_Z, 0);
            step = sharedPreferences.getFloat(APP_PREFERENCES_SENSITIVITY_STEP, 0);
            data[0] = (double) y;
            data[1] = (double) z;
            data[2] = (double) step;
            return data;
        }

        return UserDataExample.SENSITIVITY_3;
    }

    public void saveRequest(int volume) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(APP_PREFERENCES_REQUEST_FREQUENCY, volume);
        editor.apply();

    }

    public int getRequest() {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(APP_PREFERENCES_REQUEST_FREQUENCY)) {
            return sharedPreferences.getInt(APP_PREFERENCES_REQUEST_FREQUENCY, 0);
        }

        return UserDataExample.ACCELERATION_REQUEST_0_6;
    }

    public void saveQunlockState(boolean state) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(APP_PREFERENCES_QUNLOCK, state);
        editor.apply();

    }

    public boolean getQunlockState() {
        sharedPreferences = Singleton.getInstance().getMainContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(APP_PREFERENCES_QUNLOCK)) {
            return sharedPreferences.getBoolean(APP_PREFERENCES_QUNLOCK, false);
        }

        return true;
    }
}
