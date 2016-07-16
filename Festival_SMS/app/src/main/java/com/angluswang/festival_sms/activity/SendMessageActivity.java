package com.angluswang.festival_sms.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.angluswang.festival_sms.R;
import com.angluswang.festival_sms.bean.Festival;
import com.angluswang.festival_sms.bean.FestivalLab;
import com.angluswang.festival_sms.bean.Msg;
import com.angluswang.festival_sms.view.FlowLayout;

public class SendMessageActivity extends Activity {

    private static final String KEY_FESTIVAL_ID = "festival";
    private static final String KEY_MSG_ID = "msgId";

    private int mFestivalId;
    private int mMsgId;

    private Festival mFestival;
    private Msg mMsg;

    private EditText etMsg;
    private Button btnAdd;
    private FlowLayout flContacts;
    private FloatingActionButton fabSend;
    private View layoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        initDatas();
        initViews();
    }

    private void initViews() {
        etMsg = (EditText) findViewById(R.id.id_et_content);
        btnAdd = (Button) findViewById(R.id.id_btn_add);
        flContacts = (FlowLayout) findViewById(R.id.id_fl_contacts);
        fabSend = (FloatingActionButton) findViewById(R.id.id_fab_send);
        layoutLoading = findViewById(R.id.id_layout_loading);

        layoutLoading.setVisibility(View.GONE);

        if (mMsgId == -1) {
            mMsg = FestivalLab.getInstance().getMsgByMsgId(mMsgId);
            etMsg.setText(mMsg.getContent());
        }

    }

    private void initDatas() {
        mFestivalId = getIntent().getIntExtra(KEY_FESTIVAL_ID, -1);
        mMsgId = getIntent().getIntExtra(KEY_MSG_ID, -1);

        mFestival = FestivalLab.getInstance().getFestivalById(mFestivalId);
        setTitle(mFestival.getName());
    }

    public static void toActivity(Context context, int festivalId, int msgId) {
        Intent intent = new Intent(context, SendMessageActivity.class);
        intent.putExtra(KEY_FESTIVAL_ID, festivalId);
        intent.putExtra(KEY_MSG_ID, msgId);
        context.startActivity(intent);
    }

}
