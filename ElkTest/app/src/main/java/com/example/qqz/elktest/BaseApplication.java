package com.example.qqz.elktest;

import android.app.Application;

import com.example.qqz.elktest.currencylist.CurrencyManager;

public class BaseApplication extends Application{

    public static BaseApplication sApplication = null;
    @Override
    public void onCreate() {
        sApplication = this;
        super.onCreate();
        CurrencyManager.getInstance().init(getApplicationContext());
    }


    public static BaseApplication getApplication() {
        return sApplication;
    }

}
