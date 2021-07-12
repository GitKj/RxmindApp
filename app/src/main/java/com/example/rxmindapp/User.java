package com.example.rxmindapp;


import java.io.Serializable;
import java.util.ArrayList;

// we need a class for users, each user will have a List of their own personal reminders
// we implement serializable to allow for passing objects between activities
// we pass a user object when going from login screen to the main activity so we know who is logged in.


// UPDATE 7/11: Might delete this class lol
public class User implements Serializable {

    private ArrayList<UserReminder> userReminders;
    private String username;
    private String password;

    public User()
    {

    }
    public User(ArrayList<UserReminder> userReminders, String username, String password) {
        this.userReminders = userReminders;
        this.username = username;
        this.password = password;
    }

    public User(ArrayList<UserReminder> userReminders) {
        this.userReminders = userReminders;
    }

    public ArrayList<UserReminder> getUserReminders() {
        return userReminders;
    }

    public void setUserReminders(ArrayList<UserReminder> userReminders) {
        this.userReminders = userReminders;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
