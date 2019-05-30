package com.mySampleApplication.client;

import java.io.Serializable;
import java.util.ArrayList;

public class WorkersAndPointsStruct implements Serializable {
    public Worker[] workers;
    public ArrayList<TradePoint> points;

    public WorkersAndPointsStruct(Worker[] workers, ArrayList<TradePoint> points) {
        this.workers = workers;
        this.points = points;
    }

    public WorkersAndPointsStruct() {
    }
}
