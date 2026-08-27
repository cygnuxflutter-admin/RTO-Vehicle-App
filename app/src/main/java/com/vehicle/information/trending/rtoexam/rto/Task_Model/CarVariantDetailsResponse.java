package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class CarVariantDetailsResponse implements Serializable {
    private CarVariant details;
    private int statusCode;
    private String statusMessage;

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public CarVariant getDetails() {
        return this.details;
    }

    public String toString() {
        return "CarVariantDetailsResponse{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + "', details=" + this.details + '}';
    }
}
