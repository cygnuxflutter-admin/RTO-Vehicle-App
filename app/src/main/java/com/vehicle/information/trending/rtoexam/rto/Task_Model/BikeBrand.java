package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class BikeBrand implements Serializable {
    private String brandName;
    private String brandSlug;
    private int id;
    private String imageUrl;

    public BikeBrand() {
    }

    public BikeBrand(int i, String str, String str2, String str3) {
        this.id = i;
        this.brandName = str;
        this.brandSlug = str2;
        this.imageUrl = str3;
    }

    public int getId() {
        return this.id;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public String getBrandSlug() {
        return this.brandSlug;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String toString() {
        return "BikeBrand{id=" + this.id + ", brandName='" + this.brandName + "', brandSlug='" + this.brandSlug + "', imageUrl='" + this.imageUrl + "'}";
    }
}
