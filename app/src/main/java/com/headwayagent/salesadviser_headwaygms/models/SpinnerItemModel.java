package com.headwayagent.salesadviser_headwaygms.models;

public class SpinnerItemModel {

    private String spinnerItemName;
    private int spinnerItemImage;

    public SpinnerItemModel(String spinnerItemName, int spinnerItemImage) {
        this.spinnerItemName = spinnerItemName;
        this.spinnerItemImage = spinnerItemImage;
    }


    public String getSpinnerItemName() {
        return spinnerItemName;
    }

    public void setSpinnerItemName(String spinnerItemName) {
        this.spinnerItemName = spinnerItemName;
    }

    public int getSpinnerItemImage() {
        return spinnerItemImage;
    }

    public void setSpinnerItemImage(int spinnerItemImage) {
        this.spinnerItemImage = spinnerItemImage;
    }
}
