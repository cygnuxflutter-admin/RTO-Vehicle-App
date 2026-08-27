package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class RTODetail implements Serializable {
    private String address;
    private String district;
    private int id;
    private String phone;
    @SerializedName("rto_code")
    private String rtoCode;
    private String state;
    private String website;

    public RTODetail(int i, String str, String str2, String str3, String str4, String str5, String str6) {
        this.id = i;
        this.rtoCode = str;
        this.district = str2;
        this.state = str3;
        this.address = str4;
        this.phone = str5;
        this.website = str6;
    }

    public int getId() {
        return this.id;
    }

    public String getRtoCode() {
        return this.rtoCode;
    }

    public String getDistrict() {
        return this.district;
    }

    public String getState() {
        return this.state;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getWebsite() {
        return this.website;
    }

    public String toString() {
        return "RTODetail{id=" + this.id + ", rtoCode='" + this.rtoCode + "', district='" + this.district + "', state='" + this.state + "', address='" + this.address + "', phone='" + this.phone + "', website='" + this.website + "'}";
    }
}
