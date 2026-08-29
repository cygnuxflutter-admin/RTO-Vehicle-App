package com.vehicle.information.trending.rtoexam.rto.Task_Model;

public class Task_FormModel {
    private String formNumber;
    private String title;
    private String description;
    private String category;
    private String feeInfo;
    private int iconResId;

    public Task_FormModel(String formNumber, String title, String description, String category, String feeInfo, int iconResId) {
        this.formNumber = formNumber;
        this.title = title;
        this.description = description;
        this.category = category;
        this.feeInfo = feeInfo;
        this.iconResId = iconResId;
    }

    public String getFormNumber() {
        return formNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getFeeInfo() {
        return feeInfo;
    }

    public int getIconResId() {
        return iconResId;
    }
}