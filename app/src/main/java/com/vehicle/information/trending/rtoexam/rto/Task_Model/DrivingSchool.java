package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class DrivingSchool implements Serializable {
    private String address;
    private String contactEmail;
    private String contactNo;
    private boolean featured;
    private int id;
    private String latitude;
    private String longitude;
    private String name;

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getContactNo() {
        return this.contactNo;
    }

    public String getContactEmail() {
        return this.contactEmail;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public boolean isFeatured() {
        return this.featured;
    }

    public String toString() {
        return "DrivingSchool{id=" + this.id + ", name='" + this.name + "', address='" + this.address + "', contactNo='" + this.contactNo + "', contactEmail='" + this.contactEmail + "', latitude='" + this.latitude + "', longitude='" + this.longitude + "', featured=" + this.featured + '}';
    }
}
