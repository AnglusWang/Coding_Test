package com.example.jason.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button bt_1 = (Button) findViewById(R.id.bt_1);
        Button bt_3 = (Button) findViewById(R.id.bt_3);
        Button bt_4 = (Button) findViewById(R.id.bt_4);

        bt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        bt_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.jason.menu.ACTION_START");
                intent.addCategory("com.example.jason.menu.My_Category");
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_first,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Third_Activity:
                startActivity(
                        new Intent("com.example.jason.menu.ACTION_START").
                                addCategory("com.example.jason.menu.My_Category")
                );
                Toast.makeText(FirstActivity.this, "隐式活动Ok", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Button_2_Activity:
                startActivity(new Intent(FirstActivity.this,SecondActivity.class));
                Toast.makeText(FirstActivity.this,"进入到第二个活动界面",Toast.LENGTH_LONG).show();
            default:
        }
        return true;
    }
}
