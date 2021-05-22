package com.example.marketapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAuthenticate {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;

    public UserAuthenticate(String email, String password){
        this.email = email;
        this.password = password;
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
}
