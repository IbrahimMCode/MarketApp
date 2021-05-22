package com.example.marketapp;

import android.app.Application;
import android.content.Context;

public class MarketApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
    private static Context _context;

    public static Context getContext() {
        return _context;
    }

    public static void setContext(Context context) {
        _context = context;
    }
}
