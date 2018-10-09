package com.milog.test.myfloat.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.milog.test.myfloat.FloatManager;

/**
 * Created by miloway on 2018/10/9.
 */

public class FloatManagerService extends Service {

    private MyBinder binder;

    @Override
    public ComponentName startForegroundService(Intent service) {
        return super.startForegroundService(service);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FloatManager.getInstance().show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (binder == null) {
            binder = new MyBinder();
        }
        return binder;
    }

    public void showFloat(){
        FloatManager.getInstance().show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        FloatManager.getInstance().close();
    }

    public class MyBinder extends Binder {
        public FloatManagerService getService() {
            return FloatManagerService.this;
        }
    }
}
