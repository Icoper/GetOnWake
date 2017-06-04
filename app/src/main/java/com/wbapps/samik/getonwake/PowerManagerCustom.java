package com.wbapps.samik.getonwake;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;

import com.wbapps.samik.getonwake.engine.SensorManagerWorker;

import java.util.HashMap;

public class PowerManagerCustom {
    private boolean qUnlock;
    private Context appContext;

    public PowerManagerCustom(Context appContext) {
        this.appContext = appContext;
    }

    public void wakeUp() {

        if (new SensorManagerWorker().checkProximitySensor()) {
            screenOn();
            if (qUnlock) {
                unlockDevice();
            }
        }

    }

    private void screenOn() {
        PowerManager pm = (PowerManager) appContext.getSystemService(Context.POWER_SERVICE);

        PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                | PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");

        wakeLock.acquire();
        wakeLock.release();
    }

    private void unlockDevice() {
        KeyguardManager keyguardManager = (KeyguardManager) appContext.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
        keyguardLock.disableKeyguard();
    }

    public void setqUnlock(boolean qUnlock) {
        this.qUnlock = qUnlock;
    }
}
