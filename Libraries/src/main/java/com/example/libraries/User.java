package com.example.libraries;

public class User {
    String name,image;
    int id;

    public User(String name, String image, int id) {
        this.name = name;
        this.image = image;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }
}
