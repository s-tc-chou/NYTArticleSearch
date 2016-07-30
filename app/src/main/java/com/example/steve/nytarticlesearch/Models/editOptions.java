package com.example.steve.nytarticlesearch.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;


public class editOptions{

    private String sortOrder;//newest or oldest

    //whether to apply new json query or not.
    private boolean settingsApplied;

    //date picker variables
    private boolean useBeginDate;
    private int year;
    private int month;
    private int day;

    //news desk values
    private boolean arts;
    private boolean fashionStyle;
    private boolean sports;

    public editOptions() {
        sortOrder = "newest";

        //set calendar date to today
        Calendar date = Calendar.getInstance();
        year = date.get(Calendar.YEAR);
        month = date.get(Calendar.MONTH);
        day = date.get(Calendar.DAY_OF_MONTH);
        useBeginDate = false;
        settingsApplied = false;
        arts = false;
        fashionStyle = false;
        sports = false;
    }

    public editOptions(boolean arts, boolean fashionStyle, boolean sports, String sortOrder, int year, int month, int day, boolean useBeginDate, boolean settingsApplied)
    {
        this.sortOrder = sortOrder;
        this.year = year;
        this.month = month;
        this.day = day;
        this.useBeginDate = useBeginDate;
        this.settingsApplied = settingsApplied;
        //news desk sets
        this.arts = arts;
        this.fashionStyle = fashionStyle;
        this.sports = sports;

    }

    //generated getter

    public String getSortOrder() {
        return sortOrder;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public boolean getUseBeginDate() {
        return useBeginDate;
    }

    public boolean isSettingsApplied() { return settingsApplied;}

    public boolean isArts() {
        return arts;
    }

    public boolean isFashionStyle() {
        return fashionStyle;
    }

    public boolean isSports() {
        return sports;
    }

    //generated setter

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setUseBeginDate(Boolean useBeginDate) {
        this.useBeginDate = useBeginDate;
    }

    public void setSettingsApplied(boolean settingsApplied) { this.settingsApplied = settingsApplied;}


    public void setArts(boolean arts) {
        this.arts = arts;
    }
}

