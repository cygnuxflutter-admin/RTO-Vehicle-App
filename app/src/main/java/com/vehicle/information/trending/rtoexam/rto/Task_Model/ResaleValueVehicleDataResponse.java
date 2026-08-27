package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.List;


public class ResaleValueVehicleDataResponse implements Serializable {
    private List<ResaleValueVehicleData> data;
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

    public List<ResaleValueVehicleData> getData() {
        return this.data;
    }

    public void setData(List<ResaleValueVehicleData> list) {
        this.data = list;
    }

    public String toString() {
        return "ResaleValueVehicleDataResponse{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + "', data=" + this.data + '}';
    }
}
