package com.example.marketapp.Adapters;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketapp.R;
import com.example.marketapp.api.models.Order;
import com.example.marketapp.api.models.OrderItem;
import com.example.marketapp.managers.UserManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;
    private UserManager userManager;
    //we are storing all the products in a list
    private List<Order> orders;

    //getting the context and product list with constructor
    public OrderHistoryAdapter(Context mCtx) {
        this.mCtx = mCtx;
        this.orders = new ArrayList<Order>();
    }

    public void setResults(List<Order> results) {
        this.orders = results;
        notifyDataSetChanged();
    }
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_order_history, null);
        return new OrderHistoryViewHolder(view);
    }




    @Override
    public void onBindViewHolder(OrderHistoryViewHolder holder, int position) {
        //getting the product of the specified position
        Order order = orders.get(position);


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String titleText = "Order Products";
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                @SuppressLint("ResourceAsColor") ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(R.color.lightgrey);
                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
                ssBuilder.setSpan(
                        foregroundColorSpan,
                        0,
                        titleText.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                View convertView = (View) inflater.inflate(R.layout.orderhistory_list_dialog, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle(ssBuilder);
                ListView lv = (ListView) convertView.findViewById(R.id.orderHistory_list);
                ArrayAdapter<OrderItem> adapter = new ArrayAdapter<OrderItem>(v.getContext(),R.layout.orderhistory_order_item,order.getOrderItems());
                lv.setAdapter(adapter);
                alertDialog.show();
                return true;
            }
        });
        //binding the data with the viewholder views
        holder.textViewTitle.setText("Market : " + String.valueOf(order.getMarket().getName()));
        holder.textViewShortDesc.setText("Order Total Price : " + String.valueOf(order.getTotalPrice()) + " LBP");


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc;
        ImageView imageView;

        public OrderHistoryViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.orderId);
            textViewShortDesc = itemView.findViewById(R.id.orderHistoryDetails);
        }




    }
}

