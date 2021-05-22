package com.example.marketapp.api.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.marketapp.api.clients.APIClient;
import com.example.marketapp.api.models.User;
import com.example.marketapp.api.models.UserAuthenticate;
import com.example.marketapp.api.models.UserRegister;
import com.example.marketapp.api.services.UserAPIInterface;
import com.example.marketapp.managers.UserManager;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {
    public UserAPIInterface userAPIInterface;
    public RegisterRepository() {
        userAPIInterface = APIClient.getClient().create(UserAPIInterface.class);
    }

    public MutableLiveData<Boolean> getMutableData(String email, String password, String username) {
        final MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        UserRegister user = new UserRegister(username, email, password);
        Call<JsonElement> call1 = userAPIInterface.RegisterUser(user);
        call1.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(true);
                }else {
                    mutableLiveData.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                call.cancel();
            }
        });
        return mutableLiveData;
    }
}
