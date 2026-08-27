package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


public class CarModel implements Serializable {
    private int brandId;
    private String brandName;
    private String carModelName;
    private String carModelSlug;
    private List<CarColor> colors;
    private String exShowroomPrice;
    private int id;
    private String imageUrl;
    private String[] images;
    private boolean isBS6Compliant;
    private boolean isDiscontinued;
    private List<CarVariant> variants;

    public CarModel() {
    }

    public CarModel(int i) {
        this.id = i;
    }

    public CarModel(int i, int i2, String str, String str2, String str3, String str4, String str5) {
        this.id = i;
        this.brandId = i2;
        this.brandName = str;
        this.carModelName = str2;
        this.carModelSlug = str3;
        this.exShowroomPrice = str4;
        this.imageUrl = str5;
    }

    public CarBrand getCarBrand() {
        return new CarBrand(this.brandId, this.brandName, "", "");
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

    public String getCarModelName() {
        return this.carModelName;
    }

    public String getCarModelSlug() {
        return this.carModelSlug;
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

    public List<CarColor> getColors() {
        return this.colors;
    }

    public List<CarVariant> getVariants() {
        return this.variants;
    }

    public boolean isDiscontinued() {
        return this.isDiscontinued;
    }

    public boolean isBS6Compliant() {
        return this.isBS6Compliant;
    }

    public String toString() {
        return "CarModel{id=" + this.id + ", brandId=" + this.brandId + ", brandName='" + this.brandName + "', carModelName='" + this.carModelName + "', carModelSlug='" + this.carModelSlug + "', exShowroomPrice='" + this.exShowroomPrice + "', imageUrl='" + this.imageUrl + "', images=" + Arrays.toString(this.images) + ", colors=" + this.colors + ", variants=" + this.variants + ", isDiscontinued=" + this.isDiscontinued + ", isBS6Compliant=" + this.isBS6Compliant + '}';
    }
}
