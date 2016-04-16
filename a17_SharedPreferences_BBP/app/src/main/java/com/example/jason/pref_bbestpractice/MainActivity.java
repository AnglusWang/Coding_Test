package com.example.jason.pref_bbestpractice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity);

        Button forceOffline = (Button) findViewById(R.id.send_offlineBroadcast);
        forceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.jason.pref_bbestpractice.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });
    }
}
