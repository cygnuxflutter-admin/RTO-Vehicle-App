package com.vehicle.information.trending.rtoexam.rto.Task_Model;

public class Task_RuleModel {
    private String id;
    private String title;
    private String description;
    private String category;
    private String formTag;
    private int iconResId;
    private String htmlAsset;
    private String badgeBgColorHex;
    private String badgeTextColorHex;

    public Task_RuleModel(String id, String title, String description, String category, String formTag, int iconResId, String htmlAsset, String badgeBgColorHex, String badgeTextColorHex) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.formTag = formTag;
        this.iconResId = iconResId;
        this.htmlAsset = htmlAsset;
        this.badgeBgColorHex = badgeBgColorHex;
        this.badgeTextColorHex = badgeTextColorHex;
    }

    public String getId() {
        return id;
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

    public String getFormTag() {
        return formTag;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getHtmlAsset() {
        return htmlAsset;
    }

    public String getBadgeBgColorHex() {
        return badgeBgColorHex;
    }

    public String getBadgeTextColorHex() {
        return badgeTextColorHex;
    }
}