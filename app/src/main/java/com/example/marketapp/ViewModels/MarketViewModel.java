package com.example.marketapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.marketapp.api.models.Location;
import com.example.marketapp.api.models.Market;
import com.example.marketapp.api.repositories.LocationRepository;
import com.example.marketapp.api.repositories.MarketRepository;

import java.util.List;

public class MarketViewModel extends AndroidViewModel {
    private MarketRepository marketRepository;
    private LiveData<List<Market>> nearestMarkets;

    public MarketViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        marketRepository = new MarketRepository();
        nearestMarkets = marketRepository.getNearestMarkets();
    }

    public void getMarkets(){
        marketRepository.getMarkets();
    }

    public LiveData<List<Market>> getNearestMarkets() {
        return nearestMarkets;
    }

}
