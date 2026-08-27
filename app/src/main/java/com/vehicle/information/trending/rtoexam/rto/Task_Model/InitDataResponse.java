package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class InitDataResponse implements Serializable {
    private InitData data;
    private int statusCode;
    private String statusMessage;

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public InitData getData() {
        return this.data;
    }

    public String toString() {
        return "InitDataResponse{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + "', data=" + this.data + '}';
    }
}
