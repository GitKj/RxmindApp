package com.example.rxmindapp;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class APIResponse {

    @SerializedName("replyStatus")
    @Expose
    private ReplyStatus replyStatus;
    @SerializedName("nlmRxImages")
    @Expose
    private List<NlmRxImage> nlmRxImages = null;

    public ReplyStatus getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(ReplyStatus replyStatus) {
        this.replyStatus = replyStatus;
    }

    public List<NlmRxImage> getNlmRxImages() {
        return nlmRxImages;
    }

    public void setNlmRxImages(List<NlmRxImage> nlmRxImages) {
        this.nlmRxImages = nlmRxImages;
    }

}

