package com.example.jason.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Jason on 4/5/2016.
 * 发送有序广播练习
 */
public class AnotherBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Received in AnotherBroadcastReceiver", Toast.LENGTH_SHORT).show();
    }
}
