package com.example.rxmindapp;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class NlmRxImage {

    @SerializedName("ndc11")
    @Expose
    private String ndc11;
    @SerializedName("part")
    @Expose
    private Integer part;
    @SerializedName("relabelersNdc9")
    @Expose
    private List<RelabelersNdc9> relabelersNdc9 = null;
    @SerializedName("rxcui")
    @Expose
    private Integer rxcui;
    @SerializedName("splSetId")
    @Expose
    private String splSetId;
    @SerializedName("splRootId")
    @Expose
    private String splRootId;
    @SerializedName("splVersion")
    @Expose
    private Integer splVersion;
    @SerializedName("acqDate")
    @Expose
    private String acqDate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("labeler")
    @Expose
    private String labeler;
    @SerializedName("deaSchedule")
    @Expose
    private String deaSchedule;
    @SerializedName("attribution")
    @Expose
    private String attribution;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("imageSize")
    @Expose
    private Integer imageSize;

    public String getNdc11() {
        return ndc11;
    }

    public void setNdc11(String ndc11) {
        this.ndc11 = ndc11;
    }

    public Integer getPart() {
        return part;
    }

    public void setPart(Integer part) {
        this.part = part;
    }

    public List<RelabelersNdc9> getRelabelersNdc9() {
        return relabelersNdc9;
    }

    public void setRelabelersNdc9(List<RelabelersNdc9> relabelersNdc9) {
        this.relabelersNdc9 = relabelersNdc9;
    }

    public Integer getRxcui() {
        return rxcui;
    }

    public void setRxcui(Integer rxcui) {
        this.rxcui = rxcui;
    }

    public String getSplSetId() {
        return splSetId;
    }

    public void setSplSetId(String splSetId) {
        this.splSetId = splSetId;
    }

    public String getSplRootId() {
        return splRootId;
    }

    public void setSplRootId(String splRootId) {
        this.splRootId = splRootId;
    }

    public Integer getSplVersion() {
        return splVersion;
    }

    public void setSplVersion(Integer splVersion) {
        this.splVersion = splVersion;
    }

    public String getAcqDate() {
        return acqDate;
    }

    public void setAcqDate(String acqDate) {
        this.acqDate = acqDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabeler() {
        return labeler;
    }

    public void setLabeler(String labeler) {
        this.labeler = labeler;
    }

    public String getDeaSchedule() {
        return deaSchedule;
    }

    public void setDeaSchedule(String deaSchedule) {
        this.deaSchedule = deaSchedule;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getImageSize() {
        return imageSize;
    }

    public void setImageSize(Integer imageSize) {
        this.imageSize = imageSize;
    }

}