package com.wbapps.samik.getonwake;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

public class NotificationWorker {

    private static final int NOTIFY_ID = 79;
    private Context context;

    public NotificationWorker(Context context) {
        this.context = context;
    }

    public void showNotification() {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentText(context.getResources().getString(R.string.app_status_on))
                .setContentTitle(context.getResources().getString(R.string.app_name));


        Notification n = builder.build();
        n.flags = n.flags | Notification.FLAG_NO_CLEAR;
        nm.notify(NOTIFY_ID, n);
    }

    public void closeNotification() {
        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        manager.cancel(NOTIFY_ID);
    }

}
