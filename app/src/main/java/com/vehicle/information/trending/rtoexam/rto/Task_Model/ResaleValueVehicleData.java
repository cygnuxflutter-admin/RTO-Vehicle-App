package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class ResaleValueVehicleData implements Serializable {
    private int brandId;
    private String brandName;
    private String kmDriven;
    private int modelId;
    private String modelName;
    private int variantId;
    private String variantName;
    private String vehicleType;
    private String year;

    public ResaleValueVehicleData(String str) {
        this.kmDriven = str;
    }

    public int getBrandId() {
        return this.brandId;
    }

    public int getModelId() {
        return this.modelId;
    }

    public int getVariantId() {
        return this.variantId;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public String getModelName() {
        return this.modelName;
    }

    public String getVariantName() {
        return this.variantName;
    }

    public String getVehicleType() {
        return this.vehicleType;
    }

    public String getYear() {
        return this.year;
    }

    public String getKmDriven() {
        return this.kmDriven;
    }

    public String toString() {
        return "ResaleValueVehicleData{brandId=" + this.brandId + ", modelId=" + this.modelId + ", variantId=" + this.variantId + ", brandName='" + this.brandName + "', modelName='" + this.modelName + "', variantName='" + this.variantName + "', vehicleType='" + this.vehicleType + "', year='" + this.year + "', kmDriven='" + this.kmDriven + "'}";
    }
}
