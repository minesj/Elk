package com.example.qqz.elktest;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

public class CurrencyListRightActivity extends CurrencyListLeftActivity {


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int resultCode =2;
        String RightText = stickyList.getItemAtPosition(position).toString();
        Intent data = new Intent();
        data.putExtra("RightTextView", RightText);
        setResult(resultCode, data);
        finish();
    }
}
