package com.example.marketapp.api.services;

import com.example.marketapp.api.models.Location;
import com.example.marketapp.api.models.LocationPost;
import com.example.marketapp.api.models.Order;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OrderAPIInterface {
    @POST("/api/Orders")
    Call<JsonElement> PostOrder(@Body Order order, @Header("Authorization") String authHeader);

    @GET("/api/Orders")
    Call<List<Order>> GetUserOrders(@Header("Authorization") String authHeader);
}
