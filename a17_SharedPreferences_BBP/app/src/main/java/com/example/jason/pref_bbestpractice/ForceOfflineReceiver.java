package com.example.jason.pref_bbestpractice;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Jeson on 2016/4/15.
 * 广播接收器 用于接收下线广播 并做相应的逻辑处理动作
 */
public class ForceOfflineReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(final Context context, Intent intent) {

        Toast.makeText(context, "接收到广播", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder dialogBuilder =  new AlertDialog.Builder(context);
        dialogBuilder.setTitle("下线通知");
        dialogBuilder.setMessage("你的账户在其他地方登录 Over");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("重新登录",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCollector.finishAll();//摧毁所有活动
                        Intent intent1 = new Intent(context, LoginActivity.class);
                        //在广播接受者中启动活动需要添加FLAG_ACTIVITY_NEW_TASK标志
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);
                    }
                });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();
    }
}
