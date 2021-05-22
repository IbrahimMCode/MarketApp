package com.example.marketapp.Adapters;

import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marketapp.R;
import com.example.marketapp.api.models.SubCategory;

import java.util.ArrayList;

import static androidx.core.content.res.ResourcesCompat.getColor;

/**
 * Created by brkckr on 28.10.2017.
 */

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder>
{
    private ArrayList<SubCategory> subCategoryList;
    public ISubCategoryAdapterItemCallback subCategoryAdapterItemCallback;

    public class SubCategoryViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtSubCategoryName;

        public SubCategoryViewHolder(View view)
        {
            super(view);
            txtSubCategoryName = view.findViewById(R.id.txtSubCategory);
        }
    }

    public interface ISubCategoryAdapterItemCallback
    {
        void onSubCategoryItemCallback(int position);
    }

    public SubCategoryAdapter(ArrayList<SubCategory> subCategoryList)
    {
        this.subCategoryList = subCategoryList;
    }

    @Override
    public SubCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_category_item, parent, false);

        return new SubCategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubCategoryViewHolder holder, final int position)
    {
        final SubCategory subCategoryItem = subCategoryList.get(position);
        holder.txtSubCategoryName.setText(subCategoryItem.getName());

        if (subCategoryItem.isSelected)
        {
            holder.txtSubCategoryName.setBackgroundResource(R.drawable.sub_category_selected_item);
            holder.txtSubCategoryName.setTextColor(Color.parseColor("#FFFFFF"));
        } else
        {
            holder.txtSubCategoryName.setBackgroundResource(R.drawable.sub_category_item);
            holder.txtSubCategoryName.setTextColor(holder.txtSubCategoryName.getContext().getResources().getColor(R.color.primary));
        }

        holder.txtSubCategoryName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (subCategoryAdapterItemCallback != null)
                {
                    subCategoryAdapterItemCallback.onSubCategoryItemCallback(position);
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return subCategoryList.size();
    }

    public SubCategory getItem(int position)
    {
        return subCategoryList.get(position);
    }
}
