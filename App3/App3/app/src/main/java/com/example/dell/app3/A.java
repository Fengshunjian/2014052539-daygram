package com.example.dell.app3;

import java.io.Serializable;

/**
 * Created by DELL on 2016/9/23.
 */

public class A implements Serializable {
    private static final long serialVersionUID=1L;
    private String week;
    private String date;
    private String content;

    public A(String week, String date, String content) {
        this.week = week;
        this.date = date;
        this.content = content;
    }

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
