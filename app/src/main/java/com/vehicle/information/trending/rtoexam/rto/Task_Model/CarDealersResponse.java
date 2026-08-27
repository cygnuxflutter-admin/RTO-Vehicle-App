package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.List;


public class CarDealersResponse implements Serializable {
    private List<CarDealer> data;
    private int statusCode;
    private String statusMessage;

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public List<CarDealer> getData() {
        return this.data;
    }

    public String toString() {
        return "CarDealersResponse{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + "', data=" + this.data + '}';
    }
}
