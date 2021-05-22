package com.example.marketapp.util;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;



public class TopGridLayoutManager extends GridLayoutManager
{
    public TopGridLayoutManager(Context context, int spanCount)
    {
        super(context, spanCount);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position)
    {
        RecyclerView.SmoothScroller smoothScroller = new TopSmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    private static class TopSmoothScroller extends LinearSmoothScroller
    {
        TopSmoothScroller(Context context)
        {
            super(context);
        }

        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference)
        {
            return (boxStart - viewStart);
        }
    }
}