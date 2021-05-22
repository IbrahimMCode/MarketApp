package com.example.marketapp.ViewModels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.marketapp.api.repositories.LoginRepository;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepository loginRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = new LoginRepository();
    }

    public LiveData<Boolean> getResponse(String email, String password) {
        return loginRepository.getMutableData(email, password);
    }
}