package com.vehicle.information.trending.rtoexam.rto.Task_Model;

public class Task_StateListModel {
    private String id;
    private String stateName;
    private String subtitle;
    private boolean isState;

    public Task_StateListModel(String name, String id) {
        this.stateName = name;
        this.id = id;
        this.subtitle = "";
        this.isState = "STATE".equalsIgnoreCase(id) || "null".equalsIgnoreCase(id);
    }

    public Task_StateListModel(String name, String id, String subtitle, boolean isState) {
        this.stateName = name;
        this.id = id;
        this.subtitle = subtitle;
        this.isState = isState;
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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public boolean isState() {
        return isState;
    }

    public void setState(boolean state) {
        isState = state;
    }
}
