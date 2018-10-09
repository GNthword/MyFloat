package com.milog.test.myfloat.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import com.milog.test.myfloat.R;
import com.milog.test.myfloat.common.MiloLog;
import java.util.ArrayList;

/**
 * Created by miloway on 2018/8/7.
 */

public class TypeSetTextView extends TextView {

    private TextPaint paint;
    private String content;
    private float textSize;
    private float textDrawWidth;
    private float paddingTop;
    private float paddingBottom;
    private float paddingLeft;
    private float paddingRight;

    private boolean isLineHeightRaw;
    private boolean isNeedTransform;

    private final String TAG = "TypeSetTextView";
    public TypeSetTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.type_set_text_view);
        if (attrs != null) {
            isNeedTransform = attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res-auto", "transform_character", true);
        }
        isLineHeightRaw = typedArray.getBoolean(R.styleable.type_set_text_view_line_height_raw, false);
        typedArray.recycle();
        init();
        MiloLog.d(TAG, "<init>");
    }

    private void init() {
        paint = getPaint();
        content = getText().toString();
        textSize = paint.getTextSize();
        paddingTop = getPaddingTop();
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();

        if (!TextUtils.isEmpty(content)) {
            if (isNeedTransform) {
                content = content.replace("\t", "   ");
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        MiloLog.d(TAG, "onFinishInflate");
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        content = getText().toString();
        if (!TextUtils.isEmpty(content)) {
            if (isNeedTransform) {
                content = content.replace("\t", "   ");
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        MiloLog.d(TAG, "onMeasure");
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = calcViewHeight(width);
        if (width == 0) {
            return;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        MiloLog.d(TAG, "onLayout " + changed);
    }

    private int calcViewHeight(int width) {
        textDrawWidth = width - paddingLeft - paddingRight;
        char[] chars = content.toCharArray();
        int length = chars.length;
        float curWidth = 0;
        int lines = 0;
        for (int i = 0; i < length; i++) {
            char c = chars[i];
            if (c == '\n') {
                lines++;
                curWidth = 0;
                i++;
                continue;
            }
            curWidth += paint.measureText(chars, i, 1);
            if (curWidth >= textDrawWidth) {
                lines++;
                curWidth = paint.measureText(chars, i, 1);
            }
        }
        if (curWidth > 0) {
            lines++;
        }
        //设置默认高度
        if (lines == 0) {
            lines = 1;
        }

        return (int) (paddingTop + lines * getLineHeight() + paddingBottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        MiloLog.d(TAG, "onSizeChanged");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        MiloLog.d(TAG, "onDraw");
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        textDrawWidth = getWidth() - paddingLeft - paddingRight;
        char[] chars = content.toCharArray();
        int length = chars.length;
        float curWidth = 0;
        int pos = 0;
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            char c = chars[i];
            if (c == '\n') {
                curWidth = 0;
                strings.add(content.substring(pos,i));
                i++;
                pos = i;
                continue;
            }
            curWidth += paint.measureText(chars, i, 1);
            if (curWidth >= textDrawWidth) {
                curWidth = paint.measureText(chars, i, 1);
                strings.add(content.substring(pos,i));
                pos = i;
            }
        }
        if (curWidth > 0) {
            strings.add(content.substring(pos));
        }

        float y;
        float x = paddingLeft;
        int baseLine = getBaseline();
        int lineHeight = getLineHeight();
        for (int i = 0; i < strings.size(); i++) {
            y = paddingTop + i * lineHeight + baseLine;
            canvas.drawText(strings.get(i), x, y, paint);
        }
    }

    /**
     * 绘制文字的基准线，对应 Paint.FontMetrics.leading 即0值
     * 当前 top = -44.35，baseline为45 即-Math.floor(paint.getFontMetrics().top) 或者 Math.ceil(-paint.getFontMetrics().top)
     */
    @Override
    public int getBaseline() {
        if (isLineHeightRaw) {
            return super.getBaseline();
        }else {
            return (getLineHeight() - super.getLineHeight()) /2 + super.getBaseline();
        }
    }

    @Override
    public int getLineHeight() {
        if (isLineHeightRaw) {
            return super.getLineHeight();
        }else {
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            return (int) (Math.ceil(fontMetrics.bottom - fontMetrics.top) + textSize/5 + 0.5f);
        }
    }
}
