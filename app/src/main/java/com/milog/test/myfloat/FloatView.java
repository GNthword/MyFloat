package com.milog.test.myfloat;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by miloway on 2018/10/8.
 */

public class FloatView extends LinearLayout{

    private View view;
    private Button btnStart;

    public FloatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        view = findViewById(R.id.view);
        btnStart = findViewById(R.id.btn_start);
        init();
    }

    private void init() {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.getVisibility() == View.GONE) {
                    btnStart.setVisibility(View.VISIBLE);
                }else {
                    btnStart.setVisibility(View.GONE);
                }
            }
        });
        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatManager.getInstance().close();
            }
        });

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
