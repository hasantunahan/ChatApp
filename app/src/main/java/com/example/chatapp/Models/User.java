package com.example.chatapp.Models;

public class User {
    String id;
    String imageURL;
    public User(){

    }



    String name;

   String ara;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAra() {
        return ara;
    }

    public void setAra(String ara) {
        this.ara = ara;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User(String id, String imageURL, String name, String ara, String status) {
        this.id = id;
        this.imageURL = imageURL;
        this.name = name;
        this.ara = ara;
        this.status = status;
    }

    String status;




}
