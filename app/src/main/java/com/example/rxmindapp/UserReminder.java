package com.example.rxmindapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

// This class is to track an individual users pills/medication && reminder
public class UserReminder implements Serializable {

    private String pillNickname;
    private String pillName;
    private String pillDescription;
    private String pillQuantity; // how many pills they need to take at time/day

    //private ArrayList<String> pillDays;
    private String pillTime;

    private boolean takeOnMonday = false;
    private boolean takeOnTuesday = false;
    private boolean takeOnWednesday = false;
    private boolean takeOnThursday = false;
    private boolean takeOnFriday = false;
    private boolean takeOnSat = false;
    private boolean takeOnSun = false;


    public String getPillNickname() {
        return pillNickname;
    }

    public void setPillNickname(String pillNickname) {
        this.pillNickname = pillNickname;
    }

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
    

    public String getPillTime() {
        return pillTime;
    }

    public void setPillTime(String pillTime) {
        this.pillTime = pillTime;
    }

    public boolean isTakeOnMonday() {
        return takeOnMonday;
    }

    public void setTakeOnMonday(boolean takeOnMonday) {
        this.takeOnMonday = takeOnMonday;
    }

    public boolean isTakeOnTuesday() {
        return takeOnTuesday;
    }

    public void setTakeOnTuesday(boolean takeOnTuesday) {
        this.takeOnTuesday = takeOnTuesday;
    }

    public boolean isTakeOnWednesday() {
        return takeOnWednesday;
    }

    public void setTakeOnWednesday(boolean takeOnWednesday) {
        this.takeOnWednesday = takeOnWednesday;
    }

    public boolean isTakeOnThursday() {
        return takeOnThursday;
    }

    public void setTakeOnThursday(boolean takeOnThursday) {
        this.takeOnThursday = takeOnThursday;
    }

    public boolean isTakeOnFriday() {
        return takeOnFriday;
    }

    public void setTakeOnFriday(boolean takeOnFriday) {
        this.takeOnFriday = takeOnFriday;
    }

    public boolean isTakeOnSat() {
        return takeOnSat;
    }

    public void setTakeOnSat(boolean takeOnSat) {
        this.takeOnSat = takeOnSat;
    }

    public boolean isTakeOnSun() {
        return takeOnSun;
    }

    public void setTakeOnSun(boolean takeOnSun) {
        this.takeOnSun = takeOnSun;
    }
}
