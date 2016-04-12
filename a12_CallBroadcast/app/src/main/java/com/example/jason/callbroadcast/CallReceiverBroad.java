package com.example.jason.callbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Jason on 4/7/2016.
 * 电话广播接收类
 */
public class CallReceiverBroad extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "拨打电话了", Toast.LENGTH_SHORT).show();
    }
}
