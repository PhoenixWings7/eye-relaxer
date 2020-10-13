package com.codecool.eyerelaxer;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.PersistableBundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_SCHEDULES_ARRAY ="com.codecool.eyerelaxer.SCHEDULES_ARRAY";
    private static final String LOGCAT_TAG = "MainActivity";
    private static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add_new_notification_time_fab);
        fab.setOnClickListener(view -> startAddToScheduleActivity());

        Intent intentIncoming = getIntent();

        if (savedInstanceState != null) {
            this.onRestoreInstanceState(savedInstanceState);
        }
        if (isScheduleChanged(intentIncoming)) {
            setPickedNotificationSchedule(intentIncoming);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList <String> schedules = new ArrayList<>();

        LinearLayout cardsContainer = findViewById(R.id.schedule_cards_container);
        final int insideLayoutPosition = 0;
        final int schedulePosition = 1;

        for (int i = 0; i < cardsContainer.getChildCount(); i++) {
            String scheduleString = null;
            try {
                CardView card = (CardView) cardsContainer.getChildAt(i);
                ConstraintLayout cardInsideLayout = (ConstraintLayout) card.getChildAt(insideLayoutPosition);
                TextView daySchedule = (TextView) cardInsideLayout.getChildAt(schedulePosition);
                scheduleString = daySchedule.getText().toString();

            } catch (NullPointerException|ClassCastException e) {
                Log.e(LOGCAT_TAG, e.getMessage() != null ? e.getMessage() : "onSaveInstanceState went wrong");
            }
            if (scheduleString != null) {
                schedules.add(i, scheduleString);
            }
        }
        outState.putStringArrayList(EXTRA_SCHEDULES_ARRAY, schedules);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Log.e(LOGCAT_TAG, "saved state is null");
            return;
        }
        super.onRestoreInstanceState(savedInstanceState);

        ArrayList<String> savedSchedules = savedInstanceState.getStringArrayList(EXTRA_SCHEDULES_ARRAY);

        LinearLayout cardsContainer = findViewById(R.id.schedule_cards_container);
        final int insideLayoutPosition = 0;
        final int schedulePosition = 1;

        for (int i = 0; i < cardsContainer.getChildCount(); i++) {
            try {
                CardView card = (CardView) cardsContainer.getChildAt(i);
                ConstraintLayout cardInsideLayout = (ConstraintLayout) card.getChildAt(insideLayoutPosition);
                TextView daySchedule = (TextView) cardInsideLayout.getChildAt(schedulePosition);
                String scheduleString = savedSchedules.get(i);
                daySchedule.setText(scheduleString);

            } catch (NullPointerException|ClassCastException e) {
                Log.e(LOGCAT_TAG, e.getMessage() != null ? e.getMessage() : "onRestoreInstanceState went wrong");
            }
        }
    }



    protected boolean isScheduleChanged(Intent intent) {
        Object[] daysChecked = intent.getStringArrayExtra(AddToScheduleActivity.EXTRA_DAYS_CHECKED);
        return (!Objects.equals(daysChecked, null));
    }

    protected void startAddToScheduleActivity() {
        Intent intent = new Intent(this, AddToScheduleActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            setPickedNotificationSchedule(data);

        }
    }

    protected void setPickedNotificationSchedule(Intent intent) {
        String[] daysChecked = intent.getStringArrayExtra(AddToScheduleActivity.EXTRA_DAYS_CHECKED);
        int hour = intent.getIntExtra(AddToScheduleActivity.EXTRA_TIME_PICKER_HOUR, 1);
        int minute = intent.getIntExtra(AddToScheduleActivity.EXTRA_TIME_PICKER_MINUTE, 0);

        LinearLayout scheduleCardsContainer = findViewById(R.id.schedule_cards_container);

        CardView scheduleCardView = null;

        for (String day:daysChecked) {

            for (int i = 0; i < scheduleCardsContainer.getChildCount(); i++) {
                String layoutDayName = getResources()
                        .getResourceEntryName(scheduleCardsContainer.getChildAt(i).getId());
                if (day.toLowerCase().equals(layoutDayName.toLowerCase())) {
                    scheduleCardView = (CardView) scheduleCardsContainer.getChildAt(i);
                }
            }

            if (scheduleCardView != null) {
                ConstraintLayout cardInnerLayout = (ConstraintLayout) scheduleCardView.getChildAt(0);
                int SCHEDULE_TEXTVIEW_IN_CARD_INNER_LAYOUT_INDEX = 1;
                TextView scheduleTextView = (TextView) cardInnerLayout
                        .getChildAt(SCHEDULE_TEXTVIEW_IN_CARD_INNER_LAYOUT_INDEX);

                StringBuilder scheduleTextSB = new StringBuilder(scheduleTextView.getText());
                scheduleTextSB.append(String.format("\n %s:%s", hour, minute));
                scheduleTextView.setText(scheduleTextSB.toString());
            }
        }
    }
}
