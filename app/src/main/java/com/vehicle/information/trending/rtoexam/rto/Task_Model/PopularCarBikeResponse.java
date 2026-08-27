package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class PopularCarBikeResponse implements Serializable {
    private PopularCarBike data;
    private int statusCode;
    private String statusMessage;

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public PopularCarBike getData() {
        return this.data;
    }

    public String toString() {
        return "PopularCarBikeResponse{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + "', data=" + this.data + '}';
    }
}
