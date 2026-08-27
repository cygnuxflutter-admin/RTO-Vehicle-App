package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class Store implements Serializable {
    private String ccEmail;
    private String ccPhoneNumber;
    private String colorCode;
    private String icon;
    private int id;
    private String name;
    private String oneLiner;
    private String saleTransactionUrl;
    private String url;

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getOneLiner() {
        return this.oneLiner;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getCcPhoneNumber() {
        return this.ccPhoneNumber;
    }

    public String getCcEmail() {
        return this.ccEmail;
    }

    public String getSaleTransactionUrl() {
        return this.saleTransactionUrl;
    }

    public String getColorCode() {
        return this.colorCode;
    }
}
