package com.example.marketapp.ui.main;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketapp.Adapters.LocationAdapter;
import com.example.marketapp.Adapters.OrderHistoryAdapter;
import com.example.marketapp.R;
import com.example.marketapp.ViewModels.LocationViewModel;
import com.example.marketapp.api.models.Location;
import com.example.marketapp.api.models.Order;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class OrderHistoryFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel viewModel;
    RecyclerView recyclerView;
    private OrderHistoryAdapter adapter;


    public static OrderHistoryFragment newInstance(int index) {
        OrderHistoryFragment fragment = new OrderHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PageViewModel.class);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_history, container, false);

        adapter = new OrderHistoryAdapter(root.getContext());
        viewModel.init();
        viewModel.getOrderHistory().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(@Nullable List<Order> orders) {
                if (orders != null) {
                    adapter.setResults(orders);
                }
            }
        });
        recyclerView = (RecyclerView) root.findViewById(R.id.OrderHistoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(adapter);
        viewModel.getOrders();
        return root;
    }
}