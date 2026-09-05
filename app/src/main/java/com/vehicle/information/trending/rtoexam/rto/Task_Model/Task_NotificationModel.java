package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;

public class Task_NotificationModel implements Serializable {
    private String id;
    private String title;
    private String message;
    private String date;
    private String type; // "PUC", "INSURANCE", "EXAM", "UPDATE", "GENERAL"
    private boolean isRead;
    private String actionData;

    public Task_NotificationModel() {
    }

    public Task_NotificationModel(String id, String title, String message, String date, String type, boolean isRead) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.date = date;
        this.type = type;
        this.isRead = isRead;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getActionData() {
        return actionData;
    }

    public void setActionData(String actionData) {
        this.actionData = actionData;
    }
}
