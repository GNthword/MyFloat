package com.milog.test.myfloat.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.milog.test.myfloat.R;

/**
 * Created by miloway on 2018/8/22.
 */

public class MiloDialog extends LinearLayout {

    private int showHeight;
    private int appWidth;
    private ScrollView scrollView;
    private LinearLayout llBottom;
    private View line2;
    private float widthPercent;

    private final String TAG = "MiloDialog";

    public MiloDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        MiloLog.d(TAG, "<init>");
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.milo_dialog);
        widthPercent = typedArray.getFloat(R.styleable.milo_dialog_width_percent, 0);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        MiloLog.d(TAG, "onFinishInflate");
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        ///考虑每个dialog布局不一定相同，不做精准的计算
        showHeight = (int) (dm.heightPixels * 0.7d);
        appWidth = dm.widthPixels;
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        line2 = findViewById(R.id.line2);
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        MiloLog.d(TAG, "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        MiloLog.d(TAG, "onLayout " + changed);
        int height = getMeasuredHeight();
        if (height >= showHeight ) {
            if (scrollView != null) {
                int scrollHeight = showHeight - getBottomHeight();
                LayoutParams lp = (LayoutParams) scrollView.getLayoutParams();
                if (lp.height != scrollHeight) {
                    lp.height = scrollHeight;
                }
            }
        }
        setDialogWidth();

    }

    private void setDialogWidth() {
        if (widthPercent > 0 && widthPercent < 1) {
            ViewGroup.LayoutParams lp = getLayoutParams();
            int width = (int) (widthPercent * appWidth);
            if (lp.width != width) {
                lp.width = width;
            }
        }
    }

    private int getBottomHeight() {
        int height = 0;
        LayoutParams lp = null;
        if (scrollView != null) {
            lp = (LayoutParams) scrollView.getLayoutParams();
            height += lp.bottomMargin;
        }

        if (line2 != null) {
            height += line2.getMeasuredHeight();
            lp = (LayoutParams) line2.getLayoutParams();
            height += lp.topMargin + lp.bottomMargin;
        }

        if (llBottom != null) {
            height += llBottom.getMeasuredHeight();
            lp = (LayoutParams) llBottom.getLayoutParams();
            height += lp.topMargin + lp.bottomMargin;
        }


        return height;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        MiloLog.d(TAG, "onSizeChanged");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        MiloLog.d(TAG, "onDraw");
    }
}
