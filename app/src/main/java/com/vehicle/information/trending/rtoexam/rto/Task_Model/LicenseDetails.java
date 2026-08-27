package com.vehicle.information.trending.rtoexam.rto.Task_Model;



import com.vehicle.information.trending.rtoexam.rto.Task_utils.Utils;

import java.io.Serializable;


public class LicenseDetails implements Serializable {
    private String currentStatus;
    private String dateOfIssue;
    private String dob;
    private String gender;
    private String holderName;
    private String lastTransactionAt;
    private String licenseNo;
    private String rtoCode;
    private int searchCount;
    private String validFrom;
    private String validTo;
    private String vehicleClass;

    public LicenseDetails() {
    }

    public LicenseDetails(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, int i) {
        this.licenseNo = str;
        this.dob = str2;
        this.holderName = str3;
        this.dateOfIssue = str4;
        this.currentStatus = str5;
        this.lastTransactionAt = str6;
        this.validFrom = str7;
        this.validTo = str8;
        this.vehicleClass = str9;
        this.searchCount = i;
    }

    public boolean isEmptyResponse() {
        return Utils.isNullOrEmpty(this.holderName) || Utils.isNullOrEmpty(this.licenseNo);
    }

    public String getLicenseNo() {
        return this.licenseNo;
    }

    public void setLicenseNo(String str) {
        this.licenseNo = str;
    }

    public String getDob() {
        return this.dob;
    }

    public void setDob(String str) {
        this.dob = str;
    }

    public String getHolderName() {
        return this.holderName;
    }

    public void setHolderName(String str) {
        this.holderName = str;
    }

    public String getDateOfIssue() {
        return this.dateOfIssue;
    }

    public void setDateOfIssue(String str) {
        this.dateOfIssue = str;
    }

    public String getCurrentStatus() {
        return this.currentStatus;
    }

    public void setCurrentStatus(String str) {
        this.currentStatus = str;
    }

    public String getLastTransactionAt() {
        return this.lastTransactionAt;
    }

    public void setLastTransactionAt(String str) {
        this.lastTransactionAt = str;
    }

    public String getValidFrom() {
        return this.validFrom;
    }

    public void setValidFrom(String str) {
        this.validFrom = str;
    }

    public String getValidTo() {
        return this.validTo;
    }

    public void setValidTo(String str) {
        this.validTo = str;
    }

    public String getVehicleClass() {
        return this.vehicleClass;
    }

    public void setVehicleClass(String str) {
        this.vehicleClass = str;
    }

    public String getRtoCode() {
        return this.rtoCode;
    }

    public void setRtoCode(String str) {
        this.rtoCode = str;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String str) {
        this.gender = str;
    }

    public int getSearchCount() {
        return this.searchCount;
    }

    public void setSearchCount(int i) {
        this.searchCount = i;
    }

    public String toString() {
        return "LicenseDetails{licenseNo='" + this.licenseNo + "', dob='" + this.dob + "', holderName='" + this.holderName + "', dateOfIssue='" + this.dateOfIssue + "', currentStatus='" + this.currentStatus + "', lastTransactionAt='" + this.lastTransactionAt + "', validFrom='" + this.validFrom + "', validTo='" + this.validTo + "', vehicleClass='" + this.vehicleClass + "', rtoCode='" + this.rtoCode + "', gender='" + this.gender + "', searchCount=" + this.searchCount + '}';
    }
}
