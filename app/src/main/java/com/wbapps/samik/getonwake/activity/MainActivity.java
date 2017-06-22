package com.wbapps.samik.getonwake.activity;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.wbapps.samik.getonwake.engine.IntentServiceCustom;
import com.wbapps.samik.getonwake.engine.PowerManagerCustom;
import com.wbapps.samik.getonwake.R;
import com.wbapps.samik.getonwake.data.Singleton;
import com.wbapps.samik.getonwake.engine.SensorManagerWorker;

public class MainActivity extends AppCompatActivity {
    private static final String ADMOB_APP_ID = "ca-app-pub-7209919469454946~8280778119";
    private ImageButton onOffAppBtn;
    private TextView appInfoTextView;

    private AdView mAdView;

    // This variable indicates whether the service is currently running or not
    private static Boolean serviceRun = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        setupItems();
        updateUI();

        MobileAds.initialize(this, ADMOB_APP_ID);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    // This method declares and sets fields
    private void initialize() {
        Singleton.getInstance().setMainContext(MainActivity.this);

        onOffAppBtn = (ImageButton) findViewById(R.id.ma_on_off_btn);
        appInfoTextView = (TextView) findViewById(R.id.ma_app_info_tv);

    }

    /**
     * This method setup UI items & their work logic
     */
    private void setupItems() {
        onOffAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (serviceRun) {
                    // stop work
                    stopService(new Intent(MainActivity.this, IntentServiceCustom.class));
                    serviceRun = false;
                } else {
                    // start work

                    startService(new Intent(MainActivity.this, IntentServiceCustom.class));
                    serviceRun = true;
                }
                updateUI();
            }
        });


    }

    // Called when the state of interface elements was changed
    private void updateUI() {
        if (serviceRun) {
            appInfoTextView.setText(R.string.app_status_on);
            onOffAppBtn.setBackgroundResource(R.drawable.button_green);

        } else {
            appInfoTextView.setText(R.string.app_status_off);
            onOffAppBtn.setBackgroundResource(R.drawable.button_red);
        }


    }

    @Override
    protected void onDestroy() {
        mAdView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mAdView.pause();
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        updateUI();
        mAdView.resume();
        super.onPostResume();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

}
