package com.example.jeson.servicebestpractice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.Date;

/**
 * Created by Jeson on 2016/4/20.
 *
 * 后台执行的定时任务
 *  Android中的定时任务有两种实现方式：
 *      一、使用Java API里提供的Timer类
 *      二、使用Android的Alarm机制
 *   区别：CUP进入到睡眠状态时，Timer中的定时任务无法正常运行，而Alarm机制则不存在这种情况，它具有唤醒CUP的功能。
 *
 * Alarm机制：
 *      主要就是借助了AlarmManager类来实现
 *          该类和NotificationManager类有些类似
 *      1、获取AlarmManager实例
 *          AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
 *      2、利用set方法设置一个定时任务
 *          如：设置一个任务在10秒后执行
 *              long triggerAtTime = SystemClock.elapsedRealtime() + 10*1000;
 *              manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
 *      set参数分析：
 *          1、是一个整型参数，用于指定AlarmManager的工作类型，有四种值可选
 *             ELAPSED_REALTIME、ELAPSED_REALTIME_WAKEUP
 *                  表示让定时任务触发时间从系统开机开始算起，后者可以唤醒CUP
 *             RTC、RTC_WAKEUP
 *                  表示让定时任务触发时间从1970年1月1日0点开始算起，后者可以唤醒CUP
 *          2、定时任务触发的时间，以毫秒为单位
 *          3、是一个pendingIntent
 *                  一般会调用getBroadcast()方法来获取一个能够执行广播的PendingIntent
 */
public class LongRunningService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("LongRunningService", "executed at " + new Date().toString());
            }
        }).start();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 60 * 60 * 1000;    //一小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent intent1 = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 0, intent1, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent1);

        return super.onStartCommand(intent, flags, startId);
    }
}
