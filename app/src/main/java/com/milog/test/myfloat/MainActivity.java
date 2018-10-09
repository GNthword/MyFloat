package com.milog.test.myfloat;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.view.View;

import com.milog.test.myfloat.common.PermissionManager;
import com.milog.test.myfloat.services.FloatManagerService;

public class MainActivity extends Activity {

    private PermissionManager permissionManager;
    private FloatManagerService floatManagerService;
    private ServiceConnection serviceConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(getResources().getColor(R.color.mi_transparent));

        init();
    }

    private void init() {

        permissionManager = new PermissionManager(this);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (floatManagerService != null) {
                    floatManagerService.showFloat();
                    return;
                }
                if (permissionManager.checkPermission(Manifest.permission.SYSTEM_ALERT_WINDOW)) {
                    Intent intent = new Intent(MainActivity.this, FloatManagerService.class);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        startForegroundService(intent);
//                    }else {
//                        startService(intent);
//                    }
                    bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                }else {
                    permissionManager.requestPermission(Manifest.permission.SYSTEM_ALERT_WINDOW);
                }
            }
        });

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                floatManagerService = ((FloatManagerService.MyBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        permissionManager.destroy();
        floatManagerService = null;
        unbindService(serviceConnection);
    }
}
