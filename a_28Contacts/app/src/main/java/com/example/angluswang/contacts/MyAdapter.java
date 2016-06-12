package com.example.angluswang.contacts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Jeson on 2016/6/12.
 * 自定义适配器器
 */

public class MyAdapter extends BaseAdapter {

    private List<PhoneInfo> mList;
    private Context mContext;

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

        return null;
    }
}
