package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.List;


public class CompareCarVariantAttribute implements Serializable {
    private String attrName;
    private List<String> attrValues;

    public String getAttrName() {
        return this.attrName;
    }

    public List<String> getAttrValues() {
        return this.attrValues;
    }

    public String toString() {
        return "CompareCarVariantAttribute{attrName='" + this.attrName + "', attrValues='" + this.attrValues + "'}";
    }
}
