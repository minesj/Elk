package com.example.qqz.elktest.View;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.qqz.elktest.R;

public class SelectPicPopupLeftWindow extends PopupWindow {
    private View mMenuLeftView;
    private Button mExchange, mMore, mCancel,mCustomize;

    public SelectPicPopupLeftWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuLeftView = inflater.inflate(R.layout.popleft, null);

        mExchange = mMenuLeftView.findViewById(R.id.leftexchange);
        mMore = mMenuLeftView.findViewById(R.id.changeleftmore);
        mCancel = mMenuLeftView.findViewById(R.id.leftcancel);
        mCustomize = mMenuLeftView.findViewById(R.id.customize);


        mExchange.setOnClickListener(itemsOnClick);
        mMore.setOnClickListener(itemsOnClick);
        mCancel.setOnClickListener(itemsOnClick);
        mCustomize.setOnClickListener(itemsOnClick);

        this.setContentView(mMenuLeftView);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setTouchable(true);
        this.setAnimationStyle(R.style.AnimationBottomFade);



        mMenuLeftView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuLeftView.findViewById(R.id.pop_left_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();

                    }
                }
                return true;
            }
        });

    }


}

