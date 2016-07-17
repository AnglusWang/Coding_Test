package com.angluswang.festival_sms.activity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.angluswang.festival_sms.R;
import com.angluswang.festival_sms.bean.Festival;
import com.angluswang.festival_sms.bean.FestivalLab;
import com.angluswang.festival_sms.bean.Msg;
import com.angluswang.festival_sms.biz.SmsBiz;
import com.angluswang.festival_sms.view.FlowLayout;

import java.util.HashSet;

public class SendMessageActivity extends AppCompatActivity {

    private static final String KEY_FESTIVAL_ID = "festival";
    private static final String KEY_MSG_ID = "msgId";
    private static final int CODE_REQUEST = 1;

    private int mFestivalId;
    private int mMsgId;

    private Festival mFestival;
    private Msg mMsg;

    private EditText etMsg;
    private Button btnAdd;
    private FlowLayout flContacts;
    private FloatingActionButton fabSend;
    private View layoutLoading;

    private HashSet<String> mContactsNames = new HashSet<>();
    private HashSet<String> mContactsNums = new HashSet<>();

    private LayoutInflater mInflater;

    private static final String ACTION_SEND_MSG = "ACTION_SEND_MSG";
    private static final String ACTION_DELIVER_MSG = "ACTION_DELIVER_MSG";

    private PendingIntent mSendPi;
    private PendingIntent mDeliverPi;
    private BroadcastReceiver mSendBroadcastReceiver;
    private BroadcastReceiver mDeliverBroadcastReceiver;

    private SmsBiz mSmsBiz = new SmsBiz();
    private int mMsgSendCount;  //计算发送成功多少条短信
    private int mTotalCount;    //记录总的短信数目

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        initDatas();
        initViews();
        initEvent();
        initReceiver();

        mInflater = LayoutInflater.from(this);
    }

    private void initReceiver() {
        Intent sendIntent = new Intent(ACTION_SEND_MSG);
        mSendPi = PendingIntent.getBroadcast(this, 0, sendIntent, 0);
        Intent deliverIntent = new Intent(ACTION_DELIVER_MSG);
        mDeliverPi = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);

        registerReceiver(mSendBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getResultCode() == RESULT_OK) {
                    mMsgSendCount++;
                    Log.e("TAG", "短信发送成功~" + (mMsgSendCount + "/" + mTotalCount));
                } else {
                    Log.e("TAG", "短信发送失败~");
                }
                Toast.makeText(SendMessageActivity.this, (mMsgSendCount + "/" + mTotalCount) + "短信发送成功~",
                        Toast.LENGTH_SHORT).show();
                if (mMsgSendCount == mTotalCount) {
                    finish();
                }

            }
        }, new IntentFilter(ACTION_SEND_MSG));

        registerReceiver(mDeliverBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("TAG", "联系人已经成功收到短信~");
            }
        }, new IntentFilter(ACTION_DELIVER_MSG));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 防止造成内存泄漏
        unregisterReceiver(mSendBroadcastReceiver);
        unregisterReceiver(mDeliverBroadcastReceiver);
    }

    private void initEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, CODE_REQUEST);
            }
        });

        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContactsNums.size() == 0) {
                    Toast.makeText(SendMessageActivity.this, "请先选择联系人~",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String msg = etMsg.getText().toString();
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(SendMessageActivity.this, "短信内容不能为空~",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                mTotalCount = mSmsBiz.sendMsg(mContactsNums, msg, mSendPi, mDeliverPi); //发生短信并计数
                mMsgSendCount = 0; //
            }
        });
    }

    private void initViews() {
        etMsg = (EditText) findViewById(R.id.id_et_content);
        btnAdd = (Button) findViewById(R.id.id_btn_add);
        flContacts = (FlowLayout) findViewById(R.id.id_fl_contacts);
        fabSend = (FloatingActionButton) findViewById(R.id.id_fab_send);
        layoutLoading = findViewById(R.id.id_layout_loading);

        layoutLoading.setVisibility(View.GONE);

        if (mMsgId != -1) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
                cursor.moveToNext();
                String contactName = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                mContactsNames.add(contactName);

                String contactNum = getContactNumber(cursor);
                if (!TextUtils.isEmpty(contactNum)) {
                    mContactsNums.add(contactNum);
                    mContactsNames.add(contactName);

                    addTag(contactName, contactNum);
                }
            }
        }
    }

    private void addTag(String contactName, String contactNumber) {
        TextView view = (TextView) mInflater.inflate(R.layout.tag, flContacts, false);
        view.setText(contactName + ":" + contactNumber + ";");
        flContacts.addView(view);
    }

    /**
     * 获取联系人电话号码
     *
     * @param cursor
     * @return
     */
    private String getContactNumber(Cursor cursor) {
        int numberCount = cursor.getInt(cursor.getColumnIndex(
                ContactsContract.Contacts.HAS_PHONE_NUMBER));
        String number = null;

        if (numberCount > 0) {
            int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phoneCursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
            phoneCursor.moveToFirst();
            number = phoneCursor.getString(phoneCursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneCursor.close();
        }
        cursor.close();
        return number;
    }

    public static void toActivity(Context context, int festivalId, int msgId) {
        Intent intent = new Intent(context, SendMessageActivity.class);
        intent.putExtra(KEY_FESTIVAL_ID, festivalId);
        intent.putExtra(KEY_MSG_ID, msgId);
        context.startActivity(intent);
    }

}
