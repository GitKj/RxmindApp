package com.example.rxmindapp;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//Necessary class for API parsing, however does not directly provide functionality.
@Generated("jsonschema2pojo")
public class RelabelersNdc9 {

    @SerializedName("@sourceNdc9")
    @Expose
    private String sourceNdc9;
    @SerializedName("ndc9")
    @Expose
    private List<String> ndc9 = null;

    public String getSourceNdc9() {
        return sourceNdc9;
    }

    public void setSourceNdc9(String sourceNdc9) {
        this.sourceNdc9 = sourceNdc9;
    }

    public List<String> getNdc9() {
        return ndc9;
    }

    public void setNdc9(List<String> ndc9) {
        this.ndc9 = ndc9;
    }

}