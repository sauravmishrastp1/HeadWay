package com.headwayagent.salesadviser_headwaygms.models;

public class TotalDownlineModel {

    private String ainnumber,numberoforders;

    public TotalDownlineModel(String ainnumber, String numberoforders) {
        this.ainnumber = ainnumber;
        this.numberoforders = numberoforders;
    }


    public String getAinnumber() {
        return ainnumber;
    }

    public void setAinnumber(String ainnumber) {
        this.ainnumber = ainnumber;
    }

    public String getNumberoforders() {
        return numberoforders;
    }

    public void setNumberoforders(String numberoforders) {
        this.numberoforders = numberoforders;
    }
}
