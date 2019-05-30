package com.mySampleApplication.client;

import com.sencha.gxt.widget.core.client.grid.Grid;

import java.util.Date;

public class GlobalSettings {
    static Date currentDate = new Date(System.currentTimeMillis());
    static HoursAndMinutes startWorkTime;
    static HoursAndMinutes EndWorkTime;
    static TradePoint startPoint;
    static int longitudeStart;
    static int latitudeStart;
    static boolean manualMode = false;
    static Grid<Worker> mainGrid;
    static {
        startPoint = new TradePoint();
        startPoint.setDate(new java.sql.Date(currentDate.getTime()));
        startPoint.setName("Start");
        startPoint.setLongtide(longitudeStart);
        startPoint.setLatitude(latitudeStart);
    }

    public static String [] createHoursForTextBox() {
        String [] out = new String[48];
        String minutes;
        int hours = 0;
        for (int i = 0; i < 48; i++) {
            if (i % 2 == 0){
                minutes = "00";
            }else{
                minutes = "30";
            }
            hours = i/2;
            if(hours > 9)
                out[i] = hours + " : " + minutes;
            else
                out[i] = "0" + hours + " : " + minutes;
        }
        return out;
    }

    public static Date getCurrentDate() {
        return currentDate;
    }

    public static TradePoint getStartPoint() {
        return startPoint;
    }

    public static void setStartPoint(TradePoint startPoint) {
        GlobalSettings.startPoint = startPoint;
    }

    public static int getLongitudeStart() {
        return longitudeStart;
    }

    public static void setLongitudeStart(int longitudeStart) {
        GlobalSettings.longitudeStart = longitudeStart;
    }

    public static int getLatitudeStart() {
        return latitudeStart;
    }

    public static void setLatitudeStart(int latitudeStart) {
        GlobalSettings.latitudeStart = latitudeStart;
    }

    public static void setCurrentDate(Date currentDate) {
        GlobalSettings.currentDate = currentDate;
    }

    public static HoursAndMinutes getStartWorkTime() {
        return startWorkTime;
    }

    public static void setStartWorkTime(HoursAndMinutes startWorkTime) {
        GlobalSettings.startWorkTime = startWorkTime;
    }

    public static HoursAndMinutes getEndWorkTime() {
        return EndWorkTime;
    }

    public static void setEndWorkTime(HoursAndMinutes endWorkTime) {
        EndWorkTime = endWorkTime;
    }

    public static boolean isManualMode() {
        return manualMode;
    }

    public static void setManualMode(boolean manualMode) {
        GlobalSettings.manualMode = manualMode;
    }

    public static Grid<Worker> getMainGrid() {
        return mainGrid;
    }

    static public void setMainGrid(Grid<Worker> in) {
        mainGrid = in;
    }
}
