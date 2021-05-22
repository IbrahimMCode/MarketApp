package com.example.marketapp.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.marketapp.api.models.Order;
import com.example.marketapp.api.repositories.OrderRepository;

import java.util.List;

public class PageViewModel extends ViewModel {
    private OrderRepository orderRepository;
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<List<Order>> ordersList = new MutableLiveData<>();

    public void init(){
        orderRepository = new OrderRepository();
        ordersList = orderRepository.getOrderHistory();
    }

    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Profile Section";
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void getOrders(){
        orderRepository.getOrders();
    }

    public LiveData<List<Order>> getOrderHistory(){
        return ordersList;
    }
}