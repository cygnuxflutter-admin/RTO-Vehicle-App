package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class CarVariantAttribute implements Serializable {
    private String attrName;
    private String attrValue;

    public String getAttrName() {
        return this.attrName;
    }

    public String getAttrValue() {
        return this.attrValue;
    }

    public String toString() {
        return "CarVariantAttribute{attrName='" + this.attrName + "', attrValue='" + this.attrValue + "'}";
    }
}
