package com.milog.test.myfloat;

import android.app.Application;

/**
 * Created by miloway on 2018/10/8.
 */

public class FloatApplication extends Application {

    private static FloatApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static FloatApplication getApplication() {
        return app;
    }
}
