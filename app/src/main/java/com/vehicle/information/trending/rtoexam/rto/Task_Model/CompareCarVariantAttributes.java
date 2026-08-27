package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.List;


public class CompareCarVariantAttributes implements Serializable {
    private List<CompareCarVariantAttribute> items;
    private String key;

    public String getKey() {
        return this.key;
    }

    public List<CompareCarVariantAttribute> getItems() {
        return this.items;
    }

    public String toString() {
        return "CompareCarVariantAttributes{key='" + this.key + "', items=" + this.items + '}';
    }
}
