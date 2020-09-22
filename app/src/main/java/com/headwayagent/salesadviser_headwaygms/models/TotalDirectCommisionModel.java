package com.headwayagent.salesadviser_headwaygms.models;

public class TotalDirectCommisionModel {

    private String date,orderamt,orderid,commission;


    public TotalDirectCommisionModel(String date, String orderamt, String orderid, String commission) {
        this.date = date;
        this.orderamt = orderamt;
        this.orderid = orderid;
        this.commission = commission;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderamt() {
        return orderamt;
    }

    public void setOrderamt(String orderamt) {
        this.orderamt = orderamt;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }
}

