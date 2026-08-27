package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class Task_TrendPersonModel implements Serializable {
    private String personName;
    private String registrationNo;

    public Task_TrendPersonModel() {
    }

    public Task_TrendPersonModel(String str, String str2) {
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
