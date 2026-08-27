package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class BikeModelDetailsResponse implements Serializable {
    private BikeModel details;
    private int statusCode;
    private String statusMessage;

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public BikeModel getDetails() {
        return this.details;
    }

    public String toString() {
        return "BikeModelDetailsResponse{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + "', details=" + this.details + '}';
    }
}
