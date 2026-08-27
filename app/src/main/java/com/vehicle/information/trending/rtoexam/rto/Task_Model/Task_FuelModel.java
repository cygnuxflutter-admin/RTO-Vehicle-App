package com.vehicle.information.trending.rtoexam.rto.Task_Model;

public class Task_FuelModel {
    String currentPrice;
    String currently;
    String day;
    String fuelName;
    String month;
    String previousPrice;
    String scale;
    String year;

    public Task_FuelModel(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.fuelName = str;
        this.currently = str2;
        this.day = str3;
        this.month = str4;
        this.year = str5;
        this.currentPrice = str6;
        this.previousPrice = str7;
        this.scale = str8;
    }

    public String getFuelName() {
        return this.fuelName;
    }

    public void setFuelName(String str) {
        this.fuelName = str;
    }

    public String getCurrently() {
        return this.currently;
    }

    public void setCurrently(String str) {
        this.currently = str;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(String str) {
        this.day = str;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String str) {
        this.month = str;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String str) {
        this.year = str;
    }

    public String getCurrentPrice() {
        return this.currentPrice;
    }

    public void setCurrentPrice(String str) {
        this.currentPrice = str;
    }

    public String getPreviousPrice() {
        return this.previousPrice;
    }

    public void setPreviousPrice(String str) {
        this.previousPrice = str;
    }

    public String getScale() {
        return this.scale;
    }

    public void setScale(String str) {
        this.scale = str;
    }
}
