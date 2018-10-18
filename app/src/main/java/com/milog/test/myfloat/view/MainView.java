package com.milog.test.myfloat.view;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.milog.test.myfloat.MyActivity;
import com.milog.test.myfloat.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by miloway on 2018/10/10.
 */

public class MainView extends LinearLayout {

    private Button btnStart;
    private Button btnStartActivity;
    private ViewConnection connection;

    public MainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        btnStart = findViewById(R.id.btn_start);
        btnStartActivity = findViewById(R.id.btn_start_activity);
        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connection != null) {
                    connection.starFloat();
                    injectActivity();
                }
            }
        });
        btnStartActivity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    private void injectActivity() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(getContext(), MyActivity.class);
//                Instrumentation inst = getInstrumentation();
//                Activity activity = inst.startActivitySync(intent);
//                activity.onTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 300, 300, 0));
//                activity.onTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 300, 300, 0));
//            }
//        }).start();
    }

    private Instrumentation getInstrumentation() {
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Method sCurrentActivityThread = activityThread.getDeclaredMethod("currentActivityThread");
            sCurrentActivityThread.setAccessible(true);
            //获取ActivityThread 对象
            Object activityThreadObject = sCurrentActivityThread.invoke(activityThread);

            //获取 Instrumentation 对象
            Field mInstrumentation = activityThread.getDeclaredField("mInstrumentation");
            mInstrumentation.setAccessible(true);
            return  (Instrumentation) mInstrumentation.get(activityThreadObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    public void setViewConnection(ViewConnection viewConnection) {
        this.connection = viewConnection;
    }


    public void destroy() {
        connection = null;
    }

    public interface ViewConnection {
        void starFloat();
    }
}
