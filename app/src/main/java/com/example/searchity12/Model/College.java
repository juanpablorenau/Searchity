package com.example.searchity12.Model;

import java.util.Date;

public class College extends User{

    private String province;
    private String university;
    private String degree;
    private String course;

    public College(String email, String password, String name, String lastName, Date birthday, String gender,
                   String province, String university, String degree, String course) {
        super(email, password, name, lastName, birthday, gender);
        this.province = province;
        this.university = university;
        this.degree = degree;
        this.course = course;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
