/*
package com.example.qqz.elktest;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.JsonReader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;

public class CurrencyManager {

    private static CurrencyManager instance = null;

    private HashMap<String, Currency> currencyMap = new HashMap<>();

    private static final String SEARCH_COMMON_ZH =
            "[\"CNY\", \"USD\", \"HKD\", \"EUR\", \"JPY\", \"GBP\", \"TWD\", \"MOP\", \"AUD\", \"CAD\"]";
    private static final String SEARCH_COMMON_EN =
            "[\"USD\", \"CNY\", \"GBP\", \"EUR\", \"INR\", \"AUD\", \"CAD\", \"AED\", \"JPY\", \"HKD\"]";

    public static CurrencyManager getInstance() {
        if (instance == null) {
            synchronized (CurrencyManager.class) {
                if (instance == null) {
                    instance = new CurrencyManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        Gson gson = GsonFactory.getInstance().getGson();
        AssetManager assetManager = context.getAssets();
        InputStream is = null;
        JsonReader reader = null;
        try {
            is = assetManager.open("currencies.json");
            reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
            Type t = new TypeToken<ArrayList<Currency>>() {}.getType();
            ArrayList<Currency> currencies = gson.fromJson(String.valueOf(reader), t);
            for (Currency currency : currencies) {
                currencyMap.put(currency.getSymbol(), currency);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public ArrayList<Currency> currencies() {
        return new ArrayList<>(currencyMap.values());
    }

    public List<Currency> searchCurrency(final Context context, String data) {
        data = data.toLowerCase();
        List<Currency> currencies = new ArrayList<>();
        for (Currency c : currencyMap.values()) {
            String searchData = c.getSearchData();
            if (!TextUtils.isEmpty(searchData) && searchData.contains(data)) {
                currencies.add(c);
            }
        }
        Collections.sort(currencies, new Comparator<Currency>() {
            @Override
            public int compare(Currency o1, Currency o2) {
                return o1.getSection(context).compareTo(o2.getSection(context));
            }
        });
        return currencies;
    }

}
*/
