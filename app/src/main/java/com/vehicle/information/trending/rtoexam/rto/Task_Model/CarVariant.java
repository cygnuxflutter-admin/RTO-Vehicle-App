package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.List;


public class CarVariant implements Serializable {
    private String engineDisplacement;
    private List<CarVariantAttributes> features;
    private String fuelType;
    private int id;
    private String mileage;
    private List<CarVariantAttributes> overview;
    private List<CarVariantAttributes> specifications;
    private String transmissionType;
    private String variantName;
    private String variantPrice;
    private String variantSlug;

    public int getId() {
        return this.id;
    }

    public String getVariantName() {
        return this.variantName;
    }

    public String getVariantSlug() {
        return this.variantSlug;
    }

    public String getTransmissionType() {
        return this.transmissionType;
    }

    public String getFuelType() {
        return this.fuelType;
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

    public List<CarVariantAttributes> getOverview() {
        return this.overview;
    }

    public List<CarVariantAttributes> getSpecifications() {
        return this.specifications;
    }

    public List<CarVariantAttributes> getFeatures() {
        return this.features;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CarVariant)) {
            return false;
        }
        CarVariant carVariant = (CarVariant) obj;
        if (getId() != carVariant.getId() || !getVariantName().equals(carVariant.getVariantName())) {
            return false;
        }
        return getVariantSlug().equals(carVariant.getVariantSlug());
    }

    public int hashCode() {
        return (((getId() * 31) + getVariantName().hashCode()) * 31) + getVariantSlug().hashCode();
    }

    public String toString() {
        return "CarVariant{id=" + this.id + ", variantName='" + this.variantName + "', variantSlug='" + this.variantSlug + "', transmissionType='" + this.transmissionType + "', fuelType='" + this.fuelType + "', engineDisplacement='" + this.engineDisplacement + "', mileage='" + this.mileage + "', variantPrice='" + this.variantPrice + "', overview=" + this.overview + ", specifications=" + this.specifications + ", features=" + this.features + '}';
    }
}
