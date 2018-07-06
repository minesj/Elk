package com.example.qqz.elktest;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;

import com.example.qqz.elktest.Adapter.CurrencyListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class CurrencyListLeftActivity extends AppCompatActivity implements AdapterView
        .OnItemClickListener, StickyListHeadersListView.OnHeaderClickListener,
        StickyListHeadersListView.OnStickyHeaderChangedListener, StickyListHeadersListView
                .OnStickyHeaderOffsetChangedListener {

    public CurrencyListAdapter currencyListAdapter;
    public  StickyListHeadersListView stickyList;
    public SwipeRefreshLayout refreshLayout;
    public boolean fadeHeader = true;
    public ImageButton ListBack;
  /*  protected CurrencyListData fiatCurrencyListData = new CurrencyListData();
    public static final char INDEX_FAV = '#';
    public static final char INDEX_LOCAL = '*';*/



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currencylist);

        refreshLayout =  findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });


        currencyListAdapter = new CurrencyListAdapter(this);
        stickyList = findViewById(R.id.list);
        stickyList.setOnItemClickListener(this);
        stickyList.setOnHeaderClickListener(this);
        stickyList.setOnStickyHeaderChangedListener(this);
        stickyList.setOnStickyHeaderOffsetChangedListener(this);
        stickyList.addHeaderView(getLayoutInflater().inflate(R.layout.currency_list_header, null));
        stickyList.setEmptyView(findViewById(R.id.empty));
        stickyList.setDrawingListUnderStickyHeader(true);
        stickyList.setAreHeadersSticky(true);
        stickyList.setAdapter(currencyListAdapter);

        ListBack = findViewById(R.id.list_back);
        ListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       // new LoadCurrencyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int resultCode =1;
                String LeftText = stickyList.getItemAtPosition(position).toString();
                Intent data = new Intent();
                data.putExtra("LeftTextView", LeftText);
                setResult(resultCode, data);
                finish();
    }

    @Override
    public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long
            headerId, boolean currentlySticky) {

    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onStickyHeaderChanged(StickyListHeadersListView l, View header, int itemPosition,
                                      long headerId) {
        header.setAlpha(1);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onStickyHeaderOffsetChanged(StickyListHeadersListView l, View header, int offset) {

        if (fadeHeader && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            header.setAlpha(1 - (offset / (float) header.getMeasuredHeight()));
        }
    }
    /*private static class LoadCurrencyData extends CurrencyListData {
        String currencyType;
        Currency localCurrency = null;
        List<Currency> commonCurrencies = new ArrayList<>();
        List<Currency> allCurrencies = new ArrayList<>();

        LoadCurrencyData(String currencyType) {
            this.currencyType = currencyType;
        }
    }
    private static class CurrencyListData {
        List<Currency> currencyList = new ArrayList<>();
        Set<Integer> sectionStartIndexSet = new HashSet<>();
        HashMap<Character, Integer> sectionIndexMap = new HashMap<>();
        String index = "";
    }*/

   /* private class LoadCurrencyTask extends AsyncTask<Void, String, Void> {
        private Context context = CurrencyListLeftActivity.this;
        private Currency localCurrency;
        private List<Currency> commonCurrencies;
        private ArrayList<java.util.Currency> allCurrencies;
        private LoadCurrencyData fiatCurrencyLoadData;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            CurrencyManager currencyManager = CurrencyManager.getInstance();
            allCurrencies = currencyManager.currencies();

            //BaseApplication application = (BaseApplication) getApplication();
           *//* boolean isLocationEnabled = ShareDataHelper
                    .getBooleanValue(context, ShareDataHelper.SHARE_LOCATION_ENABLE);
            if (AppHelper.isLocationOpen(context) &&
                    !TextUtils.isEmpty(application.countryCode) && isLocationEnabled) {
                localCurrency = currencyManager.findCurrencyByCountryCode(application.countryCode);
            }*//*
            fiatCurrencyLoadData = new LoadCurrencyData(Currency.TYPE_FIAT_CURRENCY);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            int fiatCommCurrNumber = 0;
            int cryptoCommCurrNumber = 0;
            for (int i = 0, count = commonCurrencies.size(); i < count; i++) {
                Currency currency = commonCurrencies.get(i);
                currency.setSection(Currency.SECTION_FAV);
                if (currency.isFiatCurrency()) {
                    if(fiatCommCurrNumber < 10){
                        fiatCurrencyLoadData.commonCurrencies.add(currency);
                    }
                    fiatCommCurrNumber++;
                }*//* else {
                    if(cryptoCommCurrNumber < 10){
                        cryptoCurrencyLoadData.commonCurrencies.add(currency);
                    }
                    cryptoCommCurrNumber++;
                }*//*
            }
            if (localCurrency != null) {
                localCurrency.setSection(Currency.SECTION_LOCAL);
                if (localCurrency.isFiatCurrency()) {
                    fiatCurrencyLoadData.localCurrency = localCurrency;
                } *//*else {
                    cryptoCurrencyLoadData.localCurrency = localCurrency;
                }*//*
            }
*//*
            Collections.sort(allCurrencies, new Comparator<Currency>() {
                @Override
                public int compare(Currency a, Currency b) {
                    return a.getSection(context).compareTo(b.getSection(context));
                }
            });
*//*
            for (int i = 0, count = allCurrencies.size(); i < count; i++) {
                Currency currency = allCurrencies.get(i);
                if (currency.isFiatCurrency()) {
                    fiatCurrencyLoadData.allCurrencies.add(currency);
                } else {
                    cryptoCurrencyLoadData.allCurrencies.add(currency);
                }
            }
            processCurrencyData(fiatCurrencyLoadData);

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
            showFiatCurrencyList();
            }
        }

        private void processCurrencyData(LoadCurrencyData data) {
            int sectionStartIndex = 0;
            StringBuilder indexBuilder = new StringBuilder(32);
            if (data.commonCurrencies.size() > 0) {
                indexBuilder.append(INDEX_FAV);
                data.currencyList.addAll(data.commonCurrencies);
                data.sectionStartIndexSet.add(sectionStartIndex);
                data.sectionIndexMap.put(INDEX_FAV, sectionStartIndex);
                sectionStartIndex += data.commonCurrencies.size();
                publishProgress(data.currencyType);
            }
            if (data.localCurrency != null) {
                indexBuilder.append(INDEX_LOCAL);
                data.currencyList.add(data.localCurrency);
                data.sectionStartIndexSet.add(sectionStartIndex);
                data.sectionIndexMap.put(INDEX_LOCAL, sectionStartIndex);
                sectionStartIndex += 1;
            }
            Set<String> sectionSet = new HashSet<>();
            for (int i = 0, count = data.allCurrencies.size(); i < count; i++) {
                Currency c = data.allCurrencies.get(i);
                String section = c.getSection(context);
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
    private void showFiatCurrencyList() {
        stickyList.setOnScrollListener(null);
        if (fiatCurrencyListData.currencyList.size() > 0) {
            if (currencyListAdapter != null) {
                currencyListAdapter.setData(currencyList, sectionStartIndexSet);
            }
            currencyListFragment.setData(fiatCurrencyListData.currencyList,
                    fiatCurrencyListData.sectionStartIndexSet);
        }
       *//* if (!TextUtils.isEmpty(fiatCurrencyListData.index)) {
            indexView.setIndex(fiatCurrencyListData.index);
        }*//*
        stickyList.setSelectionFromTop(0, 0);
        //currencySectionListFragment.getSectionListView().setSelectionFromTop(0, 0);
        final AbsListView.OnScrollListener l = (AbsListView.OnScrollListener) this;
    }*/

}


