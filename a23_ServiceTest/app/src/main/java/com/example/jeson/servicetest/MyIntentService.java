package com.example.jeson.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Jeson on 2016/4/19.
 * 对于忘记开启线程或者忘记调用stopSelf方法的程序员
 * 为了可以简单地创建一个异步的、会自动停止的服务  Android提供了一个IntentService类
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");   //调用父类的有参构造函数
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //打印当前线程的id
        Log.d("MyIntentService", "Thread id is " + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyIntentService", "onDestroy executed");
    }
}
