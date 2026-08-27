package com.vehicle.information.trending.rtoexam.rto.Task_Model;

public class Task_CityNameModel {
    String cityName;
    String id;

    public Task_CityNameModel(String str, String str2) {
        this.cityName = str;
        this.id = str2;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String str) {
        this.cityName = str;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }
}
