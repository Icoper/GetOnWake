package com.wbapps.samik.getonwake;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.wbapps.samik.getonwake.engine.SensorManagerWorker;

public class IntentServiceCustom extends IntentService {
    private static final String CUSTOM_SERVICE_NAME = "IntentServiceCustom";

    private Context context;
    private SensorManagerWorker sensorManager;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public IntentServiceCustom(Context context) {
        super(CUSTOM_SERVICE_NAME);
        this.context = context;
    }

    public void startService() {
        Toast.makeText(context, R.string.app_status_on, Toast.LENGTH_SHORT).show();
        sensorManager = new SensorManagerWorker(context);
        Singleton.getInstance().setSensorManagerWorker(sensorManager);

        sensorManager.startRecording();

    }

    public void stopService() {
        Toast.makeText(context, R.string.app_status_off, Toast.LENGTH_SHORT).show();
        sensorManager.stopRecording();

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        startService();
    }
}
