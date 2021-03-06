package com.example.jeson.contendfordatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jeson on 2016/4/16.
 * 把建表语句定义成一个字符串常量，
 * 然后在onCreate（）方法中调用SQLiteDatabase的execSQL（）方法去执行这条建表语句，
 * 就可以保证在数据库创建完成的同时还能成功创建Book表
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    /**
     * 数据库表，表中有id（主键）、作者、价格、页数和书名
     * 其中，integer表示整型， real表示浮点型，text表示文本类型，blob表示二进制类型
     * primary key 表示将id设置为主键， autoincrement表示id列是自增长的。
     *  create table Book (
     *      id integer primary key autoincrement,
     *      author text,
     *      price real,
     *      pages integer,
     *      name text)
     */
    public static final String CREATE_BOOK = "create table Book ("
            + "id integer primary key autoincrement, "
            + "author text, "
            + "price real, "
            + "pages integer, "
            + "name text, "
            + "category_id integer )";

    /**
     * 新建一张Category表用于记录书籍的分类
     * 表中有 id（主键）、分类名和分类代码
     *  create table category (
     *      id integer primary key autoincrement,
     *      category_name text,
     *      category_code integer)
     */
    public static final String CREATE_CATEGORY = "create table category ("
            + "id integer primary key autoincrement, "
            + "category_name text, "
            + "category_code integer)";

    private Context mContext;

    public MyDatabaseHelper (Context context, String name,
           SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //执行DROP语句，如果发现数据库中已经存在Book表或者Category表了，就将这两张表删除掉
//        db.execSQL("drop table if exists Book");
//        db.execSQL("drop table if exists Category");
//        onCreate(db);
        switch (oldVersion) {
            case 1:
                db.execSQL(CREATE_CATEGORY);
            case 2:
                db.execSQL("alter table Book add column category_id integer");
        }
    }
}
