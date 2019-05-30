package com.mySampleApplication.client;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Worker implements Serializable {
    private int id;
    private HoursAndMinutes startWorkingTime;
    private HoursAndMinutes endWorkingTime;
    private String name;
    private String info;
    private TradePoint[] workPlan;
    private static int AUTO_ID = 0;
    //private HashMap<Date, TradePoint[]> points;
    private String[] namesTradePoints;
    //private String secretPointName;
    private ArrayList<TradePoint> pointsOnCurrDate;
    private int secretIndex;

    public Worker(HoursAndMinutes startWorkingTime, HoursAndMinutes endWorkingTime, String name, String info
            , HashMap<Date, TradePoint[]> points) {
        this.startWorkingTime = startWorkingTime;
        this.endWorkingTime = endWorkingTime;
        this.name = name;
        this.info = info;
        //id = AUTO_ID++;
        //this.points = points;
    }

    public Worker(){
        startWorkingTime = new HoursAndMinutes(0, 0);
        endWorkingTime = new HoursAndMinutes(23, 30);
        name = "Worker Name";
        info = "Some info";
        workPlan = new TradePoint[48];
        //id = AUTO_ID++;
        //points = new HashMap<>();
        namesTradePoints = new String[48];
        pointsOnCurrDate = new ArrayList<>();
        secretIndex = 0;
        AUTO_ID++;
        id = AUTO_ID;
    }

    public HoursAndMinutes getStartWorkingTime() {
        return startWorkingTime;
    }

    public void setStartWorkingTime(HoursAndMinutes startWorkingTime) {
        this.startWorkingTime = startWorkingTime;
    }

    public HoursAndMinutes getEndWorkingTime() {
        return endWorkingTime;
    }

    public void setEndWorkingTime(HoursAndMinutes endWorkingTime) {
        this.endWorkingTime = endWorkingTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSecretPointName(){
        if(
                //points.get(new Date(GlobalSettings.currentDate.getTime())) != null &&
                workPlan[secretIndex] != null){
            String temp = "";
            TradePoint point1 = workPlan[secretIndex];
            TradePoint point2 = null;
            TradePoint point3 = null;
            if(secretIndex - 1 >= 0)
                point2 = workPlan[secretIndex - 1];
            if(secretIndex + 1 < 49)
                point3 = workPlan[secretIndex + 1];
            if(point1.getName() == "Wait")
                temp = "Wait";
            else if(point1.getPointIdInDB() == GlobalSettings.getStartPoint().getPointIdInDB())
                temp = GlobalSettings.getStartPoint().getName() + " - Driving";
            else if(point2 != null && point3 != null &&
                    point3.getPointIdInDB() != GlobalSettings.getStartPoint().getPointIdInDB() &&
                    point1.getLatitude() == point2.getLatitude() &&
                    point1.getLongtide() == point2.getLongtide() &&
                    point1.getLatitude() == point3.getLatitude() &&
                    point1.getLongtide() == point3.getLongtide())
                temp = point1.getName() + " - Driving";
            else if(point2 != null && !(point1.getLatitude() == point2.getLatitude() &&
                    point1.getLongtide() == point2.getLongtide()))
            temp = point1.getName() + " - Driving";
            else if (point3 != null &&
                    !(point1.getLatitude() == point3.getLatitude() &&
                            point1.getLongtide() == point3.getLongtide() &&
                            point1.getPointIdInDB() != GlobalSettings.getStartPoint().getPointIdInDB()))
                temp = point1.getName() + " - Unloading";
            else if (point3 == null && point1.getPointIdInDB() != GlobalSettings.getStartPoint().getPointIdInDB())
                temp = point1.getName() + " - Unloading";
            else if (point2 == null)
                temp = point1.getName() + " - Driving";
            else if (point3 == null)
                temp = point1.getName() + " - Driving";
            secretIndex++;
            if(secretIndex > 48){
                secretIndex = 1;
            }
            return "<span style='background-color:" + point1.getColor() + "'>" + temp + "</span>";
            //return "<span style='background-color:red'>" + temp + "</span>";
        }else{
            secretIndex++;
            if(secretIndex > 48){
                secretIndex = 1;
            }
//            if(secretIndex - 1 >= 0)
//                return "<span style='background-color:" + points.get(new Date(GlobalSettings.currentDate.getTime()))[secretIndex - 1].getColor() + "'>Wait</span>";
//            else
                return "Wait";
        }
//        secretIndex++;
//        if(secretIndex > 48){
//            secretIndex = 0;
//        }
//        return "Wait point " + Integer.toString(secretIndex);
    }

    public void setSecretPointName(String secretPointName){
       // this.secretPointName = secretPointName;
    }

    public void setSecretIndex() {
        this.secretIndex = 0;
    }

//    public Map<Date, ArrayList<TradePoint>> getWorkPlan() {
//        return workPlan;
//    }
//
//    public void addPointToPlan(Date date, TradePoint point){
//        workPlan.get(date).add(point);
//    }


    public TradePoint[] getWorkPlan() {
        return workPlan;
    }

    public void setWorkPlan(TradePoint[] workPlan) {
        this.workPlan = workPlan;
    }

    public int getId() {
        return id;
    }

//    public TradePoint[] getWorkPlanByCurrentDate(){
//        if(points.get(new Date(GlobalSettings.currentDate.getTime())) == null)
//            points.put(new Date(GlobalSettings.currentDate.getTime()), new TradePoint[48]);
//        return points.get(new Date(GlobalSettings.currentDate.getTime()));
//    }

    public TradePoint[] getTradePoints(){
        //return getWorkPlanByCurrentDate();
        return workPlan;
    }

//    public String[] getNamesTradePoints(){
//////        TradePoint[] arr = getTradePoints();
//////        //String[] arrNames = new String[arr.length];
//////        for (int i = 0; i < arr.length; i++) {
//////            namesTradePoints[i] = arr[i].getName();
//////        }
////        return namesTradePoints;
////    }


    public String[] getNamesTradePoints() {
        return namesTradePoints;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNamesTradePoints(String[] namesTradePoints) {
        this.namesTradePoints = namesTradePoints;
    }

    @Override
    public String toString() {
        return name != null ? name : super.toString();
    }

//    public HashMap<Date, TradePoint[]> getPoints() {
//        return points;
//    }

//    public void setPoints(HashMap<Date, TradePoint[]> points) {
//        this.points = points;
//    }

    public ArrayList<TradePoint> getPointsOnCurrDate() {
        return pointsOnCurrDate;
    }

    public void setPointsOnCurrDate(ArrayList<TradePoint> pointsOnCurrDate) {
        this.pointsOnCurrDate = pointsOnCurrDate;
    }
}
