package com.example.marketapp.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.marketapp.MarketActivity;
import com.example.marketapp.R;
import com.example.marketapp.api.models.Market;
import com.example.marketapp.managers.SessionManager;
import com.example.marketapp.services.DateParser;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MarketViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;
    private DateParser dateParser;
    //we are storing all the products in a list
    private List<Market> MarketList;

    //getting the context and product list with constructor
    public MarketAdapter(Context mCtx) {
        this.mCtx = mCtx;
        this.dateParser = new DateParser();
        this.MarketList = new ArrayList<Market>();
    }

    @Override
    public MarketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_market, null);
        return new MarketViewHolder(view);
    }


    public void setResults(List<Market> results) {
        this.MarketList = results;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MarketViewHolder holder, int position) {
        //getting the product of the specified position
        Market Market = MarketList.get(position);

        holder.marketNameView.setText(Market.getName());
        holder.marketOpeningHoursView.setText(Market.getOpeningHour() + " - " + Market.getClosingHour());
        Glide.with(mCtx).load(Market.getImagePath()).into(holder.marketLogoView);

        if (!isOpen(Market)){
            holder.marketStatusView.setColorFilter(Color.rgb(255,0,0));
            holder.marketOpeningStatusText.setText(R.string.not_accepting_orders);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen(Market)){
                    Intent intent =  new Intent(mCtx, MarketActivity.class);
                    SessionManager.getInstance().setSelectedMarketId(Market.getId());
                    mCtx.startActivity(intent);
                }else{
                    Snackbar snack = Snackbar.make(holder.itemView, "This Market is Closed right now. Please Try Again Later!", Snackbar.LENGTH_LONG);
                    View view = snack.getView();
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                    params.gravity = Gravity.CENTER;
                    view.setLayoutParams(params);
                    snack.show();
                }

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar snack = Snackbar.make(holder.itemView, Market.getDescription(), Snackbar.LENGTH_LONG);
                View view = snack.getView();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                params.gravity = Gravity.CENTER;
                view.setLayoutParams(params);
                snack.show();
                return true;
            }
        });




    }

    public boolean isOpen(Market market){
        Calendar now = Calendar.getInstance();

        int hour = now.get(Calendar.HOUR_OF_DAY); // Get hour in 24 hour format
        int minute = now.get(Calendar.MINUTE);

        Date date = this.dateParser.parseDate(hour + ":" + minute);
        Date dateCompareOne = this.dateParser.parseDate(market.getOpeningHour());
        Date dateCompareTwo = this.dateParser.parseDate(market.getClosingHour());
        return  (dateCompareOne.before( date ) && dateCompareTwo.after(date));
    }

    @Override
    public int getItemCount() {
        return MarketList.size();
    }

    class MarketViewHolder extends RecyclerView.ViewHolder {

        TextView marketNameView, marketOpeningHoursView, marketOpeningStatusText;
        ImageView marketLogoView, marketStatusView;

        public MarketViewHolder(View itemView) {
            super(itemView);
            marketNameView = itemView.findViewById(R.id.orderId);
//            marketDescriptionView = itemView.findViewById(R.id.marketDescriptionView);
            marketOpeningHoursView = itemView.findViewById(R.id.orderHistoryDetails);
            marketLogoView = itemView.findViewById(R.id.orderhistory_image);
            marketStatusView = itemView.findViewById(R.id.marketStatusView);
            marketOpeningStatusText = itemView.findViewById(R.id.marketOpeningStatusText);

        }
    }
}

