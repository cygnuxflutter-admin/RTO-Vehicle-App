package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.List;


public class CarBrandsResponse implements Serializable {
    private List<CarBrand> data;
    private int statusCode;
    private String statusMessage;

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public List<CarBrand> getData() {
        return this.data;
    }

    public String toString() {
        return "CarBrandsResponse{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + "', data=" + this.data + '}';
    }
}
