package com.wbapps.samik.getonwake.engine;

import android.util.Log;

import java.util.ArrayList;


public class ArrayAdapterCustom {
    private static final String LOG_TAG = "ArrayAdapterCustom";
    private static final int MAX_ARRAY_SIZE = 4;// Maximum line of data array with accelerometer

    private static ArrayList<float[]> dataRegList; // Store the data that returned the accelerometer

    private ArrayDataListener arrayDataListener;


    public ArrayAdapterCustom() {
        Log.d(LOG_TAG, "is called");
        initialize();
    }

    // Initialize the elements of the class
    private void initialize() {
        if (dataRegList == null) {
            dataRegList = new ArrayList<>();
        }
        arrayDataListener = new ArrayDataListener();
    }


    /**
     * This method gets the latest data from the accelerometer, and saves them to an array.
     * In this case, the length of the array is stored equal to MAX_ARRAY_SIZE.
     * A new element is added to the code, the last element is deleted.
     */
    public void addItem(float x, float y, float z) {

        // write the data on X, Y, Z into an array, which is then added to the dataRegList
        float[] temp = new float[3];
        temp[0] = x;
        temp[1] = y;
        temp[2] = z;

        dataRegList.add(temp);

        if (dataRegList.size() >= MAX_ARRAY_SIZE) {

            dataRegList.remove(0);

            if (dataRegList != null) {
                arrayDataListener.updateDataArray(dataRegList);
            }

        }
    }

}
