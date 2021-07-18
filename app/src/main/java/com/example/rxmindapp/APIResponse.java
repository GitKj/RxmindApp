package com.example.rxmindapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;

public class APIResponse {
    Status received = new Status();
    ArrayList<Result> nlmRxImages;

    public APIResponse(){


    }




    public class Status{

        Boolean success = false;
        String date;
        int imageCount = 0;
        int totalImageCount;
        JsonArray matchedTerms;

        public Status(){

        }

    }
}


