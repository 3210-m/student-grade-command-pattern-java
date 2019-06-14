package com.tw.entity;


import java.util.Objects;


public class Student {
    private int number;
    private String name;
    private String nation;
    private int kclass;
    private Grade grade;

    public void setNumber(int number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setKclass(int kclass) {
        this.kclass = kclass;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getKclass() {
        return kclass;
    }

    public String getNation() {
        return nation;
    }

    public Grade getGrade() {
        return grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return number == student.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
