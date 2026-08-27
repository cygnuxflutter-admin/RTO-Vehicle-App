package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class SearchChallanHistory implements Serializable {
    private int id;
    private String registrationNo;
    private int searchOrder;
    private String searchType;

    public SearchChallanHistory() {
    }

    public SearchChallanHistory(int i, String str, String str2, int i2) {
        this.id = i;
        this.registrationNo = str;
        this.searchType = str2;
        this.searchOrder = i2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getRegistrationNo() {
        return this.registrationNo;
    }

    public void setRegistrationNo(String str) {
        this.registrationNo = str;
    }

    public String getSearchType() {
        return this.searchType;
    }

    public void setSearchType(String str) {
        this.searchType = str;
    }

    public int getSearchOrder() {
        return this.searchOrder;
    }

    public void setSearchOrder(int i) {
        this.searchOrder = i;
    }

    public String toString() {
        return "SearchChallanHistory{id=" + this.id + ", registrationNo='" + this.registrationNo + "', searchType='" + this.searchType + "', searchOrder=" + this.searchOrder + '}';
    }
}
