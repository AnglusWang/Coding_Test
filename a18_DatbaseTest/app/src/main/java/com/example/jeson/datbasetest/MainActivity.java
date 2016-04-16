package com.example.jeson.datbasetest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private MyDatabaseHelper myDatabaseHelper;

    /**
     * 在onCreate 方法中构建了一个MyDatabaseHelper对象，
     * 并在通过构造函数的参数将数据库名指定为 BookStore.db ，版本号为1
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在这里调用getWritableDatabase方法， 每次会进行检测是否有BookStore.db数据库，没有的就会创建一个
                myDatabaseHelper.getWritableDatabase();
            }
        });
    }

}
