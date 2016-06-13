package com.example.angluswang.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jeson on 2016/6/12.
 * 自定义适配器器
 */

public class MyAdapter extends BaseAdapter {

    private List<PhoneInfo> mList;
    private Context mContext;
    private LinearLayout mLayout;

    public MyAdapter(List<PhoneInfo> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        mLayout = (LinearLayout) inflater.inflate(R.layout.item_lvcontact, null);
        TextView phoneName = (TextView) mLayout.findViewById(R.id.tv_contact_name);
        TextView phoneNumber = (TextView) mLayout.findViewById(R.id.tv_contact_number);
        phoneName.setText(mList.get(position).getName());
        phoneNumber.setText(mList.get(position).getNumber());

        return mLayout;
    }
}
