package com.mySampleApplication.client.GXTClient;

import com.mySampleApplication.client.HoursAndMinutes;
import com.mySampleApplication.client.TradePoint;

import java.io.Serializable;

public class PointStartEndTime implements Serializable {
    TradePoint point;
    HoursAndMinutes startTime;
    HoursAndMinutes endTime;
    boolean timeMarker;

    public PointStartEndTime(TradePoint point, HoursAndMinutes startTime, HoursAndMinutes endTime, boolean timeMarker) {
        this.point = point;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeMarker = timeMarker;
    }

    public PointStartEndTime() {
        point = new TradePoint();
        startTime = new HoursAndMinutes(0, 0);
        endTime = new HoursAndMinutes(23, 30);
        timeMarker = false;
    }

    public TradePoint getPoint() {
        return point;
    }

    public void setPoint(TradePoint point) {
        this.point = point;
    }

    public HoursAndMinutes getStartTime() {
        return startTime;
    }

    public void setStartTime(HoursAndMinutes startTime) {
        this.startTime = startTime;
    }

    public HoursAndMinutes getEndTime() {
        return endTime;
    }

    public void setEndTime(HoursAndMinutes endTime) {
        this.endTime = endTime;
    }

    public boolean isTimeMarker() {
        return timeMarker;
    }

    public void setTimeMarker(boolean timeMarker) {
        this.timeMarker = timeMarker;
    }
}

