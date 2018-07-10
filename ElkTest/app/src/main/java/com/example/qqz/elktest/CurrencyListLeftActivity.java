package com.example.qqz.elktest;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.qqz.elktest.adapter.CurrencyListAdapter2;
import com.example.qqz.elktest.currencylist.Currency;
import com.example.qqz.elktest.currencylist.CurrencyManager;
import com.example.qqz.elktest.view.SelectPicPopupLeftWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CurrencyListLeftActivity extends AppCompatActivity implements AbsListView
        .OnScrollListener, AdapterView.OnItemClickListener {


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int
            totalItemCount) {

    }

    public class CurrencyListData {
        List<Currency> currencyList = new ArrayList<>();
        Set<Integer> sectionStartIndexSet = new HashSet<>();
        HashMap<Character, Integer> sectionIndexMap = new HashMap<>();
        String index = "";
    }

    public static final char INDEX_FAV = '#';
    public CurrencyListAdapter2 sectionListAdapter;
    public ListView currencyList;
    public CurrencyListData fiatCurrencyListData = new CurrencyListData();
    public ImageButton ListBack;
    public static final String EXTRA_KEY_CURRENCY_SELECTED_CURRENCY = "CURRENCY_SELECTED_CURRENCY";
    public static final String EXTRA_KEY_SELECTED_CURRENCIES = "SELECTED_CURRENCIES";
    SelectPicPopupLeftWindow menuLeftWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currencylist);
        sectionListAdapter = new CurrencyListAdapter2(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            sectionListAdapter.setCurrentSelectedCurrency(
                    bundle.getString(EXTRA_KEY_CURRENCY_SELECTED_CURRENCY));
            sectionListAdapter.setSelectedCurrencies(
                    bundle.getStringArrayList(EXTRA_KEY_SELECTED_CURRENCIES));
        }
        new LoadCurrencyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        currencyList = findViewById(R.id.list);
        currencyList.setAdapter(sectionListAdapter);
        ListBack = findViewById(R.id.list_back);
        ListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        currencyList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int resultCode = 1;
        String LeftText = sectionListAdapter.getItem(position).getSymbol();
        Intent data = new Intent();
        data.putExtra("LeftTextView", LeftText);
        setResult(resultCode, data);
        finish();
    }

    public void showFiatCurrencyList() {
        if (fiatCurrencyListData.currencyList.size() > 0) {
            setData(fiatCurrencyListData.currencyList,
                    fiatCurrencyListData.sectionStartIndexSet);
        }
        currencyList.setSelectionFromTop(0, 0);
        final AbsListView.OnScrollListener l = this;
    }

    public class LoadCurrencyData extends CurrencyListData {
        String currencyType;
        Currency localCurrency = null;
        List<Currency> commonCurrencies = new ArrayList<>();
        List<Currency> allCurrencies = new ArrayList<>();

        LoadCurrencyData(String currencyType) {
            this.currencyType = currencyType;
        }
    }

    public class LoadCurrencyTask extends AsyncTask<Void, String, Void> {
        public Context context = CurrencyListLeftActivity.this;
        public Currency localCurrency;
        public List<Currency> commonCurrencies;
        public List<Currency> allCurrencies;
        public LoadCurrencyData fiatCurrencyLoadData;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            CurrencyManager currencyManager = CurrencyManager.getInstance();
            commonCurrencies = currencyManager.commonCurrenciesForSearch(context);
            allCurrencies = currencyManager.currencies();

            //BaseApplication application = (BaseApplication) getApplication();
            //boolean isLocationEnabled = ShareDataHelper
            //.getBooleanValue(context, ShareDataHelper.SHARE_LOCATION_ENABLE);
            /*if (AppHelper.isLocationOpen(context) &&
                    !TextUtils.isEmpty(application.countryCode) && isLocationEnabled) {
                localCurrency = currencyManager.findCurrencyByCountryCode(application.countryCode);
            }*/
            fiatCurrencyLoadData = new LoadCurrencyData(Currency.TYPE_FIAT_CURRENCY);
            /* cryptoCurrencyLoadData = new LoadCurrencyData(Currency.TYPE_CRYPTO_CURRENCY);*/
        }

        @Override
        public Void doInBackground(Void... voids) {
            int fiatCommCurrNumber = 0;
            //int cryptoCommCurrNumber = 0;
            for (int i = 0, count = commonCurrencies.size(); i < count; i++) {
                Currency currency = commonCurrencies.get(i);
                currency.setSection(Currency.SECTION_FAV);
                if (currency.isFiatCurrency()) {
                    if (fiatCommCurrNumber < 10) {
                        fiatCurrencyLoadData.commonCurrencies.add(currency);
                    }
                    fiatCommCurrNumber++;
                } /*else {
                    if(cryptoCommCurrNumber < 10){
                        cryptoCurrencyLoadData.commonCurrencies.add(currency);
                    }
                    cryptoCommCurrNumber++;
                }*/
            }
            if (localCurrency != null) {
                localCurrency.setSection(Currency.SECTION_LOCAL);
                if (localCurrency.isFiatCurrency()) {
                    fiatCurrencyLoadData.localCurrency = localCurrency;
                } /*else {
                    cryptoCurrencyLoadData.localCurrency = localCurrency;
                }*/
            }
            Collections.sort(allCurrencies, new Comparator<Currency>() {
                @Override
                public int compare(Currency a, Currency b) {
                    return a.getSection(context).compareTo(b.getSection(context));
                }
            });
            for (int i = 0, count = allCurrencies.size(); i < count; i++) {
                Currency currency = allCurrencies.get(i);
                if (currency.isFiatCurrency()) {
                    fiatCurrencyLoadData.allCurrencies.add(currency);
                } /*else {
                    cryptoCurrencyLoadData.allCurrencies.add(currency);
                }*/
            }
            processCurrencyData(fiatCurrencyLoadData);
            //processCurrencyData(cryptoCurrencyLoadData);
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            fiatCurrencyListData.currencyList = fiatCurrencyLoadData.currencyList;
            fiatCurrencyListData.sectionStartIndexSet = fiatCurrencyLoadData.sectionStartIndexSet;
            fiatCurrencyListData.sectionIndexMap = fiatCurrencyLoadData.sectionIndexMap;
            fiatCurrencyListData.index = fiatCurrencyLoadData.index;
            /*cryptoCurrencyListData.currencyList = cryptoCurrencyLoadData.currencyList;
            cryptoCurrencyListData.sectionStartIndexSet =
                    cryptoCurrencyLoadData.sectionStartIndexSet;
            cryptoCurrencyListData.sectionIndexMap = cryptoCurrencyLoadData.sectionIndexMap;
            cryptoCurrencyListData.index = cryptoCurrencyLoadData.index;*/
           /* switch (selectedTab) {
                case TAB_FIAT_CURRENCY:*/
            showFiatCurrencyList();
            //break;
                /*case TAB_CRYPTO_CURRENCY:
                    showCryptoCurrencyList();
                    break;*/
        }
    }

    public void processCurrencyData(LoadCurrencyData data) {
        int sectionStartIndex = 0;
        StringBuilder indexBuilder = new StringBuilder(32);
        if (data.commonCurrencies.size() > 0) {
            indexBuilder.append(INDEX_FAV);
            data.currencyList.addAll(data.commonCurrencies);
            data.sectionStartIndexSet.add(sectionStartIndex);
            data.sectionIndexMap.put(INDEX_FAV, sectionStartIndex);
            sectionStartIndex += data.commonCurrencies.size();
            //publishProgress(data.currencyType);
        }
            /*if (data.localCurrency != null) {
                indexBuilder.append(INDEX_LOCAL);
                data.currencyList.add(data.localCurrency);
                data.sectionStartIndexSet.add(sectionStartIndex);
                data.sectionIndexMap.put(INDEX_LOCAL, sectionStartIndex);
                sectionStartIndex += 1;
            }*/
        Set<String> sectionSet = new HashSet<>();
        for (int i = 0, count = data.allCurrencies.size(); i < count; i++) {
            Currency c = data.allCurrencies.get(i);
            String section = c.getSection(CurrencyListLeftActivity.this);
            boolean newSection = false;
            if (!TextUtils.isEmpty(section)) {
                section = section.substring(0, 1).toUpperCase();
                if (!sectionSet.contains(section)) {
                    newSection = true;
                    sectionSet.add(section);
                    indexBuilder.append(section.charAt(0));
                    data.currencyList.add(c);
                    data.sectionStartIndexSet.add(sectionStartIndex + i);
                    data.sectionIndexMap.put(section.charAt(0), sectionStartIndex + i);
                }
            }
            if (!newSection) {
                data.currencyList.add(c);
            }
        }
        data.index = indexBuilder.toString();
    }

    public void setData(List<Currency> currencyList, Set<Integer> sectionStartIndexSet) {
        if (sectionListAdapter != null) {
            if (currencyList == null) {
                currencyList = new ArrayList<>();
            }
            if (sectionStartIndexSet == null) {
                sectionStartIndexSet = new HashSet<>();
            }
            sectionListAdapter.setData(currencyList);
            sectionListAdapter.setSectionStartIndexSet(sectionStartIndexSet);
            sectionListAdapter.notifyDataSetChanged();
        }
    }

}



