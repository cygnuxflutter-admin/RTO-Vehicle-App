package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class TrendingPerson implements Serializable {
    private String personName;
    private String registrationNo;

    public TrendingPerson() {
    }

    public TrendingPerson(String str, String str2) {
        this.personName = str;
        this.registrationNo = str2;
    }

    public String getPersonName() {
        return this.personName;
    }

    public String getRegistrationNo() {
        return this.registrationNo;
    }

    public String toString() {
        return "TrendingPerson{personName='" + this.personName + "', registrationNo='" + this.registrationNo + "'}";
    }
}
