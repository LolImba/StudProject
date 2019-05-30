package com.mySampleApplication.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mySampleApplication.client.GXTClient.WorkerWithBool;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

@RemoteServiceRelativePath("MySampleApplicationService")
public interface AppService extends RemoteService {
    WorkersAndPointsStruct getInfo(Worker[] workers, TradePoint[] tradePoints, TradePoint startPoint, int speed) throws IOException;
    String testMethod();
    ArrayList<TradePoint> getPoints() throws Exception;
    ArrayList<TradePoint> getRoutePointOnCurDate(Date curDate);
    void addStartPoint(TradePoint startPoint);
    TradePoint getStartPoint();
    void sendPoints(TradePoint startPoint, TradePoint selectedPoint);
    WorkersAndPointsStruct getWorkersAndRoutePoints(Date curDate);
    void sendRoutePoint (TradePoint sendedPoint, java.sql.Date curDate);
    void updateRoutePoint(TradePoint sendedPoint, java.sql.Date curDate);
    //WorkersAndPointsStruct optimize(Worker[] workers, TradePoint[] tradePoints, TradePoint startPoint, int speed, Date curDate)throws IOException;
    void sendWorker(Worker worker);
    ArrayList<Worker> getWorkers();
    boolean checkUser(String login, String password);
    void sendPointFromUnplannedPoints(TradePoint selectedPoint);
    WorkersAndPointsStruct optimize(WorkerWithBool[] workers, TradePoint[] tradePoints, TradePoint startPoint, int speed, Date curDate) throws IOException;
}
