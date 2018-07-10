package com.example.qqz.elktest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by tsangzacks on 14/12/25.
 */
public abstract class ZTAdapter <T> extends BaseAdapter {

    public List<T> data;

    public Context context;

    public LayoutInflater mInflater;


    public ZTAdapter(Context context, List<T> data) {
        // TODO Auto-generated constructor stub
        this.data=data;
        this.context=context;
        mInflater=LayoutInflater.from(context);
    }

    public void setData(List<T> data) {
        this.data=data;
        if (data!=null){
            notifyDataSetChanged();
        }
    }
    public List<T> getData() {
        return data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (data==null){
            return 0;
        }
        return data.size();
    }

    @Override
    public T getItem(int position) {
        // TODO Auto-generated method stub
        if (data==null){
            return null;
        }
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public abstract View getView(int position, View convertView, ViewGroup parent);

    public void destroy(){
        data.clear();
        data=null;
        mInflater=null;
        context=null;
    }


}


