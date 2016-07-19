package com.angluswang.festival_sms.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.angluswang.festival_sms.bean.SendedMsg;

/**
 * Created by Jeson on 2016/7/19.
 * 创建数据库操作类
 */

public class SmsDbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "sms.db";
    private static final int DB_VERSION = 1;

    private SmsDbOpenHelper(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }

    private static SmsDbOpenHelper mHelper;

    public static SmsDbOpenHelper getInstance(Context context) {
        if (mHelper == null) {
            synchronized (SmsDbOpenHelper.class) {
                if (mHelper == null) {
                    mHelper = new SmsDbOpenHelper(context);
                }
            }
        }
        return mHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "create table " + SendedMsg.TABLE_NAME + " ( " +
                " _id integer primary key autoincrement , " +
                SendedMsg.COLUME_DATE + "integer , " +
                SendedMsg.COLUME_FESTIVAL_NAME + "text , " +
                SendedMsg.COLUME_MSG + "text , " +
                SendedMsg.COLUME_NAMES + "text , " +
                SendedMsg.COLUME_NUMBERS + "text , " +
                ")";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
