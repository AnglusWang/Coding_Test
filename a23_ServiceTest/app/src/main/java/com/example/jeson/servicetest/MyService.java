package com.example.jeson.servicetest;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Jeson on 2016/4/19.
 * 服务类
 * 调用自身的stopSelf方法就能让这个服务停止下来了
 *
 * 服务的生命周期：
 *      1、当调用了startService后，又去调用stopService方法，服务的onDestroy方法就会执行
 *      2、当调用了bindService后，又去调用unbindService方法，服务的onDestroy方法也会执行
 *      3、如果既调用了starService又调用了bindService， 则需同时调用stopService方法和unbindService方法，服务的onDestroy方法也会执行
 */
public class MyService extends Service {

    /**
     *  希望在MyService中提供一个下载功能，然后在活动中可以决定何时开启下载以及随时查看下载进度
     *      实现思路： 创建一个专门的Binder对象来对下载功能进行管理
     */
    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {

        public void startDownload() {
            Log.d("Myservice", "startDownload executed");
        }

        public int getProgress() {
            Log.d("MyService", "getProgress executed");
            return 0;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStartCommand executed");

        /**
         * 由于服务中的代码都是默认运行在线程当中的，如果直接在服务里去处理一些耗时的逻辑，
         *      就很容易出现ANR（Application Not Responding）
         *  应该在服务的每个具体的方法里开启一个线程，然后在这里去处理那些耗时的逻辑
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                //处理具体的逻辑 如：
                stopSelf(); //让一个服务在执行完毕后自动停止的功能
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        //创建一个通知栏活动
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.photo1)
                        .setContentTitle("This is content title")
                        .setContentText("This is content text");
        Notification notification =  mBuilder.build();
        super.onCreate();startForeground(1,notification);
        Log.d("MyService", "onCreate executed");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy executed");
    }
}
