package com.headwayagent.salesadviser_headwaygms.models;

public class DashBoardModel {


    private  String title;
    private  int    backgroundImg;
    private  String countwork;
    private int backgroundResourse;

    public DashBoardModel(String title, int backgroundImg,String countwork,int backgroundResourse) {
        this.title = title;
        this.backgroundImg = backgroundImg;
        this.countwork=countwork;
        this.backgroundResourse=backgroundResourse;
    }


    public int getBackgroundResourse() {
        return backgroundResourse;
    }

    public void setBackgroundResourse(int backgroundResourse) {
        this.backgroundResourse = backgroundResourse;
    }

    public String getCountwork() {
        return countwork;
    }

    public void setCountwork(String countwork) {
        this.countwork = countwork;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(int backgroundImg) {
        this.backgroundImg = backgroundImg;
    }
}
