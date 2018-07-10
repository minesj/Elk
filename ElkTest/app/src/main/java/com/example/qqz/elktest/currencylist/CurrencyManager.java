package com.example.qqz.elktest.currencylist;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.qqz.elktest.gson.GsonFactory;
import com.example.qqz.elktest.gson.JsonUtil;
import com.example.qqz.elktest.helper.LanguageHelper;
import com.example.qqz.elktest.helper.ShareDataHelper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
        com.google.gson.stream.JsonReader reader = null;
        try {
            is = assetManager.open("currencies.json");
            reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
            Type t = new TypeToken<ArrayList<Currency>>() {}.getType();
            ArrayList<Currency> currencies = gson.fromJson(reader, t);
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

    public List<Currency> customCurrencies(Context context) {
        String jsonStr = ShareDataHelper
                .getStringValue(context, ShareDataHelper.SHARE_COMMONUSUALLIST);
        if (TextUtils.isEmpty(jsonStr)) {
            return null;
        }
        try {
            Gson gson = GsonFactory.getInstance().getGson();
            JsonElement jsonElem = gson.fromJson(jsonStr, JsonElement.class);
            if (!jsonElem.isJsonArray()) {
                return null;
            }
            JsonArray jsonArr = jsonElem.getAsJsonArray();
            List<Currency> currencyList = new ArrayList<>(jsonArr.size());
            for (int i = 0, count = jsonArr.size(); i < count; i++) {
                JsonElement entry = jsonArr.get(i);
                String symbol = null;
                if (entry.isJsonObject()) {
                    // For backward compatibility (version <= 1.4.2)
                    symbol = JsonUtil.getAsString(
                            entry.getAsJsonObject(), "jsonObject.nameValuePairs.symbol");
                } else if (entry.isJsonPrimitive()) {
                    symbol = entry.getAsString();
                }
                if (TextUtils.isEmpty(symbol)) {
                    return null;
                }
                Currency currency = CurrencyManager.getInstance()
                        .findCurrencyBySymbol(symbol);
                if (currency == null) {
                    return null;
                }
                currencyList.add(currency);
            }
            return currencyList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveCustomCurrencies(Context context, List<Currency> currencies) {
        List<String> symbolList = new ArrayList<>(currencies.size());
        for (int i = 0, count = currencies.size(); i < count; i++) {
            symbolList.add(currencies.get(i).getSymbol());
        }
        ShareDataHelper.saveCacheData(context, ShareDataHelper.SHARE_COMMONUSUALLIST, symbolList);
    }

    /*public List<Currency> defaultCurrencies(Context context, String countryCode, int maxCount) {
        List<Currency> list = commonCurrenciesForSearch(context);
        if (maxCount > 0) {
            while (list.size() > maxCount) {
                list.remove(list.size() - 1);
            }
        }
        if (TextUtils.isEmpty(countryCode)) {
            countryCode = Locale.getDefault().getCountry();
        }
        try {
            Currency localCurrency = findCurrencyByCountryCode(countryCode);
            boolean found = false;
            for (Currency item : list) {
                if (item.getSymbol().equals(localCurrency.getSymbol())) {
                    list.remove(item);
                    found = true;
                    break;
                }
            }
            list.add(0, localCurrency);
            if (!found) {
                list.remove(list.size() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }*/
    public ArrayList<Currency> currencies() {
        return new ArrayList<>(currencyMap.values());
    }

    public Currency findCurrencyBySymbol(String symbol) {
        Currency currency = currencyMap.get(symbol);
        return (currency != null) ? Currency.copyFrom(currency) : null;
    }

    public Currency findCurrencyByCountryCode(String code) {
        for (Currency currency : currencyMap.values()) {
            if (currency.getCountryCode().contains(code) || currency.containsCountryCode(code)) {
                return Currency.copyFrom(currency);
            }
        }
        return null;
    }
   /* public List<Currency> searchCurrency(final Context context, String data) {
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
    }*/
   public List<Currency> commonCurrenciesForSearch(Context context) {
       List<Currency> currencies = getCommonCurrenciesForSearch(context);
       if (currencies != null) {
           return currencies;
       }else {
           return new ArrayList<>();
       }
   }

    @Nullable
    private List<Currency> getCommonCurrenciesForSearch(Context context) {
        try {
            String language = LanguageHelper.getSystemLanguage(context);
            String zhJsonStr = ShareDataHelper.getStringValueWithDefault(context, ShareDataHelper.SHARE_SEARCHZH_COMMONUSUALLIST, SEARCH_COMMON_ZH);
            if(TextUtils.isEmpty(zhJsonStr)){
                zhJsonStr = SEARCH_COMMON_ZH;
            }
            String enJsonStr = ShareDataHelper.getStringValueWithDefault(context, ShareDataHelper.SHARE_SEARCHEN_COMMONUSUALLIST, SEARCH_COMMON_EN);
            if(TextUtils.isEmpty(enJsonStr)){
                enJsonStr = SEARCH_COMMON_EN;
            }
            String jsonStr = language.contains("zh") ? zhJsonStr : enJsonStr;
            Gson gson = GsonFactory.getInstance().getGson();
            Type t = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> symbolList = gson.fromJson(jsonStr, t);
            ArrayList<Currency> currencies = new ArrayList<>(symbolList.size());
            for (int i = 0, count = symbolList.size(); i < count; i++) {
                Currency c = findCurrencyBySymbol(symbolList.get(i));
                if (c != null) {
                    currencies.add(c);
                }
            }
            return currencies;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
