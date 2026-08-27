package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class CarBikeAccessories implements Serializable {
    private String brandName;
    private int id;
    private String image;
    private int mrp;
    private int offerPrice;
    private String storeName;
    private String title;
    private String url;

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public int getMrp() {
        return this.mrp;
    }

    public int getOfferPrice() {
        return this.offerPrice;
    }

    public String getUrl() {
        return this.url;
    }

    public String getImage() {
        return this.image;
    }

    public String toString() {
        return "CarBikeAccessories{id=" + this.id + ", title='" + this.title + "', storeName='" + this.storeName + "', brandName='" + this.brandName + "', mrp=" + this.mrp + ", offerPrice=" + this.offerPrice + ", url='" + this.url + "', image='" + this.image + "'}";
    }
}
