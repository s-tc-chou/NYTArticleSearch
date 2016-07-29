package com.example.steve.nytarticlesearch.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Steve on 7/27/2016.
 */
public class Person implements Parcelable{

    @SerializedName("organization")
    @Expose
    private String organization;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("lastname")
    @Expose
    private String lastname;

    /**
     *
     * @return
     * The organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     *
     * @param organization
     * The organization
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     *
     * @return
     * The role
     */
    public String getRole() {
        return role;
    }

    /**
     *
     * @param role
     * The role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     *
     * @return
     * The firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     *
     * @param firstname
     * The firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     *
     * @return
     * The rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     *
     * @param rank
     * The rank
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     *
     * @return
     * The lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     *
     * @param lastname
     * The lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.organization);
        dest.writeString(this.role);
        dest.writeString(this.firstname);
        dest.writeValue(this.rank);
        dest.writeString(this.lastname);
    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.organization = in.readString();
        this.role = in.readString();
        this.firstname = in.readString();
        this.rank = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lastname = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
