package com.example.marketapp.api.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.marketapp.api.models.Market;
import com.example.marketapp.api.clients.APIClient;
import com.example.marketapp.api.services.MarketAPIInterface;
import com.example.marketapp.managers.UserManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarketRepository {

    public MarketAPIInterface marketAPIInterface;
    private MutableLiveData<List<Market>> nearestmarkets;
    private MutableLiveData<List<Market>> markets;


    public MarketRepository(){
        nearestmarkets = new MutableLiveData<>();
        markets = new MutableLiveData<>();
        marketAPIInterface = APIClient.getClient().create(MarketAPIInterface.class);
    }

    public void getMarkets(){
        UserManager userManager = UserManager.getInstance();
        Call<List<Market>> call1 = marketAPIInterface.GetNearestMarkets(userManager.GetSelectedAddress(), userManager.GetToken());
        call1.enqueue(new Callback<List<Market>>() {
            @Override
            public void onResponse(Call<List<Market>> call, Response<List<Market>> response) {
                if (response.isSuccessful()){
                    nearestmarkets.postValue(response.body());
                }else {
                    nearestmarkets.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<List<Market>> call, Throwable t) {
                call.cancel();
            }
        });
    }
    public LiveData<List<Market>> getNearestMarkets() {
        return nearestmarkets;
    }

    public void getAllMarkets(){
        Call<List<Market>> call1 = marketAPIInterface.GetAll();
        call1.enqueue(new Callback<List<Market>>() {
            @Override
            public void onResponse(Call<List<Market>> call, Response<List<Market>> response) {
                if (response.isSuccessful()){
                    markets.postValue(response.body());
                }else {
                    markets.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<List<Market>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public LiveData<List<Market>> getAll() {
        return markets;
    }


}
