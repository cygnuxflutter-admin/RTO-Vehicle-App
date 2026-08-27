package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.List;


public class RTOInformation implements Serializable {
    private String name;
    private List<RTODetail> rtoList;

    public RTOInformation(String str, List<RTODetail> list) {
        this.name = str;
        this.rtoList = list;
    }

    public String getName() {
        return this.name;
    }

    public List<RTODetail> getRtoList() {
        return this.rtoList;
    }

    public String toString() {
        return "RTOInformation{name='" + this.name + "', rtoList=" + this.rtoList + '}';
    }
}
