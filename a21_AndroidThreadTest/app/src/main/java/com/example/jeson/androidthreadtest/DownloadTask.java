package com.example.jeson.androidthreadtest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Jeson on 2016/4/18.
 * AsyncTask 是Android为在子线程中对UI进行操作，提供的另一个工具
 *      AsyncTask 是一个抽象类，如需使用，必须要创建一个子类去继承它
 *          该类指定了三个泛型参数：1、params    在执行AsyncTask时需要传入的参数，可用于在后台任务中使用
 *                             2、Progress 后台任务执行时，如果需要在界面上显示当前的进度，则使用这里指定的泛型作为进度单位
 *                             3、Result  当任务执行完毕后，如果需要对结果进行返回，则使用这里指定的泛型作为返回值类型
 *         例如：
 *             class DownloadTask extends AsyncTask<Void, Integer, Boolean> {
 *                 ......
 *             }
 *     经常需要重写的方法有：
 *          1、onPreExecute()
 *              这个方法会在后台任务开始执行之前调用，用于进行一些界面上的初始化操作，比如显示一个进度条对话框等
 *          2、DoInBackground(Params...)
 *              这个方法中的所有代码都会在子线程中运行，可以在这里处理一些耗时的任务
 *                  但是不能再这里进行UI操作（子线程中不能进行UI操作）
 *          3、onProgressUpdate(Progress...)
 *              当在后台任务中调用了publishProgress(Progress...)方法后，这个方法很快就会被调用
 *                  可以对UI进行操作，利用参数中的数值就可以对界面元素进行相应地更新
 *          4、onPostExecute(Result)
 *              当后台任务执行完毕并通过return语句进行返回时，这个方法就很快会被调用
 *                  可以进行一些UI操作，比如：提心任务执行的结果、以及关闭掉进度条对话框等
 */
public class DownloadTask extends AsyncTask<Void, Integer, Boolean> {

    //模拟而已
    private ProgressDialog progressDialog = new ProgressDialog(null);

    @Override
    protected void onPreExecute() {
        progressDialog.show();  //显示进度对话框
    }

    @Override
    protected void onPostExecute(Boolean result) {
        progressDialog.dismiss();   //关闭进度对话框
        //在这里提示下载结果
        if (result) {
            Toast.makeText(Context, "Download succeeded", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(Context, "Download failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //在这里更新下载进度
        progressDialog.setMessage("Downloaded" + values[0] + "%");
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            while (true) {
                int downloadPercent = doDownload();
                publishProgress(downloadPercent);
                if (downloadPercent >= 100) {
                    break;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private int doDownload() {
        //这是一个演示方法
        return 0;
    }
}
