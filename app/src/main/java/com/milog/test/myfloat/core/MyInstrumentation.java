package com.milog.test.myfloat.core;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;

import com.milog.test.myfloat.common.MiloLog;

import java.lang.reflect.Method;

/**
 * Created by miloway on 2018/10/11.
 */

public class MyInstrumentation extends Instrumentation {


    private Instrumentation instrumentation;

    private final String TAG = "MyInstrumentation";

    public MyInstrumentation(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MiloLog.i(TAG, "here newActivity " + className + " intent " + intent);
        return super.newActivity(cl, className, intent);
    }

    @Override
    public Activity newActivity(Class<?> clazz, Context context, IBinder token, Application application, Intent intent, ActivityInfo info, CharSequence title, Activity parent, String id, Object lastNonConfigurationInstance) throws IllegalAccessException, InstantiationException {
        MiloLog.i(TAG, "here newActivity2 " + info + " intent " + intent);
        return super.newActivity(clazz, context, token, application, intent, info, title, parent, id, lastNonConfigurationInstance);
    }


    //这个方法是由于原始方法里面的Instrumentation有execStartActivity方法来定的
    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target, Intent intent, int requestCode, Bundle options) {
        MiloLog.i(TAG, "\n打印调用startActivity相关参数: \n" + "who = [" + who + "], " + "\ncontextThread = [" + contextThread + "], \ntoken = [" + token + "], " + "\ntarget = [" + target + "], \nintent = [" + intent + "], \nrequestCode = [" + requestCode + "], \noptions = [" + options + "]");
        //由于这个方法是隐藏的，所以需要反射来调用，先找到这方法
        try {
            Method execStartActivity = Instrumentation.class.getDeclaredMethod("execStartActivity", Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            return (ActivityResult) execStartActivity.invoke(instrumentation, who, contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            //如果你在这个类的成员变量Instrumentation的实例写错mInstrument,代码讲会执行到这里来
            throw new RuntimeException("if Instrumentation paramerter is mInstrumentation, hook will fail");
        }
    }
}