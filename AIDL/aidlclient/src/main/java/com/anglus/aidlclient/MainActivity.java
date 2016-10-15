package com.anglus.aidlclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.anglus.aidl.IMyAidlInterface;

public class MainActivity extends Activity {

    private EditText etNum1, etNum2, etSum;
    private Button btnAdd;

    private IMyAidlInterface iMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        bindService();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num1 = Integer.parseInt(etNum1.getText().toString());
                int num2 = Integer.parseInt(etNum2.getText().toString());
                try {
                    int add = iMyAidlInterface.add(num1, num2);
                    etSum.setText(add + "");
                } catch (RemoteException e) {
                    e.printStackTrace();
                    etSum.setText("发生错误了~");
                }
            }
        });
    }

    private void bindService() {
        // 绑定到服务
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.anglus.aidl",
                "com.anglus.aidl.IRemoteService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 拿到远程服务
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 回收资源
            iMyAidlInterface = null;
        }
    };

    private void initView() {
        etNum1 = (EditText) findViewById(R.id.et_num1);
        etNum2 = (EditText) findViewById(R.id.et_num2);
        etSum = (EditText) findViewById(R.id.et_sum);
        btnAdd = (Button) findViewById(R.id.btn_add);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
