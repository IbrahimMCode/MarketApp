package com.example.marketapp.Adapters;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bumptech.glide.Glide;
import com.example.marketapp.R;
import com.example.marketapp.api.models.Solution;
import com.example.marketapp.fragment.ItemFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brkckr on 28.10.2017.
 *
 * CategoryPagerAdapter represents each page as a Fragment that is persistently
 * kept in the fragment manager as long as the user
 * can return to the page.
 */

public class CategoryPagerAdapter extends FragmentPagerAdapter
{
    private List<Solution> solutionList = new ArrayList<>();
    private Context context;

    public CategoryPagerAdapter(FragmentManager manager, Context context, ArrayList<Solution> solutionList)
    {
        super(manager);
        this.solutionList = solutionList;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        return ItemFragment.newInstance(solutionList.get(position));
    }

    @Override
    public int getCount()
    {
        return solutionList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return solutionList.get(position).category.getName();
    }

    public View getTabView(final int position)
    {
        // Given you have a custom layout in `res/layout/tab_item.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.tab_item, null);

        TextView txtCategoryName = v.findViewById(R.id.txtCategoryName);
        ImageView imgCategoryIcon = v.findViewById(R.id.imgCategoryIcon);

        txtCategoryName.setText(solutionList.get(position).category.getName());

        Glide.with(this.context)
                .load(solutionList.get(position).category.getImagePath())
                .into(imgCategoryIcon);
        System.out.println(solutionList.get(position).category.getImagePath());
//        imgCategoryIcon.setImageResource(solutionList.get(position).category.resourceId);

        return v;
    }
}
