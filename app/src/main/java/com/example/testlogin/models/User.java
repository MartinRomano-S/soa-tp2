package com.example.testlogin.models;

import com.example.testlogin.interfaces.JSONable;

import org.json.JSONObject;

public class User implements JSONable {
    private String name;
    private String lastname;
    private Integer dni;
    private String email;
    private String password;
    private Integer commision;
    private Integer group;

    public User(){}

    public User(String name, String lastname, Integer dni, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.email = email;
        this.password = password;
        this.commision = 3900;
        this.group = 4;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
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

    @Override
    public JSONObject toJSON() {
        return null;
    }
}
