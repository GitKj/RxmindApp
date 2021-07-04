package com.example.rxmindapp;

import java.util.ArrayList;
import java.util.Date;

// This class is to track an individual users pills/medication && reminder
public class UserReminder {

    private String pillName;
    private String pillDescription;
    private String pillQuantity; // how many pills they need to take at time/day

    private ArrayList<String> pillDays;
    private String pillTime;

    public String getPillName() {
        return pillName;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public String getPillQuantity() {
        return pillQuantity;
    }

    public void setPillQuantity(String pillQuantity) {
        this.pillQuantity = pillQuantity;
    }

    public String getPillDescription() {
        return pillDescription;
    }

    public void setPillDescription(String pillDescription) {
        this.pillDescription = pillDescription;
    }

    public ArrayList<String> getPillDays() {
        return pillDays;
    }

    public void setPillDays(ArrayList<String> pillDays) {
        this.pillDays = pillDays;
    }

    public String getPillTime() {
        return pillTime;
    }

    public void setPillTime(String pillTime) {
        this.pillTime = pillTime;
    }
}
