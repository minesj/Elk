package com.example.qqz.elktest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qqz.elktest.R;
import com.example.qqz.elktest.currencylist.Currency;
import com.example.qqz.elktest.helper.LanguageHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class CurrencyListAdapter2 extends ZTAdapter<Currency>{
    private static final int CURRENCY_NAME_TEXT_COLOR = Color.parseColor("#ffffff");
    private static final int CURRENCY_SYMBOL_TEXT_COLOR = Color.parseColor("#ffffff");

    private String language;
    private Typeface typeface;
    private String currentSelectedCurrency ="";
    private List<String> selectedCurrencies = new ArrayList<>();
    private Set<Integer> sectionStartIndexSet;

    public CurrencyListAdapter2(Context context) {
        super(context, new ArrayList<Currency>());
        language = LanguageHelper.getSystemLanguage(context);
        typeface = Typeface.createFromAsset(context.getAssets(), "roboto-regular.ttf");
    }

    public void setCurrentSelectedCurrency(String currentSelectedCurrency) {
        this.currentSelectedCurrency = currentSelectedCurrency;
    }

    public void setSelectedCurrencies(List<String> selectedCurrencies) {
        this.selectedCurrencies = selectedCurrencies;
    }

    public void setSectionStartIndexSet(Set<Integer> sectionStartIndexSet) {
        this.sectionStartIndexSet = sectionStartIndexSet;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.currencylist_item2, parent, false);
            holder = new ViewHolder();
            holder.dividerLayout = convertView.findViewById(R.id.divider_layout);
            holder.dividerView = convertView.findViewById(R.id.divider_view);
            holder.currencyLayout = convertView.findViewById(R.id.currency_layout);
            holder.currencyIcon = convertView.findViewById(R.id.currency_icon);
            holder.currencyNameText = convertView.findViewById(R.id.currency_name_text);
            holder.currencySymbolText = convertView.findViewById(R.id.currency_symbol_text);
            holder.checkedImage = convertView.findViewById(R.id.checked_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        Currency currency = getItem(position);
        holder.currencyIcon.setImageDrawable(currency.getFlagDrawable(context));
        holder.currencyNameText.setText(currency.getName(language));
        holder.currencySymbolText.setText(currency.getSymbol());
        String symbol = currency.getSymbol();
        if (currentSelectedCurrency.equals(symbol)) {
            holder.checkedImage.setVisibility(View.VISIBLE);
            Drawable check = ContextCompat.getDrawable(context, R.drawable.currency_selected_mark);
            Drawable drawableCheck = DrawableCompat.wrap(check);
            holder.checkedImage.setImageDrawable(drawableCheck);
        } else if (selectedCurrencies.contains(symbol)) {
            holder.currencyNameText.setTextColor(CURRENCY_NAME_TEXT_COLOR);
            holder.currencySymbolText.setTextColor(CURRENCY_SYMBOL_TEXT_COLOR);
            holder.checkedImage.setVisibility(View.VISIBLE);
            holder.checkedImage.setImageResource(R.drawable.currency_selected_mark);
        } else {
            holder.currencyNameText.setTextColor(CURRENCY_NAME_TEXT_COLOR);
            holder.currencySymbolText.setTextColor(CURRENCY_SYMBOL_TEXT_COLOR);
            holder.checkedImage.setVisibility(View.GONE);
        }
        if (sectionStartIndexSet.contains(position + 1)) {
            holder.dividerLayout.setVisibility(View.VISIBLE);
        } else {
            holder.dividerLayout.setVisibility(View.GONE);
        }
        return convertView;
    }


    private static final class ViewHolder {
        private RelativeLayout dividerLayout;
        private View dividerView;
        private RelativeLayout currencyLayout;
        private ImageView currencyIcon;
        private TextView currencyNameText;
        private TextView currencySymbolText;
        private ImageView checkedImage;
    }
}

