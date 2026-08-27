package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class ChallanDetails implements Serializable {
    private int amount;
    private String challanDate;
    private String challanNo;
    private String challanStatus;
    private String paymentDate;

    public String getChallanNo() {
        return this.challanNo;
    }

    public String getChallanDate() {
        return this.challanDate;
    }

    public String getChallanStatus() {
        return this.challanStatus;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getPaymentDate() {
        return this.paymentDate;
    }

    public String toString() {
        return "ChallanDetails{challanNo='" + this.challanNo + "', challanDate='" + this.challanDate + "', challanStatus='" + this.challanStatus + "', amount=" + this.amount + ", paymentDate='" + this.paymentDate + "'}";
    }
}
