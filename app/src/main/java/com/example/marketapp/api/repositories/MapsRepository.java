package com.example.marketapp.api.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.marketapp.api.models.LocationPost;
import com.example.marketapp.api.clients.APIClient;
import com.example.marketapp.api.services.LocationAPIInterface;
import com.example.marketapp.managers.UserManager;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsRepository {
    public LocationAPIInterface locationAPIInterface;
    public MapsRepository() {
        locationAPIInterface = APIClient.getClient().create(LocationAPIInterface.class);
    }
    public MutableLiveData<Boolean> getMutableData(LocationPost locationPost) {
        final MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        Call<JsonElement> call1 = locationAPIInterface.PostLocation(locationPost, UserManager.getInstance().GetToken() );
        call1.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()){
//                    goToListofLocations();
                    mutableLiveData.setValue(true);

                }else {
//                    setContentView(R.layout.activity_maps);
//                    Snackbar snackbar = Snackbar.make(findViewById(R.id.ConstraintLayout), "Failed to save Address", Snackbar.LENGTH_INDEFINITE);
//                    snackbar.setAction("Dismiss", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            snackbar.dismiss();
//                        }
//                    });
//                    snackbar.show();
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
