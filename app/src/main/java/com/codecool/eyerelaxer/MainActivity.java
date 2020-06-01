package com.codecool.eyerelaxer;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add_new_notification_time_fab);
        fab.setOnClickListener(view -> startAddToScheduleActivity());

        Intent intentIncoming = getIntent();

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

    protected boolean isScheduleChanged(Intent intent) {
        String[] daysChecked = intent.getStringArrayExtra(AddToScheduleActivity.EXTRA_DAYS_CHECKED);
        return (daysChecked != null);
    }

    protected void startAddToScheduleActivity() {
        Intent intent = new Intent(this, AddToScheduleActivity.class);
        startActivity(intent);
    }

    protected void setPickedNotificationSchedule(Intent intent) {
        String[] daysChecked = intent.getStringArrayExtra(AddToScheduleActivity.EXTRA_DAYS_CHECKED);
        int hour = intent.getIntExtra(AddToScheduleActivity.EXTRA_TIME_PICKER_HOUR, 1);
        int minute = intent.getIntExtra(AddToScheduleActivity.EXTRA_TIME_PICKER_MINUTE, 0);

        TextView schedule;
        for (String day:daysChecked) {
            switch (day) {
                case "Monday":
                    schedule = findViewById(R.id.monday_schedule);
                    break;
                case "Tuesday":
                    schedule = findViewById(R.id.tuesday_schedule);
                    break;
                default:
                    schedule = null;
            }
            if (schedule != null) {
                StringBuilder scheduleTextSB = new StringBuilder(schedule.getText());
                scheduleTextSB.append(String.format("\n %s:%s", hour, minute));
                schedule.setText(scheduleTextSB.toString());
            }
        }
    }
}
