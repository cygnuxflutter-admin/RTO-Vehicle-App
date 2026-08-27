package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class CarModelDetailsResponse implements Serializable {
    private CarModel details;
    private int statusCode;
    private String statusMessage;

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public CarModel getDetails() {
        return this.details;
    }

    public String toString() {
        return "CarModelDetailsResponse{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + "', details=" + this.details + '}';
    }
}
