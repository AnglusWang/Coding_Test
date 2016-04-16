package com.example.jeson.datbasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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

        /**
         * 向数据库里添加数据
         * 调用SQLiteOpenHelper的getReadableDatabase()或者getWritableDatabase（）
         * 方法时会返回一个SQLiteDatabase对象
         * SQLiteDatabase中提供了insert()方法，可以用于添加数据
         * 该方法接收三个参数：1、表名
         *                 2、用于在未指定添加数据的情况下给某些可为空的列自动赋值NULL
         *                 3、一个ContentValues对象
         *                      此对象提供了一系列put()方法重载，可以用于向ContentValues中添加数据
         */
        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始封装第一条数据
                values.put("name", "The Da Vin Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 15.63);
                db.insert("Book", null, values);    //插入第一个条数据
                values.clear();

                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 483);
                values.put("price", 666.66);
                db.insert("Book", null, values);    //插入第二条数据
            }
        });
    }

}
