package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.List;


public class BikeVariant implements Serializable {
    private String engineDisplacement;
    private List<BikeVariantAttributes> features;
    private int id;
    private String kerbWeight;
    private String maxPower;
    private String mileage;
    private List<BikeVariantAttributes> overview;
    private List<BikeVariantAttributes> specifications;
    private String variantName;
    private String variantPrice;
    private String variantSlug;
    private String variantSpecs;

    public int getId() {
        return this.id;
    }

    public String getVariantName() {
        return this.variantName;
    }

    public String getVariantSlug() {
        return this.variantSlug;
    }

    public String getVariantSpecs() {
        return this.variantSpecs;
    }

    public String getMaxPower() {
        return this.maxPower;
    }

    public String getKerbWeight() {
        return this.kerbWeight;
    }

    public String getEngineDisplacement() {
        return this.engineDisplacement;
    }

    public String getMileage() {
        return this.mileage;
    }

    public String getVariantPrice() {
        return this.variantPrice;
    }

    public List<BikeVariantAttributes> getOverview() {
        return this.overview;
    }

    public List<BikeVariantAttributes> getSpecifications() {
        return this.specifications;
    }

    public List<BikeVariantAttributes> getFeatures() {
        return this.features;
    }

    public String toString() {
        return "BikeVariant{id=" + this.id + ", variantName='" + this.variantName + "', variantSlug='" + this.variantSlug + "', variantSpecs='" + this.variantSpecs + "', engineDisplacement='" + this.engineDisplacement + "', mileage='" + this.mileage + "', maxPower='" + this.maxPower + "', kerbWeight='" + this.kerbWeight + "', variantPrice='" + this.variantPrice + "', overview=" + this.overview + ", specifications=" + this.specifications + ", features=" + this.features + '}';
    }
}
