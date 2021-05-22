package com.example.marketapp.api.services;

import com.example.marketapp.api.models.User;
import com.example.marketapp.api.models.UserAuthenticate;
import com.example.marketapp.api.models.UserRegister;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPIInterface {
    @POST("/Users/authenticate")
    Call<User> GetUser(@Body UserAuthenticate user);

    @POST("/Users")
    Call<JsonElement> RegisterUser(@Body UserRegister user);

}
