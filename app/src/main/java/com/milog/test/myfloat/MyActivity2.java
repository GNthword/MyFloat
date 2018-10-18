package com.milog.test.myfloat;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.widget.Toast;


/**
 * Created by miloway on 2018/10/10.
 */

public class MyActivity2 extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(getApplicationContext(), "MyActivity2 " + event.getX()+ " " + event.getY(), Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }

}
