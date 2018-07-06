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

public class SelectPicPopupRightWindow extends PopupWindow {
    private View mMenuRightView;
    private Button mExchange, mMore, mCancel,mCustomize;

    public SelectPicPopupRightWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuRightView = inflater.inflate(R.layout.popright, null);

        mExchange = mMenuRightView.findViewById(R.id.rightexchange);
        mMore = mMenuRightView.findViewById(R.id.changerightmore);
        mCancel = mMenuRightView.findViewById(R.id.rightcancel);
        mCustomize = mMenuRightView.findViewById(R.id.customize);


        mExchange.setOnClickListener(itemsOnClick);
        mMore.setOnClickListener(itemsOnClick);
        mCancel.setOnClickListener(itemsOnClick);
        mCustomize.setOnClickListener(itemsOnClick);

        this.setContentView(mMenuRightView);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setTouchable(true);
        this.setAnimationStyle(R.style.AnimationBottomFade);

        mMenuRightView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuRightView.findViewById(R.id.pop_right_layout).getTop();
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


