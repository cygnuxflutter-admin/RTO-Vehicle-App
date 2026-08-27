package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.List;


public class CarBikeAccessoriesResponse implements Serializable {
    private List<CarBikeAccessories> data;
    private int statusCode;
    private String statusMessage;

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public List<CarBikeAccessories> getData() {
        return this.data;
    }

    public String toString() {
        return "CarBikeAccessoriesResponse{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + "', data=" + this.data + '}';
    }
}
