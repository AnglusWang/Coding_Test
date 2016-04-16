package com.example.jeson.datbasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
                //在这里调用getWritableDatabase方法，
                // 每次会进行检测是否有BookStore.db数据库，没有的就会创建一个
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

        /**
         * 更新数据
         * 构建一个ContentValues对象
         *      指定说明数据，如： 把价格这一列的数据更新成100.66
         * 调用SQLiteDatabase的update()方法去执行具体的更新操作
         */
        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 100.66);
                db.update("Book", values, "name = ?", new String[] {"The Da Vin Code"});
            }
        });

        /**
         * 删除数据
         */
        Button deleteData = (Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                db.delete("Book", "pages > ?", new String[] {"460"});
            }
        });

        /**
         * 查询数据
         */
        Button queryData = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                //查询Book表中所有数据
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        //遍历Cursor对象，取出数据并打印
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getFloat(cursor.getColumnIndex("price"));
                        //Log 打印日志
                        Log.d("MainActivity", "book name is " + name);
                        Log.d("MainActivity", "book author is " + author);
                        Log.d("MainActivity", "book pages is " + pages);
                        Log.d("MainActivity", "book price is " + price);
                    }while(cursor.moveToNext());
                }
                cursor.close();
            }
        });

        /**
         * （Android中事务的标准用法）
         * 首先用SQLiteDatabase的beginTransaction()方法来开启一个事务
         * 然后在一个异常捕获的代码块中去执行具体的数据库操作
         * 当所有的操作都完成之后，调用setTransactionSuccessful()表示事务已经执行成功了
         * 然后在finally中调用endTransaction()来结束事务。
         */
        Button replaceData = (Button) findViewById(R.id.replace_data);
        replaceData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                db.beginTransaction();  //开启事务
                try {
                    db.delete("Book", null, null);
//                    if (true) {
//                        //在这里手动抛出一个异常，让事务失败
//                        throw new NullPointerException();
//                    }
                    ContentValues values = new ContentValues();
                    values.put("name", "Game of Thrones");
                    values.put("author", "George Martin");
                    values.put("pages", 720);
                    values.put("price", 20.85);
                    db.insert("Book", null, values);
                    db.setTransactionSuccessful();  //事务已经成功执行
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.endTransaction();    //结束事务
                }
            }
        });
    }

}
