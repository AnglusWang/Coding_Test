package com.example.jason.broadcastpractice;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Jason on 4/6/2016.
 * 所有活动的父类
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
