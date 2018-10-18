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

import com.milog.test.myfloat.common.MiloLog;
import com.milog.test.myfloat.common.PermissionManager;
import com.milog.test.myfloat.services.FloatManagerService;
import com.milog.test.myfloat.view.MainView;

public class MainActivity extends Activity {

    private PermissionManager permissionManager;
    private FloatManagerService floatManagerService;
    private ServiceConnection serviceConnection;
    private boolean bindService;

    private MainView mainView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(getResources().getColor(R.color.mi_transparent));

        init();
    }

    private void init() {

        mainView = findViewById(R.id.main_view);
        permissionManager = new PermissionManager(this);

        mainView.setViewConnection(new MainView.ViewConnection() {
            @Override
            public void starFloat() {
                startFloatService();
            }
        });
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                floatManagerService = ((FloatManagerService.MyBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                MiloLog.i("mainActivity", name.toString());
            }
        };

    }

    private void startFloatService() {
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
            bindService = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }else {
            permissionManager.requestPermission(Manifest.permission.SYSTEM_ALERT_WINDOW);
        }

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
        MiloLog.i("onDestroy", "onDestroy");
        if (bindService) {
            floatManagerService = null;
            try{
                unbindService(serviceConnection);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        permissionManager.destroy();
        mainView.destroy();
    }
}
