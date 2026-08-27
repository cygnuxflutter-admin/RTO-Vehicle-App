package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class CarDealer implements Serializable {
    private String dealerAddress;
    private String dealerContactNo;
    private String dealerName;
    private boolean featured;
    private int id;

    public CarDealer() {
    }

    public CarDealer(int i, String str, String str2, String str3, boolean z) {
        this.id = i;
        this.dealerName = str;
        this.dealerAddress = str2;
        this.dealerContactNo = str3;
        this.featured = z;
    }

    public int getId() {
        return this.id;
    }

    public String getDealerName() {
        return this.dealerName;
    }

    public String getDealerAddress() {
        return this.dealerAddress;
    }

    public String getDealerContactNo() {
        return this.dealerContactNo;
    }

    public boolean isFeatured() {
        return this.featured;
    }

    public String toString() {
        return "CarDealer{id=" + this.id + ", dealerName='" + this.dealerName + "', dealerAddress='" + this.dealerAddress + "', dealerContactNo='" + this.dealerContactNo + "', featured=" + this.featured + '}';
    }
}
