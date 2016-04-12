package com.example.jason.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Jason on 4/5/2016.
 * 自定义广播类
 */
public class MyBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Receiver in MyBroadcastReceiver", Toast.LENGTH_SHORT).show();
        abortBroadcast();   //将广播拦截
    }
}
