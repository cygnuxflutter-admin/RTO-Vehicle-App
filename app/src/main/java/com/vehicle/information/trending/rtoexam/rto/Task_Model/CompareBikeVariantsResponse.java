package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class CompareBikeVariantsResponse implements Serializable {
    private BikeVariantsComparison data;
    private int statusCode;
    private String statusMessage;

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public BikeVariantsComparison getData() {
        return this.data;
    }

    public String toString() {
        return "CompareBikeVariantsResponse{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + "', data=" + this.data + '}';
    }
}
