package com.example.jason.ui_test1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText et;
    private ImageView img;
    private ProgressBar prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1_id);
        et = (EditText) findViewById(R.id.et_id);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sms = et.getText().toString();
                Toast.makeText(MainActivity.this, sms, Toast.LENGTH_SHORT).show();
            }
        });

        Button button2 = (Button) findViewById(R.id.button2_id);
        img = (ImageView) findViewById(R.id.img_id);
        prog = (ProgressBar) findViewById(R.id.progress_id);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageResource(R.drawable.photo1);

                if(prog.getVisibility() == View.GONE) {
                    prog.setVisibility(View.VISIBLE);
                }else {
                    prog.setVisibility(View.GONE);
                }

                int progress = prog.getProgress();
                progress = progress + 10;
                while(progress == 100) {
                    progress = 0;
                }
                prog.setProgress(progress);
            }
        });

        Button bt3 =(Button) findViewById(R.id.bt_start);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageResource(R.drawable.photo4);
                et.setText("");
                prog.setProgress(0);
                prog.setVisibility(View.VISIBLE);
            }
        });
    }
}
