package com.milog.test.myfloat.common;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.milog.test.myfloat.R;

/**
 * Created by miloway on 2018/8/7.
 */

public class DialogFactory {

    public static Dialog getOneBtnDialog(Context context, String content) {
        String title = context.getResources().getString(R.string.dialog_title);
        String ok = context.getResources().getString(R.string.dialog_btn_ok);
        String cancel = context.getResources().getString(R.string.dialog_btn_cancel);
        return getOneBtnDialog(context, title, content, ok, cancel);
    }


    public static Dialog getOneBtnDialog(Context context, String title, String content, String ok, String cancel) {
        if (context == null) {
            return null;
        }
        Dialog dialog = new Dialog(context, R.style.MiDialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.milo_dialog, null);
        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) contentView.findViewById(R.id.tv_content);
        Button btnCancel = (Button) contentView.findViewById(R.id.btn_cancel);
        Button btnOk = (Button) contentView.findViewById(R.id.btn_ok);

        tvTitle.setText(title);
        tvContent.setText(content);
        btnCancel.setText(cancel);
        btnOk.setText(ok);

        //hide
        btnCancel.setVisibility(View.GONE);
        contentView.findViewById(R.id.line3).setVisibility(View.GONE);

        //init theme


        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(contentView);
        return dialog;
    }


    public static Dialog getDialog(Context context, String content) {

        String ok = context.getResources().getString(R.string.dialog_btn_ok);
        String cancel = context.getResources().getString(R.string.dialog_btn_cancel);
        return getDialog(context, content, ok, cancel);
    }

    public static Dialog getDialog(Context context, String content, String ok, String cancel) {

        String title = context.getResources().getString(R.string.dialog_title);
        return getDialog(context, title, content, ok, cancel);
    }

    public static Dialog getDialog(Context context, String title, String content, String ok, String cancel) {
        if (context == null) {
            return null;
        }
        Dialog dialog = new Dialog(context, R.style.MiDialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.milo_dialog, null);
        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) contentView.findViewById(R.id.tv_content);
        Button btnCancel = (Button) contentView.findViewById(R.id.btn_cancel);
        Button btnOk = (Button) contentView.findViewById(R.id.btn_ok);

        tvTitle.setText(title);
        tvContent.setText(content);
        btnCancel.setText(cancel);
        btnOk.setText(ok);

        //init theme


        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(contentView);
        return dialog;
    }
}
