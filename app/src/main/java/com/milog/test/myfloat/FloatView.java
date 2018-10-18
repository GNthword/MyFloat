package com.milog.test.myfloat;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.milog.test.myfloat.common.MiloLog;
import com.milog.test.myfloat.common.StartApp;

/**
 * Created by miloway on 2018/10/8.
 */

public class FloatView extends LinearLayout implements View.OnClickListener{

    private View view;
    private Button btnStartApp;
    private Button btnStartClick;
    private Button btnClose;


    public FloatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        initEvent();
    }

    private void initView() {
        view = findViewById(R.id.view);
        btnStartApp = findViewById(R.id.btn_start_app);
        btnStartClick = findViewById(R.id.btn_start_click);
        btnClose = findViewById(R.id.btn_close);
    }

    private void initEvent() {
        view.setOnClickListener(this);
        btnStartApp.setOnClickListener(this);
        btnStartClick.setOnClickListener(this);
        btnClose.setOnClickListener(this);

        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                }else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    FloatManager.getInstance().updateView(event.getRawX(), event.getRawY());
                }else if (event.getAction() == MotionEvent.ACTION_UP) {

                }
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.view) {
            switchView();
        }else if (id == R.id.btn_start_app) {
            startApp("com.milog.test.mytest");
        }else if (id == R.id.btn_start_click) {
            startClick(300, 300);
        }else if (id == R.id.btn_close) {
            closeFloat();
        }
    }

    private void switchView() {
        if (btnStartApp.getVisibility() == View.GONE) {
            btnStartApp.setVisibility(View.VISIBLE);
            btnStartClick.setVisibility(View.VISIBLE);
            btnClose.setVisibility(View.VISIBLE);
        }else {
            btnStartApp.setVisibility(View.GONE);
            btnStartClick.setVisibility(View.GONE);
            btnClose.setVisibility(View.GONE);
        }
    }

    private void closeFloat() {
        FloatManager.getInstance().close();
    }

    private void startClick(final int x, final int y) {
//                MotionEvent event = MotionEvent.obtain()
//                dispatchGenericMotionEvent()
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Runtime.getRuntime().exec("input tap "+ x + " " + y);

                    InputManager inputManager = (InputManager) getContext().getSystemService(Context.INPUT_SERVICE);

                    Instrumentation inst = new Instrumentation();
                    inst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, x, y, 0));
                    inst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, x, y, 0));
                } catch (Exception e) {
                    MiloLog.i("click ", e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void startApp(final String packageName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Intent intent = new Intent();
//                    intent.setClassName(packageName, "MainActivity");
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    getContext().startActivity(intent);
                    PackageManager packageManager = getContext().getPackageManager();
                    if (checkPackInfo(packageName)) {
                        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
                        getContext().startActivity(intent);

                        StartApp.start(packageName);
                    } else {
                        Toast.makeText(getContext(), "没有安装" + packageName, Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private boolean checkPackInfo(String packName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getContext().getPackageManager().getPackageInfo(packName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }


}
