package com.example.searchity12.Model;

import java.util.Calendar;
import java.util.Date;

public class User {

    protected String email;
    protected String password;
    protected String name;
    protected String lastName;
    protected Date birthday;
    protected String gender;
    protected Date registrationDay;

    protected User(String email, String password, String name, String lastName, Date birthday, String genre) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = genre;
        Calendar calendar = Calendar.getInstance();
        this.registrationDay = calendar.getTime();;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGendre() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getRegistrationDay() {
        return registrationDay;
    }

    public void setRegistrationDay(Date registrationDay) {
        this.registrationDay = registrationDay;
    }
}
