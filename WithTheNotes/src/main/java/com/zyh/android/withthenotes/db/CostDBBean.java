package com.zyh.android.withthenotes.db;

import java.io.Serializable;

/**
 * Created by zhou on 2016/1/15.
 */
public class CostDBBean implements Serializable{

    private String eventName,eventDesc,eventCost,eventDate,year,month,day;
    private int id;

    public CostDBBean() {
    }

    public CostDBBean(String eventName, String eventDesc, String eventCost, String eventDate, String year, String month, String day, int id) {
        this.eventName = eventName;
        this.eventDesc = eventDesc;
        this.eventCost = eventCost;
        this.eventDate = eventDate;
        this.year = year;
        this.month = month;
        this.day = day;
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventCost() {
        return eventCost;
    }

    public void setEventCost(String eventCost) {
        this.eventCost = eventCost;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
