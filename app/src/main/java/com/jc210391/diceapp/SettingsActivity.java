package com.jc210391.diceapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Math.max;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    int[] diceTypes;

    int quantity, type, targetNumber;

    SeekBar quantitySeek;
    SeekBar typeSeek;
    SeekBar targetNumSeek;

    Switch targetNumSwitch;

    TextView quantityView;
    TextView typeView;
    TextView targetNumView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences("diceSettings", Context.MODE_PRIVATE);

        diceTypes = new int[]{1, 2, 3, 4, 6, 8, 10, 12, 20};

        quantitySeek = findViewById(R.id.quantitySeek);
        typeSeek = findViewById(R.id.typeSeek);
        targetNumSeek = findViewById(R.id.targetNumSeek);

        quantity = sharedPreferences.getInt("qty", 1);
        type = sharedPreferences.getInt("sides", 6);
        targetNumber = sharedPreferences.getInt("tn", -1);

        quantitySeek.setProgress(quantity - 1);
        for (int i = 0; i < diceTypes.length; i++) {
            if (type == diceTypes[i]) {
                typeSeek.setProgress(i);
                break;
            }
        }
        targetNumSeek.setMax(type - 1);
        targetNumSeek.setProgress(max(targetNumber - 1, 0));

        targetNumSwitch = findViewById(R.id.targetNumSwitch);

        quantityView = findViewById(R.id.quantityView);
        typeView = findViewById(R.id.typeView);
        targetNumView = findViewById(R.id.targetNumView);

        quantitySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                quantity = progress + 1;
                quantityView.setText(String.valueOf(quantity));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        typeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                type = diceTypes[progress];
                typeView.setText(String.valueOf(type));
                targetNumSeek.setMax(type - 1);
                if (targetNumSeek.getProgress() > type) {
                    targetNumSeek.setProgress(type);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        targetNumSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    targetNumSeek.setVisibility(View.VISIBLE);
                    targetNumber = targetNumSeek.getProgress() + 1;
                    targetNumView.setText(String.valueOf(targetNumber));
                } else {
                    targetNumSeek.setVisibility(View.INVISIBLE);
                    targetNumber = -1;
                    targetNumView.setText(getString(R.string.target_number_mode));
                }
            }
        });

        targetNumSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (targetNumSwitch.isChecked()) {
                    targetNumber = progress + 1;
                    targetNumView.setText(String.valueOf(targetNumber));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        quantityView.setText(String.valueOf(quantity));
        typeView.setText(String.valueOf(type));
        if (targetNumber > 0) {
            targetNumSwitch.setChecked(true);
            targetNumSeek.setVisibility(View.VISIBLE);
            targetNumView.setText(String.valueOf(targetNumber));
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, getString(R.string.settings_discarded), Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, getString(R.string.settings_applied), Toast.LENGTH_SHORT).show();
                sharedPreferences.edit().putInt("qty", quantity).apply();
                sharedPreferences.edit().putInt("sides", type).apply();
                sharedPreferences.edit().putInt("tn", targetNumber).apply();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
