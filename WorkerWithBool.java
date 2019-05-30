package com.mySampleApplication.client.GXTClient;

import com.mySampleApplication.client.Worker;

import java.io.Serializable;

public class WorkerWithBool implements Serializable {
    public Worker worker;
    public boolean marker;

    public WorkerWithBool(Worker worker, boolean marker) {
        this.worker = worker;
        this.marker = marker;
    }

    public WorkerWithBool() {
        worker = new Worker();
        marker = false;
    }
}
