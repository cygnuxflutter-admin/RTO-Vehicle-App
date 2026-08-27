package com.vehicle.information.trending.rtoexam.rto.Task_Model;


public class Task_SignModel {
    int image;
    String text;

    public int getImage() {
        return this.image;
    }

    public Task_SignModel(String str, int i) {
        this.text = str;
        this.image = i;
    }

    public String getText() {
        return this.text;
    }
}
