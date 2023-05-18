package com.example.entity;

public class User {

    private int id;
    private String fio;
    private String phoneNumber;
    private String technologies;

    public User(int id, String fio, String phoneNumber, String technologies) {
        this.id = id;
        this.fio = fio;
        this.phoneNumber = phoneNumber;
        this.technologies = technologies;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", fio='" + fio + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               ", technologies='" + technologies + '\'' +
               '}';
    }
}
