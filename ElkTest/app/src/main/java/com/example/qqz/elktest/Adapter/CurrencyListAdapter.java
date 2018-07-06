package com.example.qqz.elktest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.qqz.elktest.Currency;
import com.example.qqz.elktest.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class CurrencyListAdapter extends BaseAdapter implements StickyListHeadersAdapter,
        SectionIndexer{

    private final Context mContext;
    private String[] mCurrencyList;
    private int[] mSectionIndices;
    private Character[] mSectionLetters;
    private LayoutInflater mInflater;

    public CurrencyListAdapter(Context context) {
        mContext =context;
        mInflater = LayoutInflater.from(context);
        mCurrencyList = context.getResources().getStringArray(R.array.currency_list);
        mSectionIndices = getSectionIndices();
        mSectionLetters = getSectionLetters();
    }

    @Override
    public int getCount() {
        return mCurrencyList.length;
    }

    @Override
    public Object getItem(int position) {
        return mCurrencyList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.currencylist_item, parent, false);
            holder.text = convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(mCurrencyList[position]);

        return convertView;
    }



    class ViewHolder {
        TextView text;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;

        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.currencylist_headers, parent, false);
            holder.text = convertView.findViewById(R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }


        CharSequence headerChar = mCurrencyList[position].subSequence(0, 1);
        holder.text.setText(headerChar);

        return convertView;
    }
    class HeaderViewHolder {
        TextView text;
    }

    @Override
    public long getHeaderId(int position) {
        return mCurrencyList[position].subSequence(0, 1).charAt(0);
    }

    @Override
    public Object[] getSections() {
        return mSectionLetters;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        if (mSectionIndices.length == 0) {
            return 0;
        }

        if (sectionIndex >= mSectionIndices.length) {
            sectionIndex = mSectionIndices.length - 1;
        } else if (sectionIndex < 0) {
            sectionIndex = 0;
        }
        return mSectionIndices[sectionIndex];
    }

    @Override
    public int getSectionForPosition(int position) {
        for (int i = 0; i < mSectionIndices.length; i++) {
            if (position < mSectionIndices[i]) {
                return i - 1;
            }
        }
        return mSectionIndices.length - 1;
    }

    public int[] getSectionIndices() {
        ArrayList<Integer> sectionIndices = new ArrayList<Integer>();
        char lastFirstChar = mCurrencyList[0].charAt(0);
        sectionIndices.add(0);
        for (int i = 1; i < mCurrencyList.length; i++) {
            if (mCurrencyList[i].charAt(0) != lastFirstChar) {
                lastFirstChar = mCurrencyList[i].charAt(0);
                sectionIndices.add(i);
            }
        }
        int[] sections = new int[sectionIndices.size()];
        for (int i = 0; i < sectionIndices.size(); i++) {
            sections[i] = sectionIndices.get(i);
        }
        return sections;
    }

    public Character[] getSectionLetters() {
        Character[] letters = new Character[mSectionIndices.length];
        for (int i = 0; i < mSectionIndices.length; i++) {
            letters[i] = mCurrencyList[mSectionIndices[i]].charAt(0);
        }
        return letters;
    }

    public void clear() {
        mCurrencyList = new String[0];
        mSectionIndices = new int[0];
        mSectionLetters = new Character[0];
        notifyDataSetChanged();
    }

    public void restore() {
        mCurrencyList = mContext.getResources().getStringArray(R.array.currency_list);
        mSectionIndices = getSectionIndices();
        mSectionLetters = getSectionLetters();
        notifyDataSetChanged();
    }
  /*  public void setData(List<Currency> currencyList, Set<Integer> sectionStartIndexSet) {
        if (currencyList == null) {
            currencyList = new ArrayList<>();
        }
        if (sectionStartIndexSet == null) {
            sectionStartIndexSet = new HashSet<>();
        }
        this.currencyList = currencyList;
        this.sectionStartIndexSet = sectionStartIndexSet;
        this.notifyDataSetChanged();
    }*/

}
