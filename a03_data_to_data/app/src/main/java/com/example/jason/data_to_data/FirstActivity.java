package com.example.jason.data_to_data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FirstActivity extends Activity {

    private EditText et1;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Button bt1 = (Button) findViewById(R.id.bt1_name);
        Button bt1_0 = (Button) findViewById(R.id.bt1_0_name);

        bt1_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1 = (EditText) findViewById(R.id.et1_Text);
                String data = et1.getText().toString();

                Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                intent.putExtra("extra_data", data);
                startActivity(intent);
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        tv1 = (TextView) findViewById(R.id.tv1_name);
        switch (requestCode) {
            case 1:
                if(resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("data_return");
                    tv1.setText(returnedData);
                }
                break;
            default:
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        et1.setText(null);
    }
}
