package com.headwayagent.salesadviser_headwaygms.models;

public class DsrModel {

    private String name,email,phone,address,date,pname,alternatephone;


    public DsrModel(String name, String email, String phone, String address, String date, String pname, String alternatephone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.date = date;
        this.pname = pname;
        this.alternatephone = alternatephone;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getAlternatephone() {
        return alternatephone;
    }

    public void setAlternatephone(String alternatephone) {
        this.alternatephone = alternatephone;
    }
}
