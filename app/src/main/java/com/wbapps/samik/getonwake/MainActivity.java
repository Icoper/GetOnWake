package com.wbapps.samik.getonwake;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wbapps.samik.getonwake.engine.ArrayDataListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ImageButton onOffAppBtn;
    private CheckBox quickUnlockCheckBox;
    private SeekBar sensitivitySetterSeekBar;
    private TextView appInfoTextView;
    private TextView checkBoxInfoTextView;
    private TextView sensitivityVolumeTextView;

    // This variable indicates whether the service is currently running or not
    private Boolean serviceRun = false;
    // This variable indicates whether fast unlocking is selected
    private Boolean stateQuickUnlock = false;
    private int sensitivityApp;
    private DataBaseWorker dataWorker;

    private IntentServiceCustom serviceCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        getSettingsData();
        setupItems();
        updateUI();
    }

    // This method declares and sets fields
    private void initialize() {
        Singleton.getInstance().setMainContext(MainActivity.this);

        serviceCustom = new IntentServiceCustom(MainActivity.this);
        dataWorker = new DataBaseWorker(MainActivity.this);

        onOffAppBtn = (ImageButton) findViewById(R.id.ma_on_off_btn);

        quickUnlockCheckBox = (CheckBox) findViewById(R.id.ma_unlock_cb);
        quickUnlockCheckBox.setChecked(false);

        sensitivitySetterSeekBar = (SeekBar) findViewById(R.id.ma_seekBar);

        appInfoTextView = (TextView) findViewById(R.id.ma_app_info_tv);
        checkBoxInfoTextView = (TextView) findViewById(R.id.ma_check_box_info_tv);
        sensitivityVolumeTextView = (TextView) findViewById(R.id.ma_sensitivity_volume_tv);

    }

    /**
     * This method setup UI items & their work logic
     */
    private void setupItems() {
        final NotificationWorker notificationWorker = new NotificationWorker(this);

        sensitivitySetterSeekBar.setProgress(sensitivityApp);
        sensitivitySetterSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sensitivityVolumeTextView.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                dataWorker.updateSensitivity(seekBar.getProgress());
                sensitivityApp = seekBar.getProgress();
                showRestartToast();
            }
        });
        onOffAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (serviceRun) {
                    // stop work
                    notificationWorker.closeNotification();
                    serviceCustom.stopService();
                    checkBoxInfoTextView.setTypeface(Typeface.DEFAULT_BOLD);
                    serviceRun = false;
                } else {
                    // start work
                    notificationWorker.showNotification();
                    serviceCustom.onHandleIntent(new Intent(MainActivity.this, IntentServiceCustom.class));
                    checkBoxInfoTextView.setTypeface(Typeface.DEFAULT);
                    serviceRun = true;
                }
                updateUI();
            }
        });

        quickUnlockCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PowerManagerCustom powerManagerCustom = new PowerManagerCustom(MainActivity.this);
                powerManagerCustom.setqUnlock(isChecked);
            }
        });

    }

    // Called when the state of interface elements was changed
    private void updateUI() {
        if (serviceRun) {
            quickUnlockCheckBox.setEnabled(false);
            appInfoTextView.setText(R.string.app_status_on);
            onOffAppBtn.setBackgroundResource(R.mipmap.on_service_img);

        } else {
            quickUnlockCheckBox.setEnabled(true);
            appInfoTextView.setText(R.string.app_status_off);
            onOffAppBtn.setBackgroundResource(R.mipmap.off_service_img);
        }

        sensitivityVolumeTextView.setText(String.valueOf(sensitivityApp));
    }



    /**
     * This method loads user settings from SharedPreferences
     */
    private void getSettingsData() {
        sensitivityApp = dataWorker.getSensitivity();
        new ArrayDataListener().setSensitivityWakeUp(sensitivityApp);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        getSettingsData();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void showRestartToast() {
        Toast.makeText(this, R.string.need_restart, Toast.LENGTH_SHORT).show();
    }
}
