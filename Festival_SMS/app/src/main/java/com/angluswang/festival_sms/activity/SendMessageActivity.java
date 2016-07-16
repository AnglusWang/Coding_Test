package com.angluswang.festival_sms.activity;

import android.os.Bundle;
import android.app.Activity;

import com.angluswang.festival_sms.R;

public class SendMessageActivity extends Activity {

    private int mFestivalId;
    private int mMsgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
    }

}
