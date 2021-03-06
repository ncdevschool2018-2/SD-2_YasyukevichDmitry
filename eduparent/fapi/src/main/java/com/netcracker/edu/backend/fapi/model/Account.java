package com.netcracker.edu.backend.fapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    private int id;
    @NotNull
    @Size(min=2, max=45)
    private String name;
    @NotNull
    @Size(min=2, max=45)
    private String sname;
    @Email
    private String email;
    private int userId;

    Account(){}

    Account(int id,String name,String sname,String email, int userId){
        this.id = id;
        this.name = name;
        this.sname = sname;
        this.email = email;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
