package com.example.qqz.elktest.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qqz.elktest.R;
import com.example.qqz.elktest.currencylist.Currency;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class CurrencyListAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private static final String TAG = "SectionListAdapter";

    private Context context;
    private LayoutInflater inflater;
    private List<Currency> currencyList = new ArrayList<>();
    private Set<Integer> sectionStartIndexSet = new HashSet<>();
    private Typeface headerTypeface;

    public CurrencyListAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.headerTypeface = Typeface.createFromAsset(context.getAssets(), "roboto-bold.ttf");
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, String.format("getHeaderView, position: %d", position));
        View origConvertView = convertView;
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.currency_section_list_item, parent, false);
            holder = new ViewHolder();
            holder.dividerPlaceholderLayout =
                    convertView.findViewById(R.id.divider_placeholder_layout);
            holder.headerLayout = convertView.findViewById(R.id.header_layout);
            holder.headerText = convertView.findViewById(R.id.header_text);
            holder.headerText.setTypeface(headerTypeface);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (sectionStartIndexSet.contains(position)) {
            holder.dividerPlaceholderLayout.setVisibility(View.VISIBLE);
            holder.headerLayout.setVisibility(View.VISIBLE);
        } else if (origConvertView == null) {
            holder.dividerPlaceholderLayout.setVisibility(View.VISIBLE);
            holder.headerLayout.setVisibility(View.GONE);
        } else {
            holder.dividerPlaceholderLayout.setVisibility(View.VISIBLE);
            holder.headerLayout.setVisibility(View.VISIBLE);
        }
        String section = currencyList.get(position).getSection(context);
        if (section.equals(Currency.SECTION_FAV)) {
            holder.headerText.setVisibility(View.VISIBLE);
            holder.headerText.setText("常用");
        } else {
            holder.headerText.setVisibility(View.VISIBLE);
            holder.headerText.setText(section.toUpperCase().charAt(0) + "");
        }
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        Currency currency = currencyList.get(position);
        return currency.getSection(context).charAt(0);
    }

    @Override
    public int getCount() {
        return currencyList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.currency_section_list_item, parent, false);
            holder = new ViewHolder();
            holder.dividerPlaceholderLayout =
                    convertView.findViewById(R.id.divider_placeholder_layout);
            holder.headerLayout = convertView.findViewById(R.id.header_layout);
            holder.headerText = convertView.findViewById(R.id.header_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Currency currency = (Currency) getItem(position);
        holder.headerText.setText(currencyList.get(position).getName(currency.getSymbol()));
        if (sectionStartIndexSet.contains(position)) {
            holder.headerLayout.setVisibility(View.GONE);
            holder.dividerPlaceholderLayout.setVisibility(View.GONE);
        } else {
            holder.dividerPlaceholderLayout.setVisibility(View.GONE);
            holder.headerLayout.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public void setData(List<Currency> currencyList, Set<Integer> sectionStartIndexSet) {
        if (currencyList == null) {
            currencyList = new ArrayList<>();
        }
        if (sectionStartIndexSet == null) {
            sectionStartIndexSet = new HashSet<>();
        }
        this.currencyList = currencyList;
        this.sectionStartIndexSet = sectionStartIndexSet;
        this.notifyDataSetChanged();
    }


    private static final class ViewHolder {
        private RelativeLayout dividerPlaceholderLayout;
        private RelativeLayout headerLayout;
        private TextView headerText;
    }
}