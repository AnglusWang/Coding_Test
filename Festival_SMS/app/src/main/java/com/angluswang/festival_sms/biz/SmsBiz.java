package com.angluswang.festival_sms.biz;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.telephony.SmsManager;

import com.angluswang.festival_sms.bean.SendedMsg;
import com.angluswang.festival_sms.db.SmsProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * Created by Jeson on 2016/7/17.
 * 短信业务
 */
public class SmsBiz {

    private Context context;

    public SmsBiz(Context context) {
        this.context = context;
    }

    public int sendMsg(String number, String msg,
                       PendingIntent sentPi, PendingIntent deliverPi) {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> contents = smsManager.divideMessage(msg);
        for (String content : contents) {
            smsManager.sendTextMessage(number, null, content, sentPi, deliverPi);
        }
        return contents.size();
    }

    public int sendMsg(Set<String> numbers, SendedMsg msg,
                       PendingIntent sentPi, PendingIntent deliverPi) {
        save(msg);
        int result = 0;
        for (String number : numbers) {
            int count = sendMsg(number, msg.getMsg(), sentPi, deliverPi);
            result += count;
        }
        return result;
    }

    /**
     * 将发送的短信相关信息保存到本地数据库中
     * @param sendedMsg
     */
    private void save(SendedMsg sendedMsg) {
        sendedMsg.setDate(new Date());
        ContentValues contentValues = new ContentValues();
        contentValues.put(SendedMsg.COLUME_DATE, sendedMsg.getDate().getTime());
        contentValues.put(SendedMsg.COLUME_FESTIVAL_NAME, sendedMsg.getFestivalName());
        contentValues.put(SendedMsg.COLUME_MSG, sendedMsg.getMsg());
        contentValues.put(SendedMsg.COLUME_NAMES, sendedMsg.getNames());
        contentValues.put(SendedMsg.COLUME_NUMBERS, sendedMsg.getNumbers());

        context.getContentResolver().insert(SmsProvider.URI_SMS_ALL, contentValues);
    }
}
