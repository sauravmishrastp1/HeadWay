package com.headwayagent.salesadviser_headwaygms.models;

public class Distributer_model {
    String name;
    String pin;
    String ain;
    String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAin() {
        return ain;
    }

    public void setAin(String ain) {
        this.ain = ain;
    }

    public Distributer_model(String name, String pin, String ain, String phone) {
        this.name = name;
        this.pin = pin;
        this.ain = ain;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
