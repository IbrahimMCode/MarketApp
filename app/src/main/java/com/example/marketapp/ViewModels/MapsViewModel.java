package com.example.marketapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.marketapp.api.models.LocationPost;
import com.example.marketapp.api.models.Market;
import com.example.marketapp.api.repositories.LoginRepository;
import com.example.marketapp.api.repositories.MapsRepository;
import com.example.marketapp.api.repositories.MarketRepository;

import java.util.List;

public class MapsViewModel extends AndroidViewModel {
    private MapsRepository mapsRepository;
    private MarketRepository marketRepository;


    public MapsViewModel(@NonNull Application application) {
        super(application);
        mapsRepository = new MapsRepository();
        marketRepository = new MarketRepository();
    }

    public LiveData<Boolean> getResponse(LocationPost locationPost) {
        return mapsRepository.getMutableData(locationPost);
    }

    public LiveData<List<Market>> getMarkets(){
        marketRepository.getAllMarkets();
        return marketRepository.getAll();
    }

}
