package com.milog.test.myfloat.common;

import android.util.Log;

/**
 * Created by miloway on 2018/8/22.
 */

public class MiloLog {


    public static void d(String tag, String msg) {
        Log.d(tag, msg);
        System.out.println(tag + " " + msg);
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }
}
