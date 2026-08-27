package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.List;


public class RTOInformationResponse implements Serializable {
    private List<RTOInformation> details;
    private int statusCode;
    private String statusMessage;

    public RTOInformationResponse(int i, String str, List<RTOInformation> list) {
        this.statusCode = i;
        this.statusMessage = str;
        this.details = list;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public List<RTOInformation> getDetails() {
        return this.details;
    }

    public String toString() {
        return "RTOInformationResponse{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + "', details=" + this.details + '}';
    }
}
