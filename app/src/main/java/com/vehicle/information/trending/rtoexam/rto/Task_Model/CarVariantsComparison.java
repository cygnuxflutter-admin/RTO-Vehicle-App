package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class CarVariantsComparison implements Serializable {
    private List<CompareCarVariantAttributes> features;
    private List<CompareCarVariantAttributes> overview;
    private List<CompareCarVariantAttributes> specifications;
    @SerializedName("variant_x")
    private CarVariant variantX;
    @SerializedName("variant_y")
    private CarVariant variantY;

    public CarVariant getVariantX() {
        return this.variantX;
    }

    public CarVariant getVariantY() {
        return this.variantY;
    }

    public List<CompareCarVariantAttributes> getOverview() {
        return this.overview;
    }

    public List<CompareCarVariantAttributes> getSpecifications() {
        return this.specifications;
    }

    public List<CompareCarVariantAttributes> getFeatures() {
        return this.features;
    }

    public String toString() {
        return "CarVariantsComparison{variantX=" + this.variantX + ", variantY=" + this.variantY + ", overview=" + this.overview + ", specifications=" + this.specifications + ", features=" + this.features + '}';
    }
}
