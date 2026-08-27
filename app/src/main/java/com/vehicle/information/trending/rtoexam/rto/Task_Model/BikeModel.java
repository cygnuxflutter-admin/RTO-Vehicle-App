package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


public class BikeModel implements Serializable {
    private String bikeModelName;
    private String bikeModelSlug;
    private int brandId;
    private String brandName;
    private List<BikeColor> colors;
    private String exShowroomPrice;
    private int id;
    private String imageUrl;
    private String[] images;
    private boolean isBS6Compliant;
    private boolean isDiscontinued;
    private List<BikeVariant> variants;

    public BikeModel() {
    }

    public BikeModel(int i) {
        this.id = i;
    }

    public BikeModel(int i, int i2, String str, String str2, String str3, String str4, String str5) {
        this.id = i;
        this.brandId = i2;
        this.brandName = str;
        this.bikeModelName = str2;
        this.bikeModelSlug = str3;
        this.exShowroomPrice = str4;
        this.imageUrl = str5;
    }

    public BikeBrand getBikeBrand() {
        return new BikeBrand(this.brandId, this.brandName, "", "");
    }

    public int getId() {
        return this.id;
    }

    public int getBrandId() {
        return this.brandId;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public String getBikeModelName() {
        return this.bikeModelName;
    }

    public String getBikeModelSlug() {
        return this.bikeModelSlug;
    }

    public String getExShowroomPrice() {
        return this.exShowroomPrice;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String[] getImages() {
        return this.images;
    }

    public List<BikeColor> getColors() {
        return this.colors;
    }

    public List<BikeVariant> getVariants() {
        return this.variants;
    }

    public boolean isDiscontinued() {
        return this.isDiscontinued;
    }

    public boolean isBS6Compliant() {
        return this.isBS6Compliant;
    }

    public String toString() {
        return "BikeModel{id=" + this.id + ", brandId=" + this.brandId + ", brandName='" + this.brandName + "', bikeModelName='" + this.bikeModelName + "', bikeModelSlug='" + this.bikeModelSlug + "', exShowroomPrice='" + this.exShowroomPrice + "', imageUrl='" + this.imageUrl + "', images=" + Arrays.toString(this.images) + ", colors=" + this.colors + ", variants=" + this.variants + ", isDiscontinued=" + this.isDiscontinued + ", isBS6Compliant=" + this.isBS6Compliant + '}';
    }
}
