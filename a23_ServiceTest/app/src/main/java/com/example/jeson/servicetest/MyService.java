package com.example.jeson.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Jeson on 2016/4/19.
 * 服务类
 * 调用自身的stopSelf方法就能让这个服务停止下来了
 */
public class MyService extends Service {

    /**
     *  希望在MyService中提供一个下载功能，然后在活动中可以决定何时开启下载以及随时查看下载进度
     *      实现思路： 创建一个专门的Binder对象来对下载功能进行管理
     *
     *  下面是代码实现
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
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "onCreate executed");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy executed");
    }
}
