package com.example.jason.broadcastpractice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button forceOffline = (Button) findViewById(R.id.force_offline);
        forceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.jason.broadcastpractice.FORCE_OFFLINE");
                sendBroadcast(intent);
                Toast.makeText(MainActivity.this, "发送下线广播成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
