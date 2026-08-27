package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.List;


public class InitData implements Serializable {
        private List<TopBottomCategory> bottomCategories;
    private boolean clickToSee;
    private int clickToSeeAdIndex;
    private List<TopBottomCategory> extraCategories;
    private List<TopBottomCategory> topCategories;
    private List<TrendingPersonList> trendingPersons;
    private int vehicleDetailsAdIndex;

    public List<TopBottomCategory> getTopCategories() {
        return this.topCategories;
    }

    public List<TopBottomCategory> getBottomCategories() {
        return this.bottomCategories;
    }

    public List<TopBottomCategory> getExtraCategories() {
        return this.extraCategories;
    }

    public List<TrendingPersonList> getTrendingPersons() {
        return this.trendingPersons;
    }

    public boolean isClickToSee() {
        return this.clickToSee;
    }

    public int getClickToSeeAdIndex() {
        return this.clickToSeeAdIndex;
    }

    public int getVehicleDetailsAdIndex() {
        return this.vehicleDetailsAdIndex;
    }

    public String toString() {
        return "InitData{topCategories=" + this.topCategories + ", bottomCategories=" + this.bottomCategories + ", extraCategories=" + this.extraCategories + ", trendingPersons=" + this.trendingPersons + ", clickToSee=" + this.clickToSee + ", clickToSeeAdIndex=" + this.clickToSeeAdIndex + ", vehicleDetailsAdIndex=" + this.vehicleDetailsAdIndex + '}';
    }
}
