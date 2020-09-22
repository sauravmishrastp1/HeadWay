package com.headwayagent.salesadviser_headwaygms.models;

public class ServiceModel {

    private String service_name;
    private String service_price;
    private String service_image;
    private String service_id;
    private String service_details;

    public ServiceModel(String service_name, String service_price, String service_image,String service_id,String service_details) {
        this.service_name = service_name;
        this.service_price = service_price;
        this.service_image = service_image;
        this.service_id=service_id;
        this.service_details=service_details;
    }


    public String getService_details() {
        return service_details;
    }

    public void setService_details(String service_details) {
        this.service_details = service_details;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_price() {
        return service_price;
    }

    public void setService_price(String service_price) {
        this.service_price = service_price;
    }

    public String getService_image() {
        return service_image;
    }

    public void setService_image(String service_image) {
        this.service_image = service_image;
    }
}
