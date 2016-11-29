package com.example.dell.app3;

import java.io.Serializable;

/**
 * Created by DELL on 2016/9/23.
 */

public class B implements Serializable {
    private static final long serialVersionUID = 1L;

    private int imgResourceID;
    private String week;
    private String date;
    private String content;
    public B(int imgResourceID,String week, String date, String content) {
        this.imgResourceID=imgResourceID;
        this.week = week;
        this.date = date;
        this.content = content;
    }

    public B(int imgResourceID) {
        this.imgResourceID = imgResourceID;
    }

    public int getImgResourceID() {return imgResourceID;}

    public String getWeek() {
        return week;
    }
    public void setWeek(String week) {this.week = week;}
    public String getDate() {
        return date;
    }
    public void setDate(String date) {this.date = date;}
    public String getContent() {
        return content;
    }
    public void setContent(String content) {this.content = content;}


}
