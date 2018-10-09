package com.milog.test.myfloat;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by miloway on 2018/10/8.
 */

public class FloatManager {

    private WindowManager windowManager;
    private FloatView floatView;
    private WindowManager.LayoutParams layoutParams;
    private DisplayMetrics dm;

    private int statusBarHeight;
    private boolean showState;

    private static FloatManager floatManager;

    public static FloatManager getInstance() {
        if (floatManager == null) {
            floatManager = new FloatManager();
        }
        return floatManager;
    }

    private FloatManager() {
        Context context = FloatApplication.getApplication();
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        floatView = (FloatView) LayoutInflater.from(context).inflate(R.layout.float_view, null);
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
        dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.format = PixelFormat.TRANSPARENT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        //不使用 Gravity.CENTER_VERTICAL, 因为部分应用隐藏statusbar 会导致高度变化
        int resourceId = context.getResources().getIdentifier("status_bar_height","dimen","android");
        statusBarHeight = 0;
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        floatView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        layoutParams.y = dm.heightPixels/2 - statusBarHeight - floatView.getMeasuredHeight()/2;
        layoutParams.x = dm.widthPixels;
    }


    public void show() {
        if (!showState) {
            showState = true;
            windowManager.addView(floatView, layoutParams);
        }
    }

    public void close() {
        if (showState) {
            showState = false;
            windowManager.removeView(floatView);
        }
    }

    public void updateView(float x, float y) {
        if (showState) {
            layoutParams.x = (int) x - floatView.getWidth()/2;
            layoutParams.y = (int) y - floatView.getHeight()/2 - statusBarHeight;
            windowManager.updateViewLayout(floatView, layoutParams);
        }
    }

    public void start(float x, float y) {
        if (showState) {
            layoutParams.x = (int) x - floatView.getWidth()/2;
            layoutParams.y = (int) y - floatView.getHeight()/2 - statusBarHeight;
            windowManager.updateViewLayout(floatView, layoutParams);
        }
    }

    public void stop(float x, float y) {
        if (showState) {
            layoutParams.x = (int) x - floatView.getWidth()/2;
            layoutParams.y = (int) y - floatView.getHeight()/2 - statusBarHeight;
            windowManager.updateViewLayout(floatView, layoutParams);
        }
    }




}
