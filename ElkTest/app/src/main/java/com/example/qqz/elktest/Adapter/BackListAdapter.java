package com.example.qqz.elktest.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qqz.elktest.R;

import java.util.ArrayList;
import java.util.List;

public class BackListAdapter  extends BaseAdapter{
    public List<String> arrayList = new ArrayList<String>();
    private Context context;
    private Typeface typeface;
    public ViewHolder viewHolder;

    public BackListAdapter(Context context) {
        this.context = context;
    }

    public BackListAdapter(Context context, List<String> arrayList) {
        this.context = context;
        this.arrayList = new ArrayList<String>(arrayList);
        this.typeface = Typeface.createFromAsset(context.getAssets(), "DIN-Regular.otf");
    }

    public void setData(List<String> array) {
        if (array == null) {
            return;
        }
        arrayList = new ArrayList<String>(array);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         //BackListAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.back_list_item, parent, false);
            viewHolder = new BackListAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BackListAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.leftTextView.setText(arrayList.get(position));
        viewHolder.rightTextView.setText(arrayList.get(position));
        viewHolder.leftTextView.setTypeface(typeface);
        viewHolder.rightTextView.setTypeface(typeface);
        return convertView;
    }


    private class ViewHolder {
        TextView leftTextView;
        TextView rightTextView;

        public ViewHolder(View view) {
            leftTextView = view.findViewById(R.id.back_item_left_layout);
            rightTextView =view.findViewById(R.id.back_item_right_layout);
        }
    }


}
