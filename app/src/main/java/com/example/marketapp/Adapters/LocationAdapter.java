package com.example.marketapp.Adapters;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.marketapp.MarketSelection;
import com.example.marketapp.R;
import com.example.marketapp.ViewModels.LocationViewModel;
import com.example.marketapp.api.models.Location;
import com.example.marketapp.managers.UserManager;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;
    private UserManager userManager;
    //we are storing all the products in a list
    private List<Location> locationList;
    LocationViewModel viewModel;

    //getting the context and product list with constructor
    public LocationAdapter(Context mCtx, LocationViewModel viewModel) {
        this.mCtx = mCtx;
        this.locationList = new ArrayList<Location>();
        this.viewModel = viewModel;
    }



    public void setResults(List<Location> results) {
        this.locationList = results;
        notifyDataSetChanged();
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_location, null);
        return new LocationViewHolder(view);

    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        //getting the product of the specified position
        Location location = locationList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager.getInstance().SetSelectedAddress(location.getId());
                Intent intent = new Intent(mCtx, MarketSelection.class);
                mCtx.startActivity(intent);
            }
        });

        holder.deletelocationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteLocation(location.getId());
                locationList.remove(location);

                notifyDataSetChanged();
            }
        });


        //binding the data with the viewholder views
        holder.textViewTitle.setText(location.getCity());
        holder.textViewShortDesc.setText(location.getDescription());


    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc;
        ImageView imageView, deletelocationbtn;

        public LocationViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.orderId);
            textViewShortDesc = itemView.findViewById(R.id.orderHistoryDetails);
            deletelocationbtn = itemView.findViewById(R.id.deleteLocationbtn);

        }




    }
}

