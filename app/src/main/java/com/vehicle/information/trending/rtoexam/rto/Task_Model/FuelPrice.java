package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class FuelPrice implements Serializable {
    private String city;
    private String diesel;
    @SerializedName("diesel_price_diff")
    private String dieselPriceDiff;
    private String petrol;
    @SerializedName("petrol_price_diff")
    private String petrolPriceDiff;
    private String state;

    public String getPetrol() {
        return this.petrol;
    }

    public String getDiesel() {
        return this.diesel;
    }

    public String getPetrolPriceDiff() {
        return this.petrolPriceDiff;
    }

    public String getDieselPriceDiff() {
        return this.dieselPriceDiff;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String toString() {
        return "FuelPrice{petrol='" + this.petrol + "', diesel='" + this.diesel + "', petrolPriceDiff='" + this.petrolPriceDiff + "', dieselPriceDiff='" + this.dieselPriceDiff + "', city='" + this.city + "', state='" + this.state + "'}";
    }
}
