package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.List;


public class CarVariantAttributes implements Serializable {
    private List<CarVariantAttribute> items;
    private String key;

    public String getKey() {
        return this.key;
    }

    public List<CarVariantAttribute> getItems() {
        return this.items;
    }

    public String toString() {
        return "CarVariantAttributes{key='" + this.key + "', items=" + this.items + '}';
    }
}
