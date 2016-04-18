package com.example.jeson.androidthreadtest;

import android.os.AsyncTask;

/**
 * Created by Jeson on 2016/4/18.
 * AsyncTask 是Android为在子线程中对UI进行操作，提供的另一个工具
 *      AsyncTask 是一个抽象类，如需使用，必须要创建一个子类去继承它
 *          该类指定了三个泛型参数：1、params    在执行AsyncTask时需要传入的参数，可用于在后台任务中使用
 *
 */
public class DownloadTask extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] params) {
            return null;
    }
}
