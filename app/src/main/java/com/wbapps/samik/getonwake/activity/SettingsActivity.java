package com.wbapps.samik.getonwake.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wbapps.samik.getonwake.R;
import com.wbapps.samik.getonwake.data.SharedPreferencesWorker;
import com.wbapps.samik.getonwake.data.UserDataExample;
import com.wbapps.samik.getonwake.engine.PowerManagerCustom;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferencesWorker sharedPreferencesWorker;
    private CheckBox quickUnlockCheckBox;
    private TextView requestTextView;
    private SeekBar sensitivitySeekBar;
    private ImageView sensitivityImageView;
    private AlertDialog alertDialog;

    private int requestVolume;
    private boolean qUnlock;
    private int sensitivityPosition;
    ArrayList<Integer> requestDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupActionBar();
        loadUserSettings();
        initItems();
        updateUI();
    }

    private void loadUserSettings() {
        sharedPreferencesWorker = new SharedPreferencesWorker(this);

        requestVolume = sharedPreferencesWorker.getRequest();
        qUnlock = sharedPreferencesWorker.getQunlockState();
        double[] data = sharedPreferencesWorker.getSensitivity();

        if ((int) data[2] == 1) {
            sensitivityPosition = 0;
        } else if ((int) data[2] == 2) {
            sensitivityPosition = 1;
        } else if ((int) data[2] == 3) {
            sensitivityPosition = 2;
        } else if ((int) data[2] == 4) {
            sensitivityPosition = 3;
        } else if ((int) data[2] == 5) {
            sensitivityPosition = 4;
        }
    }

    private void initItems() {
        quickUnlockCheckBox = (CheckBox) findViewById(R.id.sa_unlock_cb);
        requestTextView = (TextView) findViewById(R.id.sa_request_volume);
        sensitivitySeekBar = (SeekBar) findViewById(R.id.sa_seek_bar);
        sensitivityImageView = (ImageView) findViewById(R.id.sa_sensitivity_image);

        quickUnlockCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PowerManagerCustom powerManagerCustom = new PowerManagerCustom(SettingsActivity.this);
                powerManagerCustom.setqUnlock(isChecked);
                sharedPreferencesWorker.saveQunlockState(isChecked);
            }
        });

        requestTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogData();
            }
        });

        sensitivitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateImageSensitivity(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateSensitivity(seekBar.getProgress());
                showRestartToast();
            }
        });

    }

    private void showDialogData() {
        requestDataList = new ArrayList<>();
        requestDataList.add(UserDataExample.ACCELERATION_REQUEST_0_2);
        requestDataList.add(UserDataExample.ACCELERATION_REQUEST_0_4);
        requestDataList.add(UserDataExample.ACCELERATION_REQUEST_0_6);
        requestDataList.add(UserDataExample.ACCELERATION_REQUEST_0_8);
        requestDataList.add(UserDataExample.ACCELERATION_REQUEST_1);
        requestDataList.add(UserDataExample.ACCELERATION_REQUEST_2);

        LayoutInflater layoutInflater = LayoutInflater.from(SettingsActivity.this);
        View view = layoutInflater.inflate(R.layout.alert_dialog_request, null);

        AlertDialog.Builder builder = new AlertDialog
                .Builder(new ContextThemeWrapper(SettingsActivity.this, R.style.AppTheme));


        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.ad_recycler_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(builder.getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        RVAdapter adapter = new RVAdapter();
        mRecyclerView.setAdapter(adapter);

        builder.setView(view);

        // customize dialog theme
        alertDialog = builder.create();
        alertDialog.setTitle(getString(R.string.alert_title));
        alertDialog.show();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void updateRequest(int position) {
        showRestartToast();
        alertDialog.dismiss();
        int volume = 0;
        switch (position) {
            case 0:
                volume = UserDataExample.ACCELERATION_REQUEST_0_2;
                break;
            case 1:
                volume = UserDataExample.ACCELERATION_REQUEST_0_4;
                break;
            case 2:
                volume = UserDataExample.ACCELERATION_REQUEST_0_6;
                break;
            case 3:
                volume = UserDataExample.ACCELERATION_REQUEST_0_8;
                break;
            case 4:
                volume = UserDataExample.ACCELERATION_REQUEST_1;
                break;
            case 5:
                volume = UserDataExample.ACCELERATION_REQUEST_2;
                break;
        }

        if (sharedPreferencesWorker == null) {
            sharedPreferencesWorker = new SharedPreferencesWorker(this);
        }
        sharedPreferencesWorker.saveRequest(volume);
        requestVolume = volume;
        updateUI();
    }

    private void updateSensitivity(int step) {
        showRestartToast();
        if (sharedPreferencesWorker == null) {
            sharedPreferencesWorker = new SharedPreferencesWorker(this);
        }
        double[] data = new double[2];
        switch (step) {
            case 0:
                data = UserDataExample.SENSITIVITY_1;
                break;
            case 1:
                data = UserDataExample.SENSITIVITY_2;
                break;
            case 2:
                data = UserDataExample.SENSITIVITY_3;
                break;
            case 3:
                data = UserDataExample.SENSITIVITY_4;
                break;
            case 4:
                data = UserDataExample.SENSITIVITY_5;
                break;
        }
        sharedPreferencesWorker.saveSensitivity(data);
        sharedPreferencesWorker.saveSensitivity(data);
    }

    private void updateImageSensitivity(int step) {
        switch (step) {
            case 0:
                sensitivityImageView.setImageResource(R.drawable.sensitivity_1);
                break;
            case 1:
                sensitivityImageView.setImageResource(R.drawable.sensitivity_2);
                break;
            case 2:
                sensitivityImageView.setImageResource(R.drawable.sensitivity_3);
                break;
            case 3:
                sensitivityImageView.setImageResource(R.drawable.sensitivity_4);
                break;
            case 4:
                sensitivityImageView.setImageResource(R.drawable.sensitivity_5);
                break;
        }
    }

    private void updateUI() {
        if (qUnlock) {
            quickUnlockCheckBox.setChecked(true);
        } else quickUnlockCheckBox.setChecked(false);

        requestTextView.setText(getString(R.string.settings_press_to_edit) +
                " " +
                String.format("%,d", requestVolume) + " " +
                getString(R.string.request_sec));

        switch (sensitivityPosition) {
            case 0:
                sensitivityImageView.setImageResource(R.drawable.sensitivity_1);
                break;
            case 1:
                sensitivityImageView.setImageResource(R.drawable.sensitivity_2);
                break;
            case 2:
                sensitivityImageView.setImageResource(R.drawable.sensitivity_3);
                break;
            case 3:
                sensitivityImageView.setImageResource(R.drawable.sensitivity_4);
                break;
            case 4:
                sensitivityImageView.setImageResource(R.drawable.sensitivity_5);
                break;
        }
        sensitivitySeekBar.setProgress(sensitivityPosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showRestartToast() {
        Toast.makeText(this, R.string.need_restart, Toast.LENGTH_SHORT).show();
    }


    private class RVAdapter extends RecyclerView.Adapter<SettingsActivity.RequestListViewHolder> {

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public RequestListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.request_type_item, parent, false);

            RequestListViewHolder listViewHolder = new RequestListViewHolder(v);
            return listViewHolder;
        }

        @Override
        public void onBindViewHolder(RequestListViewHolder holder, final int position) {

            if (!requestDataList.isEmpty()) {

                holder.requestType.setText(String.format("%,d", requestDataList.get(position)) + " " +
                        getString(R.string.request_sec));

                holder.requestType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateRequest(position);
                        requestTextView.setText(getString(R.string.settings_press_to_edit) +
                                " " +
                                String.format("%,d", requestVolume) + " " +
                                getString(R.string.request_sec));
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return requestDataList.size();
        }
    }

    private static class RequestListViewHolder extends RecyclerView.ViewHolder {

        private TextView requestType;

        private RequestListViewHolder(View itemView) {
            super(itemView);
            requestType = (TextView) itemView.findViewById(R.id.rq_alert_text);
        }
    }


}
