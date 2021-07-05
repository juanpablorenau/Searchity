package com.example.searchity12.Model;

import java.util.Date;

public class Graduated extends User {

    private String province;
    private String university;
    private String degree;
    private int graduationYear;

    public Graduated(String email, String password, String name, String lastName, Date birthday,
                     String gender, String province, String university, String degree, int graduationYear) {
        super(email, password, name, lastName, birthday, gender);
        this.province = province;
        this.university = university;
        this.degree = degree;
        this.graduationYear = graduationYear;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }
}
