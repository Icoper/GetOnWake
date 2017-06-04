package com.wbapps.samik.getonwake.engine;

import android.util.Log;

import com.wbapps.samik.getonwake.PowerManagerCustom;
import com.wbapps.samik.getonwake.Singleton;

import java.util.ArrayList;

/**
 * Created by samik on 18.05.2017.
 */

public class ArrayDataListener {
    private static final String LOG_TAG = "ArrayDataListener";
    private static int sensitivityWakeUp;


    public ArrayDataListener() {
        Log.d(LOG_TAG, " is called");
    }

    /**
     * Этот метод отвечает за обработку данных акселерометраа и решает,
     * будить устройство или нет
     *
     * @param list двумерный массив, содержит актуальные данные с акселерометра.
     */
    public void updateDataArray(ArrayList<float[]> list) {

        float lastData = 0;
        float oldData = 0;

        int listSize = list.size();

        for (int i = 1; i <= 2; i++) {
            float[] temp = list.get(listSize - i);
            lastData = lastData + temp[0] + temp[1] + temp[2];
        }

        for (int j = 0; j <= 2; j++) {
            float[] temp = list.get(j);
            oldData = oldData + temp[0] + temp[1] + temp[2];
        }

        if (oldData < 0) {
            oldData = oldData * -1;
        }
        if (lastData < 0) {
            lastData = lastData * -1;
        }
        Log.d(LOG_TAG, "data - " + (oldData - lastData) + "| sensitivity " + sensitivityWakeUp);

        if ((oldData - lastData) <= sensitivityWakeUp) {
            PowerManagerCustom powerManagerCustom = new PowerManagerCustom(Singleton.getInstance().getMainContext());
            powerManagerCustom.wakeUp();
        }
    }

    /**
     * This method is called when the user changes the device wake sensitivity
     *
     * @param volume Sensitivity selected by the user.
     */
    public void setSensitivityWakeUp(int volume) {
        sensitivityWakeUp = volume;
    }

}
