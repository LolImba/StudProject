package com.mySampleApplication.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mySampleApplication.client.GXTClient.WorkerWithBool;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public interface AppServiceAsync {

    void testMethod(AsyncCallback<String> async);

    void getInfo(Worker[] workers, TradePoint[] tradePoints, TradePoint startPoint, int speed, AsyncCallback<WorkersAndPointsStruct> async) throws IOException;

    void getPoints(AsyncCallback<ArrayList<TradePoint>> async);

    void getRoutePointOnCurDate(Date curDate, AsyncCallback<ArrayList<TradePoint>> async);

    void addStartPoint(TradePoint startPoint, AsyncCallback<Void> async);

    void getStartPoint(AsyncCallback<TradePoint> async);

    void sendPoints(TradePoint startPoint, TradePoint selectedPoint, AsyncCallback<Void> async);


    void sendRoutePoint(TradePoint sendedPoint, java.sql.Date curDate, AsyncCallback<Void> async);

    void updateRoutePoint(TradePoint sendedPoint, Date curDate, AsyncCallback<Void> async);

//    void optimize(Worker[] workers, TradePoint[] tradePoints, TradePoint startPoint, int speed, Date curDate, AsyncCallback<WorkersAndPointsStruct> async);

   // void optimize(WorkerWithBool[] workers, TradePoint[] tradePoints, TradePoint startPoint, int speed, Date curDate, AsyncCallback<WorkersAndPointsStruct> async);

    void getWorkersAndRoutePoints(Date curDate, AsyncCallback<WorkersAndPointsStruct> async);

    void sendWorker(Worker worker, AsyncCallback<Void> async);

    void getWorkers(AsyncCallback<ArrayList<Worker>> async);

    void checkUser(String login, String password, AsyncCallback<Boolean> async);

    void sendPointFromUnplannedPoints(TradePoint selectedPoint, AsyncCallback<Void> async);

    void optimize(WorkerWithBool[] workers, TradePoint[] tradePoints, TradePoint startPoint, int speed, Date curDate, AsyncCallback<WorkersAndPointsStruct> async);
}
