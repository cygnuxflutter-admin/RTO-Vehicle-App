package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class VehicleValuationDataResponse implements Serializable {
    private VehicleValuationDataNodes data;
    private int statusCode;
    private String statusMessage;

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int i) {
        this.statusCode = i;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public void setStatusMessage(String str) {
        this.statusMessage = str;
    }

    public VehicleValuationDataNodes getData() {
        return this.data;
    }

    public void setData(VehicleValuationDataNodes vehicleValuationDataNodes) {
        this.data = vehicleValuationDataNodes;
    }

    public String toString() {
        return "VehicleValuationDataResponse{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + "', data=" + this.data + '}';
    }
}
