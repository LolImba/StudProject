package com.mySampleApplication.client;

import java.io.Serializable;

public class HoursAndMinutes implements Serializable, Comparable<HoursAndMinutes> {
    private int hours;
    private int minutes;

    public HoursAndMinutes(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public HoursAndMinutes(){
        hours = 0;
        minutes = 0;
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

    public void addTime(HoursAndMinutes time){
//        if(minutes < 0){
//            hours--;
//            minutes += 60;
//        }
        hours += time.getHours();
        minutes += time.getMinutes();
        if(minutes >= 60){
            hours++;
            minutes -= 60;
        }
    }

    public void addMinutes(int minutes){
        hours += minutes / 60;
        this.minutes += minutes % 60;
        if(this.minutes > 60){
            this.minutes -= 60;
            hours++;
        }
    }

    @Override
    public int compareTo(HoursAndMinutes o) {
        if(hours > o.hours || (hours == o.hours && minutes > o.minutes))
            return 1;
        else if (hours < o.hours || (hours == o.hours && minutes < o.minutes))
            return -1;
        else
            return 0;
    }

    public int getTimeInMinutes(){
        return hours * 60 + minutes;
    }

    public String asString(){
        return hours + ":" + minutes;
    }
}
