package com.example.marketapp.api.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.marketapp.api.models.Category;
import com.example.marketapp.api.clients.APIClient;
import com.example.marketapp.api.services.CategoryAPIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    public CategoryAPIInterface categoryAPIInterface;
    private MutableLiveData<List<Category>> categories;

    public CategoryRepository(){
        categories = new MutableLiveData<>();
        categoryAPIInterface = APIClient.getClient().create(CategoryAPIInterface.class);
    }

    public void getCategories(){
        Call<List<Category>> call1 = categoryAPIInterface.GetCategories();
        call1.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()){
                    categories.postValue(response.body());
                }else {
                    categories.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                call.cancel();
            }
        });
    }
    public LiveData<List<Category>> getMainCategories() {
        return categories;
    }
}
