package com.example.jeson.androidthreadtest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int UPDATE_TEXT = 1;

    private TextView text;

    private Button changeText;

    private Handler handler = new Handler() {

        public void handleMessage(Message mag) {
            switch (mag.what) {
                case UPDATE_TEXT:
                    //在这里可以进行UI操作
                    text.setText("Nice to meet you");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);
        changeText = (Button) findViewById(R.id.change_text);
        changeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        //text.setText("Nice to meet you");   //这属于在子线程中进行UI操作，会到导致程序崩溃

                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message);   //将Message对象发送出去
                    }
                }).start();
            }
        });
    }
}
