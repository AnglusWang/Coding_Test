package com.anglus.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by Anglus on 2016/10/15.
 */

public class IRemoteService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    private IBinder mIBinder = new IMyAidlInterface.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.i("IRemoteService", "收到远程的请求，传入的参数是：" + num1 + "和" + num2);
            return num1 + num2;
        }
    };
}
