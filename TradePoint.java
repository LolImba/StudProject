package com.mySampleApplication.client;

import java.io.Serializable;
import java.sql.Date;

public class TradePoint implements Serializable {
    private double longtide;
    private double latitude;
    private String description;
    private HoursAndMinutes startDate;
    private HoursAndMinutes endDate;
    private String name;
    private HoursAndMinutes duration;
    private Date date;
    private HoursAndMinutes availableStartTime;
    private HoursAndMinutes availavleEndTime;
    private String color;
    private String address;
    private int id;
    private static int counter = 0;
    private int pointParentId;
    private int pointIdInDB;

    public TradePoint(int longtide, int latitude, String description,
                      HoursAndMinutes startDate, HoursAndMinutes endDate, String name,
                      Date date, HoursAndMinutes availableStartTime, HoursAndMinutes availavleEndTime) {
        this.longtide = longtide;
        this.latitude = latitude;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.date = date;
//        int minutes = endDate.getMinutes() - startDate.getMinutes();
//        int hours = endDate.getHours() - startDate.getHours();
//        if(minutes < 0){
//            hours--;
//            minutes += 60;
//        }
//        this.duration = new HoursAndMinutes(hours, minutes);
        this.availableStartTime = availableStartTime;
        this.availavleEndTime = availavleEndTime;
        color = "White";
        id = counter++;
}

    TradePoint(int longtide, int latitude, String description, String name, Date date,
               HoursAndMinutes availableStartTime, HoursAndMinutes availavleEndTime){
        this.longtide = longtide;
        this.latitude = latitude;
        this.description = description;
        this.name = name;
        //this.duration = duration;
        this.date = date;
        this.availableStartTime = availableStartTime;
        this.availavleEndTime = availavleEndTime;
        color = "White";
        id = counter++;
    }

    public TradePoint(){
        date = new Date(GlobalSettings.getCurrentDate().getTime());
        name = "New point";
        duration = new HoursAndMinutes(8,0);
        availableStartTime = new HoursAndMinutes(0,0);
        availavleEndTime = new HoursAndMinutes(23, 30);
        longtide = 0;
        latitude = 0;
        color = "White";
        id = counter++;
        pointIdInDB = 0;
    }

    public double getLongtide() {
        return longtide;
    }

    public void setLongtide(double longtide) {
        this.longtide = longtide;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public HoursAndMinutes getStartDate() {
        return startDate;
    }

    public void setStartDate(HoursAndMinutes startDate) {
        this.startDate = startDate;
    }

    public HoursAndMinutes getEndDate() {
        return endDate;
    }

    public void setEndDate(HoursAndMinutes endDate) {
        this.endDate = endDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HoursAndMinutes getDuration() {
        return duration;
    }

    public void setDuration(HoursAndMinutes duration) {
        this.duration = duration;
    }

    public HoursAndMinutes getAvailableStartTime() {
        return availableStartTime;
    }

    public void setAvailableStartTime(HoursAndMinutes availableStartTime) {
        this.availableStartTime = availableStartTime;
    }

    public HoursAndMinutes getAvailavleEndTime() {
        return availavleEndTime;
    }

    public void setAvailavleEndTime(HoursAndMinutes availavleEndTime) {
        this.availavleEndTime = availavleEndTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPointParentId() {
        return pointParentId;
    }

    public void setPointParentId(int pointParentId) {
        this.pointParentId = pointParentId;
    }

    public int getPointIdInDB() {
        return pointIdInDB;
    }

    public void setPointIdInDB(int pointIdInDB) {
        this.pointIdInDB = pointIdInDB;
    }

    public TradePoint clonePoint(){
        TradePoint out = new TradePoint();
        out.setAvailableStartTime(this.getAvailableStartTime());
        out.setAvailavleEndTime(this.getAvailavleEndTime());
        out.setDuration(this.getDuration());
        out.setDate(this.getDate());
        out.setDescription(this.getDescription());
        out.setName(this.getName());
        out.setLatitude(this.getLatitude());
        out.setLongtide(this.getLongtide());
        out.setEndDate(this.getEndDate());
        out.setStartDate(this.getStartDate());
        return out;
    }
}
