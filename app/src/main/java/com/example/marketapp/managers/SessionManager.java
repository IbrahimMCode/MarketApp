package com.example.marketapp.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.marketapp.MarketApp;

public class SessionManager {
    private static SessionManager instance;

    public String SelectedMarketId;
    SharedPreferences SessionPrefs;
    SharedPreferences.Editor SessionPrefsEditor;
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public SessionManager() {
        this.SessionPrefs = MarketApp.getContext().getSharedPreferences("SessionPref", Context.MODE_PRIVATE);
        this.SessionPrefsEditor = this.SessionPrefs.edit();
    }

    public void setSelectedMarketId(int marketId){
        this.SessionPrefsEditor.putInt("current_marketid", marketId);
        this.SessionPrefsEditor.apply();
    }

    public int getSelectedMarketId(){
        return this.SessionPrefs.getInt("current_marketid", 1);
    }


}
