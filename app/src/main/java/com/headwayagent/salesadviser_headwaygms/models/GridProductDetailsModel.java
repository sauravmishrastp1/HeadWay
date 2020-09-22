package com.headwayagent.salesadviser_headwaygms.models;

public class GridProductDetailsModel {

   private String imgid;
   private String  productTitle;
   private  String productDiscription;
   private String  productPrice;
   private String  image;
   private String  model_number;


    public GridProductDetailsModel(String imgid, String productTitle, String productDiscription, String productPrice, String image, String model_number) {
        this.imgid = imgid;
        this.productTitle = productTitle;
        this.productDiscription = productDiscription;
        this.productPrice = productPrice;
        this.image = image;
        this.model_number = model_number;
    }


    public String getImgid() {
        return imgid;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDiscription() {
        return productDiscription;
    }

    public void setProductDiscription(String productDiscription) {
        this.productDiscription = productDiscription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getModel_number() {
        return model_number;
    }

    public void setModel_number(String model_number) {
        this.model_number = model_number;
    }
}
