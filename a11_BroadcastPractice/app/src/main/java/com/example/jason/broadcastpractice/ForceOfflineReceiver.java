package com.example.jason.broadcastpractice;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

/**
 * Created by Jason on 4/6/2016.
 * 下线广播
 */
public class ForceOfflineReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        dialogBuilder.setTitle("Warning");
        dialogBuilder.setMessage("You are forced to be offline. Please try to login again");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("OK",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCollector.finishAll();
                    Intent intent1 = new Intent(context, LoginActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                }
            });

        AlertDialog alertDialog = dialogBuilder.create();

        //需要设置AlertDialog类型， 保证在广播接收器中可以正常弹出
        //需要把对话框的类型设置为TYPE_SYSTEM_ALERT，否则无法对话框无法在广播接收器中弹出
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        alertDialog.show();
    }
}
