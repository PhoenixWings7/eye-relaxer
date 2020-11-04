package com.codecool.eyerelaxer;

import android.content.Intent;
import android.os.Bundle;

import com.codecool.eyerelaxer.dao.CardDaoImplementation;
import com.codecool.eyerelaxer.model.Card;
import com.codecool.eyerelaxer.model.ScheduleEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String LOGCAT_TAG = "MainActivity";
    private static final int REQUEST_CODE = 1;
    CardRecyclerViewAdapter recyclerViewAdapter;
    CardDaoImplementation cardDaoImpl = CardDaoImplementation.getCardDao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add_new_notification_time_fab);
        fab.setOnClickListener(view -> startAddToScheduleActivity());

        // set RecyclerView
        RecyclerView recyclerView = findViewById(R.id.schedule_cards_container);
        recyclerViewAdapter = new CardRecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (savedInstanceState != null) {
            this.onRestoreInstanceState(savedInstanceState);
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
    protected void onResume() {
        super.onResume();
        Intent intentIncoming = getIntent();

        if (isScheduleChanged(intentIncoming)) {
            updateNotificationSchedule(intentIncoming);
        }
    }

    protected boolean isScheduleChanged(Intent intent) {
        Object[] daysChecked = intent.getStringArrayExtra(AddToScheduleActivity.EXTRA_DAYS_CHECKED_INDICES);
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
            updateNotificationSchedule(data);
        }
    }

    protected void updateNotificationSchedule(Intent intent) {
        int[] daysChecked = intent.getIntArrayExtra(AddToScheduleActivity.EXTRA_DAYS_CHECKED_INDICES);
        int hour = intent.getIntExtra(AddToScheduleActivity.EXTRA_TIME_PICKER_HOUR, 1);
        int minute = intent.getIntExtra(AddToScheduleActivity.EXTRA_TIME_PICKER_MINUTE, 0);


        for (int dayIndex:daysChecked) {

            Card scheduleCard = cardDaoImpl.getCardByIndex(dayIndex);

            if (scheduleCard != null) {
                ScheduleEntry scheduleEntry = new ScheduleEntry(hour, minute);
                cardDaoImpl.addScheduleToCard(dayIndex, scheduleEntry);
            }
            recyclerViewAdapter.notifyItemChanged(dayIndex);
        }
    }
}
