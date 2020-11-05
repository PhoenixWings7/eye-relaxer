package com.codecool.eyerelaxer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class AddToScheduleActivity extends AppCompatActivity {
    public static final String EXTRA_DAYS_CHECKED_INDICES = "com.codecool.eyerelaxer.DAYS_CHECKED_INDICES";
    public static final String EXTRA_TIME_PICKER_HOUR = "com.codecool.eyerelaxer.TIME_PICKER_HOUR";
    public static final String EXTRA_TIME_PICKER_MINUTE = "com.codecool.eyerelaxer.TIME_PICKER_MINUTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_schedule);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button submitBtn = findViewById(R.id.new_notification_submit_btn);
        submitBtn.setOnClickListener((view) -> setChosenTimeAndDay());

    }

    protected void setChosenTimeAndDay() {
        // get checked days
        ArrayList<Integer> daysCheckedIndices = new ArrayList<>();

        LinearLayout checkboxContainer = findViewById(R.id.checkboxes_container_layout);
        for (int i = 0; i < checkboxContainer.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) checkboxContainer.getChildAt(i);
            if (checkBox.isChecked()) {
                daysCheckedIndices.add(i);
            }
        }
        // convert ArrayList to int[]
        int[] extraArr = daysCheckedIndices.stream().mapToInt(i -> i).toArray();

        // get picked time
        TimePicker timePicker = findViewById(R.id.timePicker);
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        // put extras
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DAYS_CHECKED_INDICES, extraArr);
        intent.putExtra(EXTRA_TIME_PICKER_HOUR, hour);
        intent.putExtra(EXTRA_TIME_PICKER_MINUTE, minute);

        setResult(1, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_to_right, R.anim.slide_out_to_right);
    }

}
