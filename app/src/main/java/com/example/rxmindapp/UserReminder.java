package com.example.rxmindapp;

import java.util.Date;

// This class is to track an individual users pills/medication && reminder
public class UserReminder {

    private String pillName;
    private String pillColor;
    private String pillShape;
    private String pillQuantity; // how many pills they need to take at time/day


    private Date pillReminder;

    public String getPillName() {
        return pillName;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public String getPillColor() {
        return pillColor;
    }

    public void setPillColor(String pillColor) {
        this.pillColor = pillColor;
    }

    public String getPillShape() {
        return pillShape;
    }

    public void setPillShape(String pillShape) {
        this.pillShape = pillShape;
    }

    public String getPillQuantity() {
        return pillQuantity;
    }

    public void setPillQuantity(String pillQuantity) {
        this.pillQuantity = pillQuantity;
    }

    public Date getPillReminder() {
        return pillReminder;
    }

    public void setPillReminder(Date pillReminder) {
        this.pillReminder = pillReminder;
    }
}
