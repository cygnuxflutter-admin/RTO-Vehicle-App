package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class VehicleValuationData implements Serializable {
    private String condition;
    private int maxValue;
    private String message;
    private int minValue;

    public String getCondition() {
        return this.condition;
    }

    public int getMinValue() {
        return this.minValue;
    }

    public int getMaxValue() {
        return this.maxValue;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return "VehicleValuationData{condition='" + this.condition + "', minValue=" + this.minValue + ", maxValue=" + this.maxValue + ", message='" + this.message + "'}";
    }
}
