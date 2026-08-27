package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class Task_VehicleInformationModel implements Serializable {
    private int brandId;
    private String brandName;
    private String exShowroomPrice;
    private int id;
    private String imageUrl;
    private String modelName;
    private String modelSlug;

    public int getId() {
        return this.id;
    }

    public int getBrandId() {
        return this.brandId;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public String getModelName() {
        return this.modelName;
    }

    public String getModelSlug() {
        return this.modelSlug;
    }

    public String getExShowroomPrice() {
        return this.exShowroomPrice;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String toString() {
        return "R_VehicleInfo{id=" + this.id + ", brandId='" + this.brandId + "', brandName='" + this.brandName + "', modelName='" + this.modelName + "', modelSlug='" + this.modelSlug + "', exShowroomPrice='" + this.exShowroomPrice + "', imageUrl='" + this.imageUrl + "'}";
    }
}
