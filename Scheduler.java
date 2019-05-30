package com.mySampleApplication.client;

import java.util.ArrayList;

public class Scheduler {
    private ArrayList<TradePoint> points;
    private int startHour;
    private int endHour;

    public Scheduler(ArrayList<TradePoint> points, int startHour, int endHour) {
        this.points = points;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public Scheduler(ArrayList<TradePoint> points) {
        this.points = points;
        this.startHour = 0;
        this.endHour = 23;
    }

    public ArrayList<TradePoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<TradePoint> points) {
        this.points = points;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }
}
