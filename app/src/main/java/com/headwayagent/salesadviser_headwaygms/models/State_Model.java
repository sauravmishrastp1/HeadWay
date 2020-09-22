package com.headwayagent.salesadviser_headwaygms.models;

public class State_Model {

    String statename;
    String satepin;

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getSatepin() {
        return satepin;
    }

    public void setSatepin(String satepin) {
        this.satepin = satepin;
    }

    public State_Model(String statename, String satepin) {
        this.statename = statename;
        this.satepin = satepin;
    }
}
