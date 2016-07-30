package com.example.steve.nytarticlesearch.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;


public class editOptions{

    private String filter;
    private String sortOrder;//newest or oldest
    private Boolean useBeginDate;
    private int year;
    private int month;
    private int day;

    public editOptions() {
        filter = "";
        sortOrder = "newest";

        //set calendar date to today
        Calendar date = Calendar.getInstance();
        year = date.get(Calendar.YEAR);
        month = date.get(Calendar.MONTH);
        day = date.get(Calendar.DAY_OF_MONTH);
        useBeginDate = false;

    }

    public editOptions(String filter, String sortOrder, int year, int month, int day, Boolean useBeginDate)
    {
        this.filter = filter;
        this.sortOrder = sortOrder;
        this.year = year;
        this.month = month;
        this.day = day;
        this.useBeginDate = useBeginDate;

    }

    //generated getter
    public String getFilter() {
        return filter;
    }

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

    public Boolean getUseBeginDate() {
        return useBeginDate;
    }

    //generated setter
    public void setFilter(String filter) {
        this.filter = filter;
    }

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
}
