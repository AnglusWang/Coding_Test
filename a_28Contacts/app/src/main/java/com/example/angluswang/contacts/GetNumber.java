package com.example.angluswang.contacts;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeson on 2016/6/12.
 * 获取号码
 */

public class GetNumber {

    public static List<PhoneInfo> sList = new ArrayList<>();

    public static String getNumber(Context context) {
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        String phoneNumber;
        String phoneName;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                phoneNumber = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                phoneName = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                System.out.println("联系人：" + phoneName + "; 号码：" + phoneNumber);

                PhoneInfo phoneInfo = new PhoneInfo(phoneName, phoneNumber);
                sList.add(phoneInfo);
            }
            cursor.close();
            System.out.println("sList: " + sList.toString());
        }
        return null;
    }
}
