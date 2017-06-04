package com.wbapps.samik.getonwake;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBaseWorker {
    private static final String APP_PREFERENCES = "get_on_wake_pref";
    private static final String APP_PREFERENCES_SENSITIVITY = "sensitivity";

    private SharedPreferences sharedPreferences;
    private Context context;

    public DataBaseWorker(Context context) {
        this.context = context;
    }

    public void updateSensitivity(int volume) {
        sharedPreferences = context
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(APP_PREFERENCES_SENSITIVITY, volume);
        editor.apply();
    }

    public int getSensitivity() {
        int volume = 0;
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(APP_PREFERENCES_SENSITIVITY)) {
            volume = sharedPreferences.getInt(APP_PREFERENCES_SENSITIVITY, 8);
        }

        return volume;
    }

}
