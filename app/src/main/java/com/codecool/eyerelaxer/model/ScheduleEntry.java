package com.codecool.eyerelaxer.model;

public class ScheduleEntry {
    private int hour;
    private int minutes;

    public ScheduleEntry(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    public String hourToString() {
        if (hour < 10) {
            return String.format("0%d", hour);
        }
        return String.valueOf(hour);
    }

    public String minutesToString() {
        if (minutes < 10) {
            return String.format("0%d", minutes);
        }
        return String.valueOf(minutes);
    }

    public String timeToString() {
        return String.format("%s:%s", hourToString(), minutesToString());
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
