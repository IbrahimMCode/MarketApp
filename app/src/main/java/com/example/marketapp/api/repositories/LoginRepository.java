package com.example.marketapp.api.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.marketapp.api.models.User;
import com.example.marketapp.api.models.UserAuthenticate;
import com.example.marketapp.api.clients.APIClient;
import com.example.marketapp.api.services.UserAPIInterface;
import com.example.marketapp.managers.UserManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    public UserAPIInterface userAPIInterface;
    public LoginRepository() {
        userAPIInterface = APIClient.getClient().create(UserAPIInterface.class);
    }

    public MutableLiveData<Boolean> getMutableData(String email, String password) {
        final MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        UserAuthenticate user = new UserAuthenticate(email, password);
        Call<User> call1 = userAPIInterface.GetUser(user);
        call1.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User user = response.body();
                    UserManager.getInstance().UpdateLoginPreferences(email, password);
                    UserManager.getInstance().UpdateUserPreferences(user.getToken(), user.getRole(), user.getId(), user.getUsername());
                    mutableLiveData.setValue(true);
                }else {
                    mutableLiveData.setValue(false);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
            }
        });
        return mutableLiveData;
    }
}
