package com.example.jason.pref_bbestpractice;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Jeson on 2016/4/15.
 * 自定义一个类作为所有活动的父类，方便管理
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
