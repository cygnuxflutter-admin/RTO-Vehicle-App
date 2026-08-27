package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class TopBottomCategory implements Serializable {
    private String categoryDescription;
    private String categoryName;
    private String categoryTag;
    private String icon;
    private String link;

    public String getCategoryTag() {
        return this.categoryTag;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public String getCategoryDescription() {
        return this.categoryDescription;
    }

    public String getLink() {
        return this.link;
    }

    public String getIcon() {
        return this.icon;
    }

    public String toString() {
        return "TopBottomCategory{categoryTag='" + this.categoryTag + "', categoryName='" + this.categoryName + "', categoryDescription='" + this.categoryDescription + "', link='" + this.link + "', icon='" + this.icon + "'}";
    }
}
