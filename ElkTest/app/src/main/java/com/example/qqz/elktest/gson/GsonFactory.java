package com.example.qqz.elktest.gson;

import com.example.qqz.elktest.currencylist.Currency;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class GsonFactory {
    private static GsonFactory instance = null;
    private Gson gson;

    public static GsonFactory getInstance() {
        if (instance == null) {
            synchronized (GsonFactory.class) {
                if (instance == null) {
                    instance = new GsonFactory();
                }
            }
        }
        return instance;
    }

    private GsonFactory() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Currency.class, new Currency.Deserializer())
                .create();
    }

    public Gson getGson() {
        return gson;
    }
}
