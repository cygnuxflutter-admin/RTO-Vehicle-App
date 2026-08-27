package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class RTOQuestion implements Serializable {
    private String answer;
    private int id;
    private String imageUrl;
    private String question;

    public int getId() {
        return this.id;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String toString() {
        return "RTOQuestion{id=" + this.id + ", question='" + this.question + "', answer='" + this.answer + "', imageUrl='" + this.imageUrl + "'}";
    }
}
