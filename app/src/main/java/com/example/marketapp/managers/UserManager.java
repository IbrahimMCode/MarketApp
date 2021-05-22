package com.example.marketapp.managers;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.marketapp.MarketApp;
import com.example.marketapp.api.models.Market;

public class UserManager {
    private static UserManager instance;
    SharedPreferences LoginPref;
    SharedPreferences UserPref;
    SharedPreferences.Editor UserPrefEditor;
    SharedPreferences.Editor LoginPrefEditor;
    public UserManager() {
        this.LoginPref = MarketApp.getContext().getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
        this.UserPref = MarketApp.getContext().getSharedPreferences("UserPref", Context.MODE_PRIVATE);
        this.LoginPrefEditor = this.LoginPref.edit();
        this.UserPrefEditor = this.UserPref.edit();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }



    public SharedPreferences GetLoginPref(){
        return this.LoginPref;
    }

    public void ClearLoginPref(){
        this.LoginPrefEditor.clear().apply();
    }

    public Boolean isSetLogin(String field){
        return this.LoginPref.contains(field);
    }

    public void UpdateLoginPreferences(String email, String password){
        this.LoginPrefEditor.putString("email", email);
        this.LoginPrefEditor.putString("password", password);
        this.LoginPrefEditor.apply();
    }

    public void UpdateUserPreferences(String token, String role, String id, String username){
        this.UserPrefEditor.putString("token", token);
        this.UserPrefEditor.putString("role", role);
        this.UserPrefEditor.putString("id", id);
        this.UserPrefEditor.putString("username", username);
        this.UserPrefEditor.apply();
    }

    public String GetToken(){
        return "Bearer " + this.UserPref.getString("token", null);
    }

    public void SetSelectedAddress(String addressId){
        this.UserPrefEditor.putString("SelectedAddress", addressId);
        this.UserPrefEditor.apply();

    }
    public String GetSelectedAddress(){
        return UserPref.getString("SelectedAddress", null).toString();
    }



}
