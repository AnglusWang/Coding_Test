package com.example.angluswang.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jeson on 2016/6/12.
 * 自定义适配器器
 */

public class MyAdapter extends BaseAdapter {

    private final List<PhoneInfo> contactList;
    private final Context context;

    public MyAdapter(List<PhoneInfo> contactList, Context context) {
        this.contactList = contactList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        mLayout = (LinearLayout) inflater.inflate(R.layout.item_lvcontact, null);
//        TextView phoneName = (TextView) mLayout.findViewById(R.id.tv_contact_name);
//        TextView phoneNumber = (TextView) mLayout.findViewById(R.id.tv_contact_number);
//        phoneName.setText(mList.get(position).getName());
//        phoneNumber.setText(mList.get(position).getNumber());

        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lvcontact, null);
            holder = new ViewHolder();
            holder.phoneName = (TextView) convertView.findViewById(R.id.tv_contact_name);
            holder.phoneNumber = (TextView) convertView.findViewById(R.id.tv_contact_number);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        PhoneInfo contact = contactList.get(position);
        holder.phoneName.setText(contact.getName());
        holder.phoneNumber.setText(contact.getNumber());
        return convertView;
    }

    private static class ViewHolder {
        TextView phoneName;
        TextView phoneNumber;
    }
}
