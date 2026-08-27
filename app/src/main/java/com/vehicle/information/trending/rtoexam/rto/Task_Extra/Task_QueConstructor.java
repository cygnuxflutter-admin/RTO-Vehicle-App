package com.vehicle.information.trending.rtoexam.rto.Task_Extra;


public class Task_QueConstructor {
    private String answer;
    private int id;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String photo;
    private String question;

    public Task_QueConstructor() {
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String str) {
        this.photo = str;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String str) {
        this.question = str;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String str) {
        this.answer = str;
    }

    public String getOption1() {
        return this.option1;
    }

    public void setOption1(String str) {
        this.option1 = str;
    }

    public String getOption2() {
        return this.option2;
    }

    public void setOption2(String str) {
        this.option2 = str;
    }

    public String getOption3() {
        return this.option3;
    }

    public void setOption3(String str) {
        this.option3 = str;
    }

    public String getOption4() {
        return this.option4;
    }

    public void setOption4(String str) {
        this.option4 = str;
    }

    public Task_QueConstructor(int i, String question, String answer, String option1, String option2, String option3, String str6) {
        this.id = i;
        this.question = question;
        this.answer = answer;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = str6;
        this.photo = str6;
    }

    public Task_QueConstructor(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}
