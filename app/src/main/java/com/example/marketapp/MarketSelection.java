package com.example.marketapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.marketapp.Adapters.MarketAdapter;
import com.example.marketapp.ViewModels.MarketViewModel;
import com.example.marketapp.api.models.Market;
import com.example.marketapp.managers.UserManager;

import java.util.List;

public class MarketSelection extends AppCompatActivity {
    int Columns_count=2;


    private MarketViewModel viewModel;
    private MarketAdapter adapter;
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_selection);
        adapter = new MarketAdapter(this);
        viewModel = ViewModelProviders.of(this).get(MarketViewModel.class);
        viewModel.init();
        viewModel.getNearestMarkets().observe(this, new Observer<List<Market>>() {
            @Override
            public void onChanged(@Nullable List<Market> markets) {
                if (markets != null) {
                    adapter.setResults(markets);
                }
            }
        });
        recyclerView = this.findViewById(R.id.MarketRecyclerView);
        recyclerView.setHasFixedSize(true);
        viewModel.getMarkets();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, Columns_count));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.markets_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.layout_switch:
                if (this.Columns_count ==1){
                    this.Columns_count = 2;
                    item.setIcon(R.drawable.ic_baseline_double_column_24);
                }else if(this.Columns_count ==2) {
                    this.Columns_count = 3;
                    item.setIcon(R.drawable.ic_baseline_layout_selection_24);
                }else {
                    this.Columns_count = 1;
                    item.setIcon(R.drawable.ic_baseline_single_column_24);
                }
                recyclerView.setLayoutManager(new GridLayoutManager(this, this.Columns_count));
                recyclerView.setAdapter(adapter);
                return true;
            case R.id.logout:
                UserManager.getInstance().ClearLoginPref();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.orderHistory:
                intent = new Intent(this, AccountActivity.class);
                startActivity(intent);
                finish();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

}