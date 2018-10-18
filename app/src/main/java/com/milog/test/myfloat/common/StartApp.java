package com.milog.test.myfloat.common;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Process;
import android.os.SystemClock;
import android.os.UserHandle;
import android.view.MotionEvent;

import com.milog.test.myfloat.FloatApplication;

import java.lang.reflect.Method;

/**
 * Created by miloway on 2018/10/15.
 */

public class StartApp {

    public static void start(String packageName) {
//        instrumentMode(packageName);
        multiUserMode(packageName);
//        appDevicePolicy(packageName);
    }

    private static void instrumentMode(String packageName) {
        PackageManager packageManager = FloatApplication.getApplication().getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        Instrumentation inst = new Instrumentation();
        try {
            Activity activity = inst.startActivitySync(intent);
            activity.onTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 300, 300, 0));
            activity.onTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 300, 300, 0));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void multiUserMode(String packageName) {
//        UserManager userManager = (UserManager) getContext().getSystemService(Context.USER_SERVICE);
//        userManager.getUserName();
        UserHandle userHandle = Process.myUserHandle();
//        if (UserHandle.MU_ENABLED) {
//            return uid / UserHandle.PER_USER_RANGE;
//        }
        int userId = Process.myUid() / 100000;
//        int userId = UserHandle.myUserId();

        PackageManager packageManager = FloatApplication.getApplication().getPackageManager();
//        packageManager.installExistingPackageAsUser(packageName, userId);

        Class p = PackageManager.class;
        MiloLog.i("multiUserMode", "start install" + Process.myUid() + " " + userId + " handle " + Process.myUserHandle());
        try {
            Method method = p.getDeclaredMethod("installExistingPackageAsUser", String.class, Integer.TYPE);
            int result = (int) method.invoke(packageManager, packageName, userId);
            MiloLog.i("multiUserMode", "install result" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MiloLog.i("multiUserMode", "end");
    }


}
