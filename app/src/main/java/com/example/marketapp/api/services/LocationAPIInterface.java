package com.example.marketapp.api.services;

import com.example.marketapp.api.models.Location;
import com.example.marketapp.api.models.LocationPost;
import com.example.marketapp.api.models.User;
import com.example.marketapp.api.models.UserAuthenticate;
import com.example.marketapp.api.models.UserRegister;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LocationAPIInterface {
    @POST("/api/Location")
    Call<JsonElement> PostLocation(@Body LocationPost locationPost, @Header("Authorization") String authHeader);

    @GET("/api/Location")
    Call<List<Location>> GetUserLocations(@Header("Authorization") String authHeader);

    @DELETE("/api/Location/{id}")
    Call<JsonElement> DeleteById(@Path("id") String locationId);
}
