package com.anonsgoup.anons.models;


import java.util.Date;

public class User {
    private String email;
    private String username;
    private String name;
    private String surname;
    private String dob;
    private String gender;
    private long createdDate;


    public User(String email, String username, String name, String surname, String dob, String gender, long date) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.gender = gender;
        this.createdDate = date;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public long getCreatedDate() {
        return createdDate;
    }
}
