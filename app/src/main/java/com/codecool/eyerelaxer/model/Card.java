package com.codecool.eyerelaxer.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Card {
    private @NonNull String title;
    private @Nullable String content;
    private final ArrayList<ScheduleEntry> scheduleEntries = new ArrayList<>();

    public Card(@NonNull String title) {
        this.title = title;
        content = null;
    }

    public Card(@NonNull String title, @NonNull String content) {
        this.title = title;
        this.content = content;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @Nullable
    public String getContent() {
        return content;
    }

    public void setContent(@Nullable String content) {
        this.content = content;
    }

    public ArrayList<ScheduleEntry> getScheduleEntries() {
        return scheduleEntries;
    }

    public String getSchedulesAsString() {
        StringBuilder sb = new StringBuilder();
        for (ScheduleEntry scheduleEntry : scheduleEntries) {
            sb.append(scheduleEntry.timeToString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public void addScheduleEntry(ScheduleEntry entry) {
        this.scheduleEntries.add(entry);
    }

    public int getScheduleCount() {
        return scheduleEntries.size();
    }

    public ScheduleEntry getScheduleEntry(int index) {
        return scheduleEntries.get(index);
    }
}
