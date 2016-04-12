package com.example.jason.data_to_data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends Activity {

    private EditText et2;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tv2 = (TextView) findViewById(R.id.tv2_name);
        Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");
        tv2.setText(data);

        Button bt2 = (Button) findViewById(R.id.bt2_name);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et2 = (EditText) findViewById(R.id.et2_Text);
                Intent intent = new Intent();
                String return_data = et2.getText().toString();
                intent.putExtra("data_return",return_data);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return","Hello,FirstActivity~");
        setResult(RESULT_OK,intent);
        finish();
    }
}
