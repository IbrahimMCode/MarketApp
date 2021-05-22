package com.example.marketapp.api.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.marketapp.api.clients.APIClient;
import com.example.marketapp.api.models.Category;
import com.example.marketapp.api.models.Location;
import com.example.marketapp.api.models.Order;
import com.example.marketapp.api.services.CategoryAPIInterface;
import com.example.marketapp.api.services.OrderAPIInterface;
import com.example.marketapp.managers.UserManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {
    public OrderAPIInterface orderAPIInterface;
    private MutableLiveData<List<Order>> orders;

    public OrderRepository(){
        orders = new MutableLiveData<>();
        orderAPIInterface = APIClient.getClient().create(OrderAPIInterface.class);
    }

    public void getOrders(){
        Call<List<Order>> call1 = orderAPIInterface.GetUserOrders(UserManager.getInstance().GetToken());
        call1.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()){
                    orders.postValue(response.body());
                }else {
                    orders.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                call.cancel();
            }
        });
    }
    public LiveData<List<Order>> getOrderHistory() {
        return orders;
    }
}
