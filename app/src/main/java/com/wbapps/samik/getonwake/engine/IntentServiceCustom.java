package com.wbapps.samik.getonwake.engine;

import android.annotation.TargetApi;
import android.app.AutomaticZenRule;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.wbapps.samik.getonwake.R;
import com.wbapps.samik.getonwake.activity.MainActivity;
import com.wbapps.samik.getonwake.data.Singleton;

import java.util.List;

public class IntentServiceCustom extends Service {
    private static final Context MY_CONTEXT = Singleton.getInstance().getMainContext();
    private static final String CUSTOM_SERVICE_NAME = "IntentServiceCustom";
    private static final int NOTIFY_ID = 79;
    private NotificationManager notificationManager;

    private SensorManagerWorker sensorManager;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public IntentServiceCustom() {
        super();
    }

    private void startService() {
        Toast.makeText(this, R.string.app_status_on, Toast.LENGTH_SHORT).show();
        sensorManager = new SensorManagerWorker(this);
        Singleton.getInstance().setSensorManagerWorker(sensorManager);
        sensorManager.startRecording();


    }

    private void stopService() {
        Toast.makeText(this, R.string.app_status_off, Toast.LENGTH_SHORT).show();
        sensorManager.stopRecording();
        notificationManager.cancel(NOTIFY_ID);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(CUSTOM_SERVICE_NAME, "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(CUSTOM_SERVICE_NAME, "onCreate");
        super.onCreate();

        // create notification

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                NOTIFY_ID, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentText(this.getResources().getString(R.string.app_status_on))
                .setContentTitle(this.getResources().getString(R.string.app_name));

        Notification n = builder.build();

        n.flags = n.flags | Notification.FLAG_NO_CLEAR;
        startForeground(NOTIFY_ID, n);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(CUSTOM_SERVICE_NAME, "onStartCommand");
        startService();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        Log.d(CUSTOM_SERVICE_NAME, "onDestroy");
        stopService();
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i("Test", "Service: onTaskRemoved");
        stopService();
    }

}
