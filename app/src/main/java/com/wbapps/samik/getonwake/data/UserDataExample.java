package com.wbapps.samik.getonwake.data;

/**
 * Created by samik on 11.06.2017.
 */

public abstract class UserDataExample {
    // Acceleration request frequency
    public static final int ACCELERATION_REQUEST_0_2 = 200000;
    public static final int ACCELERATION_REQUEST_0_4 = 400000;
    public static final int ACCELERATION_REQUEST_0_6 = 600000;
    public static final int ACCELERATION_REQUEST_0_8 = 800000;
    public static final int ACCELERATION_REQUEST_1 = 1000000;
    public static final int ACCELERATION_REQUEST_2 = 2000000;

    // Acceleration sensitivity [0] -y_data and [1]-z_data, [2] - number of sensitivity
    public static final double[] SENSITIVITY_1 = {6, 7.8, 1};
    public static final double[] SENSITIVITY_2 = {6.8, 7.1, 2};
    public static final double[] SENSITIVITY_3 = {7.4, 6.5, 3};
    public static final double[] SENSITIVITY_4 = {8.0, 5.8, 4};
    public static final double[] SENSITIVITY_5 = {8.7, 4.7, 5};

}

