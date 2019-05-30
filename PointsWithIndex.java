package com.mySampleApplication.client.GXTClient;

import com.mySampleApplication.client.TradePoint;

import java.io.Serializable;

public class PointsWithIndex implements Serializable {
    TradePoint tradePoint;
    int index;

    public PointsWithIndex(TradePoint tradePoint, int index) {
        this.tradePoint = tradePoint;
        this.index = index;
    }

    public PointsWithIndex() {
        tradePoint = new TradePoint();
        index = 0;
    }

    public TradePoint getTradePoint() {
        return tradePoint;
    }

    public void setTradePoint(TradePoint tradePoint) {
        this.tradePoint = tradePoint;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
