package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class BikeColor implements Serializable {
    private String backgroundName;
    private String colorName;
    private int id;

    public int getId() {
        return this.id;
    }

    public String getColorName() {
        return this.colorName;
    }

    public String getBackgroundName() {
        return this.backgroundName;
    }

    public String toString() {
        return "BikeColor{id=" + this.id + ", colorName='" + this.colorName + "', backgroundName='" + this.backgroundName + "'}";
    }
}
