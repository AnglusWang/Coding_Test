package com.angluswang.progressbar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.angluswang.view.HorizontalProgressBar;

public class MainActivity extends Activity {

    public static final int MSG_UPDATE = 0x110;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int progerss = mHProgressBar.getProgress();
            mHProgressBar.setProgress(++progerss);
            if (progerss > 100) {
                mHandler.removeMessages(MSG_UPDATE);
            }
            mHandler.sendEmptyMessageDelayed(MSG_UPDATE, 100);
        }
    };


    private HorizontalProgressBar mHProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHProgressBar = (HorizontalProgressBar) findViewById(R.id.id_progress01);
        mHandler.sendEmptyMessage(MSG_UPDATE);
    }
}
