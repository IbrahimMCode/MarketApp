package com.example.marketapp;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.marketapp.Adapters.LocationAdapter;
import com.example.marketapp.ViewModels.LocationViewModel;
import com.example.marketapp.api.models.Location;
import com.example.marketapp.managers.SessionManager;
import com.example.marketapp.managers.UserManager;
import com.google.gson.JsonElement;

import java.util.List;


public class LocationSelection extends AppCompatActivity {
    private LocationViewModel viewModel;
    private TextView savedlocations_slogan;
    private LocationAdapter adapter;
    RecyclerView recyclerView;
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selection);

        savedlocations_slogan = findViewById(R.id.savedlocationstext);




        viewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        viewModel.init();

        adapter = new LocationAdapter(this, viewModel);


        viewModel.getLocationsListLiveData().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable List<Location> locations) {
                if (locations != null) {
                    adapter.setResults(locations);

                }
                if (locations.size() < 1){
                    savedlocations_slogan.setText("You don't have any saved locations, Please try adding a new one using the + button!");
                }
            }
        });

        viewModel.getDeleteObserver().observe(this, new Observer<JsonElement>() {
            @Override
            public void onChanged(JsonElement jsonElement) {

            }
        });


        recyclerView = (RecyclerView) this.findViewById(R.id.LocationsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        viewModel.getLocations();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.layout_switch:
                intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                UserManager.getInstance().ClearLoginPref();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.orderHistory:
                intent = new Intent(this, AccountActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.locations_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void hello(){

    }



}