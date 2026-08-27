package com.vehicle.information.trending.rtoexam.rto.Task_Model;

public class Task_StateListModel {
    String id;
    String stateName;

    public Task_StateListModel(String str, String str2) {
        this.stateName = str;
        this.id = str2;
    }

    public String getStateName() {
        return this.stateName;
    }

    public void setStateName(String str) {
        this.stateName = str;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }
}
