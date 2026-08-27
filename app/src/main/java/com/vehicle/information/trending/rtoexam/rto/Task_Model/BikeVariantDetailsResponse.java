package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class BikeVariantDetailsResponse implements Serializable {
    private BikeVariant details;
    private int statusCode;
    private String statusMessage;

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public BikeVariant getDetails() {
        return this.details;
    }

    public String toString() {
        return "BikeVariantDetailsResponse{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + "', details=" + this.details + '}';
    }
}
