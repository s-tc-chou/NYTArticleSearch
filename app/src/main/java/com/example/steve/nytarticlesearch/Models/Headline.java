package com.example.steve.nytarticlesearch.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;


public class Headline implements Parcelable{

    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("content_kicker")
    @Expose
    private String contentKicker;
    @SerializedName("kicker")
    @Expose
    private String kicker;
    @SerializedName("print_headline")
    @Expose
    private String printHeadline;

    /**
     * @return The main
     */
    public String getMain() {
        return main;
    }

    /**
     * @param main The main
     */
    public void setMain(String main) {
        this.main = main;
    }

    /**
     * @return The contentKicker
     */
    public String getContentKicker() {
        return contentKicker;
    }

    /**
     * @param contentKicker The content_kicker
     */
    public void setContentKicker(String contentKicker) {
        this.contentKicker = contentKicker;
    }

    /**
     * @return The kicker
     */
    public String getKicker() {
        return kicker;
    }

    /**
     * @param kicker The kicker
     */
    public void setKicker(String kicker) {
        this.kicker = kicker;
    }

    /**
     * @return The printHeadline
     */
    public String getPrintHeadline() {
        return printHeadline;
    }

    /**
     * @param printHeadline The print_headline
     */
    public void setPrintHeadline(String printHeadline) {
        this.printHeadline = printHeadline;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.main);
        dest.writeString(this.contentKicker);
        dest.writeString(this.kicker);
        dest.writeString(this.printHeadline);
    }

    public Headline() {
    }

    protected Headline(Parcel in) {
        this.main = in.readString();
        this.contentKicker = in.readString();
        this.kicker = in.readString();
        this.printHeadline = in.readString();
    }

    public static final Creator<Headline> CREATOR = new Creator<Headline>() {
        @Override
        public Headline createFromParcel(Parcel source) {
            return new Headline(source);
        }

        @Override
        public Headline[] newArray(int size) {
            return new Headline[size];
        }
    };
}