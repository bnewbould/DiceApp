package com.jc210391.diceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Math.*;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    TextView displayView;
    TextView currentPoolView;
    TextView historyIndexView;
    TextView historyView;

    int quantity;
    int sides;
    int targetNumber;

    int currentHistoryIndex;
    ArrayList<Roll> history;

    Roller roller;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("diceSettings", Context.MODE_PRIVATE);

        quantity = sharedPreferences.getInt("qty", 1);
        sides = sharedPreferences.getInt("sides", 6);
        targetNumber = sharedPreferences.getInt("tn", -1);

        displayView = findViewById(R.id.display);
        currentPoolView = findViewById(R.id.currentPool);
        historyIndexView = findViewById(R.id.historyIndex);
        historyView = findViewById(R.id.history);

        roller = new Roller();

        if (savedInstanceState == null) {
            history = new ArrayList<>();
            currentHistoryIndex = 1;
        } else {
            try {
                history = (ArrayList<Roll>) savedInstanceState.getSerializable("history");
                currentHistoryIndex = savedInstanceState.getInt("historyIndex");
            } catch (Exception e) {
                //Handling the exception doesn't really matter-- at that point the history data is gone, so let's carry on
                System.out.println(e);
                history = new ArrayList<>();
                currentHistoryIndex = 1;
            }

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("history", history);
        outState.putInt("historyIndex", currentHistoryIndex);
    }

    @Override
    protected void onStart() {
        super.onStart();

        quantity = sharedPreferences.getInt("qty", 1);
        sides = sharedPreferences.getInt("sides", 6);
        targetNumber = sharedPreferences.getInt("tn", -1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void rollClicked(View view) {
        history.add(targetNumber < 0 ? new AdditiveRoll(quantity, sides, roller.roll(quantity, sides)) : new TargetNumberRoll(quantity, sides, targetNumber, roller.roll(quantity, sides)));
        if (history.size() > 10) {
            history.remove(0);
        }
        currentHistoryIndex = history.size();
        updateUI();
    }

    public void historyClicked(View view) {
        if (view == findViewById(R.id.historyForward)) {
            currentHistoryIndex = min(currentHistoryIndex + 1, history.size());
        } else {
            currentHistoryIndex = max(currentHistoryIndex - 1, 1);
        }
        updateUI();
    }

    public void updateUI() {
        //results
        Roll r = null;
        if (!history.isEmpty()) {
            r = history.get(currentHistoryIndex - 1);
            displayView.setText(r.getResult());
        } else {
            displayView.setText(String.valueOf(0));
        }
        //pool
        if (r != null) {
            currentPoolView.setText(r.getPool());
        } else {
            currentPoolView.setText(getString(R.string.empty));
        }
        //history index
        historyIndexView.setText(String.valueOf(history.size() + 1 - currentHistoryIndex));
        //history at index
        if (r != null) {
            historyView.setText(r.getSequence());
        } else {
            historyView.setText(getString(R.string.empty));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
