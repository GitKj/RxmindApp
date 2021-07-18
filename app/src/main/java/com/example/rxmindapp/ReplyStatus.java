package com.example.rxmindapp;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//Necessary class for API parsing, however does not directly provide functionality.
@Generated("jsonschema2pojo")
public class ReplyStatus {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("imageCount")
    @Expose
    private Integer imageCount;
    @SerializedName("totalImageCount")
    @Expose
    private Integer totalImageCount;
    @SerializedName("matchedTerms")
    @Expose
    private MatchedTerms matchedTerms;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getImageCount() {
        return imageCount;
    }

    public void setImageCount(Integer imageCount) {
        this.imageCount = imageCount;
    }

    public Integer getTotalImageCount() {
        return totalImageCount;
    }

    public void setTotalImageCount(Integer totalImageCount) {
        this.totalImageCount = totalImageCount;
    }

    public MatchedTerms getMatchedTerms() {
        return matchedTerms;
    }

    public void setMatchedTerms(MatchedTerms matchedTerms) {
        this.matchedTerms = matchedTerms;
    }

}