package com.mySampleApplication.client;

import java.sql.Date;

public class DateStructure {
    private Date date;
    private int hours;
    private int minutes;

    public DateStructure(Date date, HoursAndMinutes time) {
        this.date = date;
        this.hours = time.getHours();
        this.minutes = time.getMinutes();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
