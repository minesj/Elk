package com.example.qqz.elktest.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.qqz.elktest.R;
import java.util.ArrayList;
import java.util.List;


public class FrontListAdapter extends BaseAdapter {

    public List<String> arrayList = new ArrayList<String>();
    private Context context;
    private View view;
    private Typeface typeface;
    public ViewHolder viewHolder;
    private int x;


    public FrontListAdapter(Context context) {
        this.context = context;
    }

    public FrontListAdapter(Context context, List<String> arrayList) {
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

        //final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.front_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.lefttextView.setTypeface(typeface);
        viewHolder.righttextview.setTypeface(typeface);
        viewHolder.lefttextView.setText(arrayList.get(position));
        viewHolder.righttextview.setText(arrayList.get(position));
        TranslactionX(x);
        Animation();
        return convertView;
    }

    public void setX(int x) {
        this.x = x;
    }


    private class ViewHolder {
        TextView lefttextView;
        TextView righttextview;

        public ViewHolder(View view) {
            lefttextView = view.findViewById(R.id.front_item_left_layout);
            righttextview = view.findViewById(R.id.front_item_right_layout);
        }
    }

    public void TranslactionX(int dx){
        viewHolder.lefttextView.setTranslationX(dx);
        viewHolder.righttextview.setTranslationX(dx);

    }

    public void Animation () {
        ObjectAnimator animatorleft = ObjectAnimator
                .ofFloat(viewHolder.lefttextView, "TranslationX", viewHolder.lefttextView
                        .getTranslationX(), 0);
        animatorleft.setInterpolator(new BounceInterpolator());
        ObjectAnimator animatorright = ObjectAnimator
                .ofFloat(viewHolder.righttextview, "TranslationX", viewHolder.righttextview
                        .getTranslationX(), 0);
        animatorright.setInterpolator(new BounceInterpolator());
        animatorright.setDuration(500);
        animatorright.start();
        animatorleft.setDuration(500);
        animatorleft.start();
    }


}
