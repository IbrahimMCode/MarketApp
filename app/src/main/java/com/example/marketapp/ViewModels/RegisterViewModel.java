package com.example.marketapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.marketapp.api.repositories.RegisterRepository;

public class RegisterViewModel extends AndroidViewModel {

    private RegisterRepository registerRepository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        registerRepository = new RegisterRepository();
    }

    public LiveData<Boolean> getResponse(String email, String password, String username) {
        return registerRepository.getMutableData(email, password, username);
    }
}
