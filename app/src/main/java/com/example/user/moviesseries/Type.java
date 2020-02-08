package com.example.user.moviesseries;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;

public class Type {
    private String title="title";
    private String gener="gener";
    private String rate="rate";
    private String year="year";
    private String time="time";
    private String cuntry="cuntry";
    private String language="language";
    private String type="Type";
    private String award="award";
    private String box="box";
    private String image="image";
    public Bitmap Background=null;

    public Type(String title, String gener, String rate, String year, String time, String cuntry, String language, String type, String award, String box, String image) {
        this.setTitle(title);
        this.setGener(gener);
        this.setRate(rate);
        this.setYear(year);
        this.setTime(time);
        this.setCuntry(cuntry);
        this.setLanguage(language);
        this.setType(type);
        this.setAward(award);
        this.setBox(box);
        this.setImage(image);

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGener() {
        return gener;
    }

    public void setGener(String gener) {
        this.gener = gener;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCuntry() {
        return cuntry;
    }

    public void setCuntry(String cuntry) {
        this.cuntry = cuntry;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
