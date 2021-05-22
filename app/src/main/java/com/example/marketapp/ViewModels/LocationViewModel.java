package com.example.marketapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.marketapp.api.models.Location;
import com.example.marketapp.api.repositories.LocationRepository;
import com.google.gson.JsonElement;

import java.util.List;

public class LocationViewModel extends AndroidViewModel {
    private LocationRepository locationRepository;
    private LiveData<List<Location>> locationResponseLiveData;
    private LiveData<JsonElement> locationDeleteResponse;

    public LocationViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        locationRepository = new LocationRepository();
        locationResponseLiveData = locationRepository.getLocationsListLiveData();
        locationDeleteResponse = new MutableLiveData<>();
    }

    public LiveData<JsonElement> getDeleteObserver(){
        return locationDeleteResponse;
    }

    public LiveData<Boolean> deleteLocation(String locationId) {
        return locationRepository.deleteLocation(locationId);
    }

    public void getLocations(){
        locationRepository.getLocationsList();
    }

    public LiveData<List<Location>> getLocationsListLiveData() {
        return locationResponseLiveData;
    }
}
