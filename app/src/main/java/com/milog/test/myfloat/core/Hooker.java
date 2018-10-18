package com.milog.test.myfloat.core;

import android.app.Instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by miloway on 2018/10/11.
 */

public class Hooker {

    private static final String TAG = "Hooker";

    public static void hookInstrumentation() {
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Method sCurrentActivityThread = activityThread.getDeclaredMethod("currentActivityThread");
            sCurrentActivityThread.setAccessible(true);
            //获取ActivityThread 对象
            Object activityThreadObject = sCurrentActivityThread.invoke(activityThread);

            //获取 Instrumentation 对象
            Field mInstrumentation = activityThread.getDeclaredField("mInstrumentation");
            mInstrumentation.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) mInstrumentation.get(activityThreadObject);
            MyInstrumentation customInstrumentation = new MyInstrumentation(instrumentation);
            //将我们的 customInstrumentation 设置进去
            mInstrumentation.set(activityThreadObject, customInstrumentation);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}



