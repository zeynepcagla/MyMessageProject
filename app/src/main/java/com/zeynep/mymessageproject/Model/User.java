package com.zeynep.mymessageproject.Model;

public class User {
public String phone;
    public String name_surname;
    public String id;
    public String imageURL;

    public User(){

    }
    public User(String phone, String name_surname, String id, String imageURL) {
        this.phone = phone;
        this.name_surname = name_surname;
        this.id = id;
        this.imageURL = imageURL;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName_surname() {
        return name_surname;
    }

    public void setName_surname(String name_surname) {
        this.name_surname = name_surname;
    }

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
}
