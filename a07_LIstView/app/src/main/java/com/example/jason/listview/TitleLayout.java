package com.example.jason.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jason on 4/4/2016.
 * 自定义标题栏控件
 */
public class TitleLayout extends LinearLayout {

    public TitleLayout (Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.mytitle, this);

        Button titleBack = (Button) findViewById(R.id.title_back);
        Button titleEdit = (Button) findViewById(R.id.title_eidt);
        TextView titleName = (TextView) findViewById(R.id.title_name);

        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "点了也没用", Toast.LENGTH_SHORT).show();
            }
        });

        titleEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "还没定义呢", Toast.LENGTH_SHORT).show();
            }
        });

        titleName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "标题也能点？", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
