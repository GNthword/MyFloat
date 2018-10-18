package com.milog.test.myfloat;

import android.app.Application;

import com.milog.test.myfloat.core.Hooker;

/**
 * Created by miloway on 2018/10/8.
 */

public class FloatApplication extends Application {

    private static FloatApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Hooker.hookInstrumentation();
    }

    public static FloatApplication getApplication() {
        return app;
    }
}
