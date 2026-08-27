package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class BikeVariantsComparison implements Serializable {
    private List<CompareBikeVariantAttributes> features;
    private List<CompareBikeVariantAttributes> overview;
    private List<CompareBikeVariantAttributes> specifications;
    @SerializedName("variant_x")
    private BikeVariant variantX;
    @SerializedName("variant_y")
    private BikeVariant variantY;

    public BikeVariant getVariantX() {
        return this.variantX;
    }

    public BikeVariant getVariantY() {
        return this.variantY;
    }

    public List<CompareBikeVariantAttributes> getOverview() {
        return this.overview;
    }

    public List<CompareBikeVariantAttributes> getSpecifications() {
        return this.specifications;
    }

    public List<CompareBikeVariantAttributes> getFeatures() {
        return this.features;
    }

    public String toString() {
        return "BikeVariantsComparison{variantX=" + this.variantX + ", variantY=" + this.variantY + ", overview=" + this.overview + ", specifications=" + this.specifications + ", features=" + this.features + '}';
    }
}
