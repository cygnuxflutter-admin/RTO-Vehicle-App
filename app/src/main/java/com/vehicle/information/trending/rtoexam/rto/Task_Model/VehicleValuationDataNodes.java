package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.List;


public class VehicleValuationDataNodes implements Serializable {
    private List<VehicleValuationData> priceValuations;
    private ResaleValueVehicleData selectedBrand;
    private ResaleValueVehicleData selectedKmDriven;
    private ResaleValueVehicleData selectedModel;
    private ResaleValueVehicleData selectedVariant;
    private ResaleValueVehicleData selectedYear;

    public ResaleValueVehicleData getSelectedBrand() {
        return this.selectedBrand;
    }

    public ResaleValueVehicleData getSelectedModel() {
        return this.selectedModel;
    }

    public ResaleValueVehicleData getSelectedYear() {
        return this.selectedYear;
    }

    public ResaleValueVehicleData getSelectedVariant() {
        return this.selectedVariant;
    }

    public ResaleValueVehicleData getSelectedKmDriven() {
        return this.selectedKmDriven;
    }

    public List<VehicleValuationData> getPriceValuations() {
        return this.priceValuations;
    }

    public String toString() {
        return "VehicleValuationDataNodes{selectedBrand=" + this.selectedBrand + ", selectedModel=" + this.selectedModel + ", selectedYear=" + this.selectedYear + ", selectedVariant=" + this.selectedVariant + ", selectedKmDriven=" + this.selectedKmDriven + ", priceValuations=" + this.priceValuations + '}';
    }
}
