package com.example.marketapp.api.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.marketapp.api.models.Location;
import com.example.marketapp.api.clients.APIClient;
import com.example.marketapp.api.services.LocationAPIInterface;
import com.example.marketapp.managers.UserManager;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationRepository {

    public LocationAPIInterface locationAPIInterface;
    private MutableLiveData<List<Location>> locationResponseLiveData;

    public LocationRepository(){
        locationResponseLiveData = new MutableLiveData<>();
        locationAPIInterface = APIClient.getClient().create(LocationAPIInterface.class);
    }

    public void getLocationsList(){
        Call<List<Location>> call1 = locationAPIInterface.GetUserLocations(UserManager.getInstance().GetToken());
        call1.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.isSuccessful()) {
                    locationResponseLiveData.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                locationResponseLiveData.postValue(null);
            }
        });
    }

    public MutableLiveData<Boolean> deleteLocation(String locationId){
        final MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        Call<JsonElement> call = locationAPIInterface.DeleteById(locationId);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mutableLiveData.setValue(true);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                mutableLiveData.setValue(false);
            }
        });
        return mutableLiveData;
    }

    public LiveData<List<Location>> getLocationsListLiveData() {
        return locationResponseLiveData;
    }

}
