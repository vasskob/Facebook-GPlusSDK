package com.task.vasskob.facebookgplussdk.model;

public class User {
    private String name;
    private String email;
    private String birthday;
    private String userPhotoUri;

    public User(String name, String email, String birthday, String uri) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.userPhotoUri = uri;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getUserPhotoUri() {
        return userPhotoUri;
    }
}