package com.example.marketapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.marketapp.api.models.Category;
import com.example.marketapp.api.models.Location;
import com.example.marketapp.api.models.Market;
import com.example.marketapp.api.repositories.CategoryRepository;
import com.example.marketapp.api.repositories.LocationRepository;
import com.example.marketapp.api.repositories.MarketRepository;

import java.util.List;

public class CategoriesViewModel extends AndroidViewModel {
    private CategoryRepository categoryRepository;
    private LiveData<List<Category>> categories;

    public CategoriesViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        categoryRepository = new CategoryRepository();
        categories = categoryRepository.getMainCategories();
    }

    public void getCategories(){
        categoryRepository.getCategories();
    }

    public LiveData<List<Category>> getMainCategories() {
        return categories;
    }

}
