package com.example.marketapp.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRegister {

    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("emailConfirmed")
    @Expose
    private Boolean emailConfirmed;
    @SerializedName("passwordHash")
    @Expose
    private String passwordHash;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("phoneNumberConfirmed")
    @Expose
    private Boolean phoneNumberConfirmed;

    public UserRegister(String userName, String email, String passwordHash) {
        this.userName = userName;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getPhoneNumberConfirmed() {
        return phoneNumberConfirmed;
    }

    public void setPhoneNumberConfirmed(Boolean phoneNumberConfirmed) {
        this.phoneNumberConfirmed = phoneNumberConfirmed;
    }

}
