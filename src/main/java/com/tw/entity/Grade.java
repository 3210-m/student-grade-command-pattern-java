package com.tw.entity;



import java.util.Map;


public class Grade {
    private int id; //id
    private Map<String,Double> subject;

    public int getId() {
        return id;
    }

    public Map<String, Double> getSubject() {
        return subject;
    }



    public void setSubject(Map<String, Double> subject) {
        this.subject = subject;
    }
}
