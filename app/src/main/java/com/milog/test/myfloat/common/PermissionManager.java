package com.milog.test.myfloat.common;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.milog.test.myfloat.R;

import java.util.HashMap;

/**
 * Created by miloway on 2018/10/8.
 * 权限管理
 */

public class PermissionManager {

    private Activity activity;

    private static final int REQUEST_CODE_SYSTEM_ALTER_WINDOW = 1;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 2;
    private HashMap<String, Integer> maps;
    public PermissionManager(Activity activity) {
        this.activity = activity;
        maps = new HashMap<>(2);
        maps.put(Manifest.permission.SYSTEM_ALERT_WINDOW, REQUEST_CODE_SYSTEM_ALTER_WINDOW);
        maps.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
    }

    /**
     * SYSTEM_ALERT_WINDOW 悬浮窗权限需要特殊判断
     */
    public boolean checkPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Manifest.permission.SYSTEM_ALERT_WINDOW.equals(permission)) {
                return Settings.canDrawOverlays(activity);
            }
            return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    public void requestPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Manifest.permission.SYSTEM_ALERT_WINDOW.equals(permission)) {
                if (!Settings.canDrawOverlays(activity)) {
                    gotoOverLaySetting();
                }
                return;
            }
            if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                if (activity.shouldShowRequestPermissionRationale(permission)) {
                    requestPermissionLocal(permission);
                } else {
                    //第一次或永久拒绝
                    //如果用户点了永久拒绝，还是会触发onRequestPermissionsResult
                    activity.requestPermissions(new String[]{permission}, maps.get(permission));
                }

            }
        }
    }

    private void requestPermissionLocal(final String permission) {
        String tip = "";
        if (Manifest.permission.SYSTEM_ALERT_WINDOW.equals(permission)) {
            tip = activity.getResources().getString(R.string.permission_need_system_alter_dialog);
        }else if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
            tip = activity.getResources().getString(R.string.permission_need_write_external_storage);
        }

        final Dialog dialog = DialogFactory.getDialog(activity, tip);
        dialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activity.requestPermissions(new String[]{permission}, maps.get(permission));
                }
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String tip = null;
        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE) {
            if (Manifest.permission.SYSTEM_ALERT_WINDOW.equals(permissions[0])) {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    tip = activity.getResources().getString(R.string.permission_get_write_external_storage_failed);
                }
            }
        }
        if (tip != null) {
            gotoPermissionSetting(tip);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void gotoOverLaySetting() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" +activity.getPackageName()));
        activity.startActivityForResult(intent, REQUEST_CODE_SYSTEM_ALTER_WINDOW);
    }

    private void gotoPermissionSetting(String tip) {
        final Dialog dialog = DialogFactory.getDialog(activity, tip);
        dialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
                String packageName = activity.getPackageName();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + packageName));
                activity.startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void destroy() {
        activity = null;
        if (maps != null) {
            maps.clear();
            maps = null;
        }
    }

}
