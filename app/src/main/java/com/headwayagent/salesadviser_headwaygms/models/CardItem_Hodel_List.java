package com.headwayagent.salesadviser_headwaygms.models;

import android.graphics.drawable.Drawable;

public class CardItem_Hodel_List {
    private String name;
    private String amount;
    private int background;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public CardItem_Hodel_List(String name, String amount, int background) {
        this.name = name;
        this.amount = amount;
        this.background = background;
    }
}
