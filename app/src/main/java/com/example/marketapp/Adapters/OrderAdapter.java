package com.example.marketapp.Adapters;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.marketapp.R;
import com.example.marketapp.api.models.Cart;

import java.util.ArrayList;

/**
 * Created by brkckr on 6.12.2017.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>
{
    private ArrayList<Cart> orderList;
    private Activity activity;

    private IOrderAdapterCallback orderCallback;

    public interface IOrderAdapterCallback
    {
        void onIncreaseDecreaseCallback();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imgThumbnail;
        public TextView txtItemName;
        public TextView txtExtendedPrice;
        public TextView txtQuantity;
        public Button btnIncrease;
        public Button btnDecrease;

        public OrderViewHolder(View view)
        {
            super(view);
            imgThumbnail = view.findViewById(R.id.imgThumbnail);
            txtItemName = view.findViewById(R.id.txtItemName);
            txtExtendedPrice = view.findViewById(R.id.txtExtendedPrice);
            txtQuantity = view.findViewById(R.id.txtQuantity);
            btnIncrease =view.findViewById(R.id.btnIncrease);
            btnDecrease = view.findViewById(R.id.btnDecrease);
        }
    }

    public OrderAdapter(Activity activity, ArrayList<Cart> orderList)
    {
        this.activity = activity;
        this.orderList = orderList;
        orderCallback = (IOrderAdapterCallback) activity;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);

        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, final int position)
    {
        final Cart order = orderList.get(position);

        Glide.with(activity)
                .load(order.item.url)
                .into(holder.imgThumbnail);

        holder.txtItemName.setText(order.item.name);
        holder.txtExtendedPrice.setText(String.format("%d",(long) order.extendedPrice));
        holder.txtQuantity.setText(String.valueOf(order.quantity));

        holder.btnIncrease.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                order.quantity++;
                order.extendedPrice = order.quantity * order.item.unitPrice;
                holder.txtQuantity.setText(String.valueOf(order.quantity));
                holder.txtExtendedPrice.setText(String.format("%d", (long)order.extendedPrice));

                notifyDataSetChanged();
                orderCallback.onIncreaseDecreaseCallback();
            }
        });

        holder.btnDecrease.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                order.quantity--;
                order.extendedPrice = order.quantity * order.item.unitPrice;
                holder.txtQuantity.setText(String.valueOf(order.quantity));
                holder.txtExtendedPrice.setText(String.format("%d", (long)order.extendedPrice));

                if (order.quantity == 0)
                {
                    orderList.remove(position);
                }
                notifyDataSetChanged();
                orderCallback.onIncreaseDecreaseCallback();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return orderList.size();
    }
}

