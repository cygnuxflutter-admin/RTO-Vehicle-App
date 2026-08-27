package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class FuelPricesResponse implements Serializable {
    private FuelPrice data;
    private int statusCode;
    private String statusMessage;

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public FuelPrice getData() {
        return this.data;
    }

    public String toString() {
        return "FuelPricesResponse{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + "', data=" + this.data + '}';
    }
}
