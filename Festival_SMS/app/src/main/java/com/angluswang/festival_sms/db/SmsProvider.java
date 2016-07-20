package com.angluswang.festival_sms.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.angluswang.festival_sms.bean.SendedMsg;

/**
 * Created by Jeson on 2016/7/20.
 * SMSProvider 数据提供者
 */

public class SmsProvider extends ContentProvider {

    private static final String AUTHORITY = "com.angluswang.sms.provider.SmsProvider";
    public static final Uri URI_SMS_ALL = Uri.parse("content://" + AUTHORITY + "/sms");

    private static UriMatcher sMatcher;

    private static final int SMS_ALL = 0;
    private static final int SMS_ONE = 1;

    static {
        sMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sMatcher.addURI(AUTHORITY, "sms", SMS_ALL);
        sMatcher.addURI(AUTHORITY, "sms/#", SMS_ONE);
    }

    private SmsDbOpenHelper mHelper;
    private SQLiteDatabase mDatabase;

    @Override
    public boolean onCreate() {
        mHelper = SmsDbOpenHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        int match = sMatcher.match(uri);
        switch (match) {
            case SMS_ALL:
                break;
            case SMS_ONE:
                long id = ContentUris.parseId(uri);
                s = "_id = ?";
                strings1 = new String[]{String.valueOf(id)};
                break;
            default:
                throw new IllegalArgumentException("Wrong URI : " + uri);
        }
        mDatabase = mHelper.getReadableDatabase();
        Cursor cursor = mDatabase.query(SendedMsg.TABLE_NAME, strings, s, strings1, null, null, s1);

        cursor.setNotificationUri(getContext().getContentResolver(), URI_SMS_ALL);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int match = sMatcher.match(uri);
        if (match != SMS_ALL) {
            throw new IllegalArgumentException("Wrong URI : " + uri);
        }
        mDatabase = mHelper.getWritableDatabase();
        long rowId = mDatabase.insert(SendedMsg.TABLE_NAME, null, contentValues);
        if (rowId > 0) {
            notifyDataSetChanged();
            return ContentUris.withAppendedId(uri, rowId);
        }
        return null;
    }

    private void notifyDataSetChanged() {
        getContext().getContentResolver().notifyChange(URI_SMS_ALL, null);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
