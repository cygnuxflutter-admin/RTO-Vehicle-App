package com.vehicle.information.trending.rtoexam.rto.Task_Model;

public class Task_CitiesModel {
    private String code;
    private String district;
    private String state;
    private String field2;
    private String field3;
    private String field4;
    private String field5;

    public void setCode(String code) {
        this.code = code;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getField2() {
        return this.field2;
    }

    public void setField2(String str) {
        this.field2 = str;
    }

    public String getField3() {
        return this.field3;
    }

    public void setField3(String str) {
        this.field3 = str;
    }

    public String getField4() {
        return this.field4;
    }

    public void setField4(String str) {
        this.field4 = str;
    }

    public String getField5() {
        return this.field5;
    }

    public void setField5(String str) {
        this.field5 = str;
    }

    public String getCode() {
        if (this.code != null && !this.code.isEmpty()) {
            return this.code;
        }
        if (this.field2 != null && this.field2.contains("-")) {
            return this.field2.split("-")[0].trim();
        }
        return this.field2 != null ? this.field2 : "";
    }

    public String getDistrict() {
        if (this.district != null && !this.district.isEmpty()) {
            return this.district;
        }
        if (this.field2 != null && this.field2.contains("-")) {
            int lastParen = this.field2.lastIndexOf('(');
            int hyphen = this.field2.indexOf('-');
            if (lastParen > hyphen) {
                return this.field2.substring(hyphen + 1, lastParen).trim();
            }
            return this.field2.substring(hyphen + 1).trim();
        }
        return this.field2 != null ? this.field2 : "";
    }

    public String getState() {
        if (this.state != null && !this.state.isEmpty()) {
            return this.state;
        }
        if (this.field2 != null && this.field2.contains("(") && this.field2.contains(")")) {
            int lastOpen = this.field2.lastIndexOf('(');
            int lastClose = this.field2.lastIndexOf(')');
            if (lastClose > lastOpen) {
                return this.field2.substring(lastOpen + 1, lastClose).trim();
            }
        }
        return "";
    }

    public String getAddress() {
        return this.field3 != null ? this.field3.trim() : "";
    }

    public String getPhone() {
        return this.field5 != null ? this.field5.trim() : "";
    }
}
