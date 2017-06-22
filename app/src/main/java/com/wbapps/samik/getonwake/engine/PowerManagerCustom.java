package com.wbapps.samik.getonwake.engine;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;

import com.wbapps.samik.getonwake.data.SharedPreferencesWorker;
import com.wbapps.samik.getonwake.engine.SensorManagerWorker;

import java.util.HashMap;

public class PowerManagerCustom {
    private boolean qUnlock;
    private Context appContext;
    private SharedPreferencesWorker sharedPreferencesWorker;

    {
        sharedPreferencesWorker = new SharedPreferencesWorker(appContext);
        qUnlock = sharedPreferencesWorker.getQunlockState();
    }

    public PowerManagerCustom(Context appContext) {
        this.appContext = appContext;
    }

    public void wakeUp() {
        // Before awakening, check the condition of the proximity sensor
        if (new SensorManagerWorker().checkProximitySensor()) {
            screenOn();
            if (qUnlock) {
                unlockDevice();
            }
        }

    }

    private void screenOn() {
        PowerManager pm = (PowerManager) appContext.getSystemService(Context.POWER_SERVICE);

        if (!pm.isScreenOn()) {
            PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                    | PowerManager.FULL_WAKE_LOCK
                    | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");

            wakeLock.acquire();
            wakeLock.release();
        }

    }

    // When calling this method, we go around the lock screen
    private void unlockDevice() {
        KeyguardManager keyguardManager = (KeyguardManager) appContext.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
        keyguardLock.disableKeyguard();
    }

    public void setqUnlock(boolean qUnlock) {
        this.qUnlock = qUnlock;
    }
}
