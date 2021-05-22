package com.example.marketapp.api.services;

import com.example.marketapp.api.models.Category;
import com.example.marketapp.api.models.Location;
import com.example.marketapp.api.models.LocationPost;
import com.example.marketapp.api.models.SubCategory;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CategoryAPIInterface {
    @GET("/api/Category/GetMain")
    Call<List<Category>> GetCategories();

    @GET("/api/Category/GetSub")
    Call<List<SubCategory>> GetSubCategories();


}
