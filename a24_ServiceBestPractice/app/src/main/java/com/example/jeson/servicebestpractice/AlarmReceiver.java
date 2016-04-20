package com.example.jeson.servicebestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Jeson on 2016/4/20.
 *  Alarm服务类
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, LongRunningService.class);
        context.startService(intent1);
    }
}
