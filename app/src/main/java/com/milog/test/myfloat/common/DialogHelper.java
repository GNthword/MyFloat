package com.milog.test.myfloat.common;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.milog.test.myfloat.R;

/**
 * Created by miloway on 2018/10/9.
 */

public class DialogHelper {

    public static void showDialog(Context context, String content) {

        final Dialog dialog = DialogFactory.getOneBtnDialog(context, content);
        if (dialog == null) {
            return;
        }
        dialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}
