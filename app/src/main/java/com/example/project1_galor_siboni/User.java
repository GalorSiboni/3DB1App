package com.example.project1_galor_siboni;

public class User {
    private String Name = "";
    private int ID = 0;
    private int Age = 0;
    private int Phone = 0;
    private String imageUrl = "";

    public User() {
    }

    public User(String name, int id, int age, int phoneNumber, String imageUrl) {
        this.Name = name;
        this.ID = id;
        this.Age = age;
        this.Phone = phoneNumber;
        this.imageUrl = imageUrl;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        Phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}

