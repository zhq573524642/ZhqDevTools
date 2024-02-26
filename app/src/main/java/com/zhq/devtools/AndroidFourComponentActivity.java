package com.zhq.devtools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.zhq.devtools.base.BaseActivity;
import com.zhq.devtools.databinding.ActivityAndroidFourComponentBinding;
import com.zhq.devtools.ui.service.TestService;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/12/4 11:16
 * Description
 */
public class AndroidFourComponentActivity extends BaseActivity<ActivityAndroidFourComponentBinding> {

    private static final String TAG = "AndroidFourComponentAct";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_android_four_component;
    }

    @Override
    protected void initView() {
        Intent intent = new Intent(this, TestService.class);
        intent.putExtra("service_param", "111111111");
        mBinding.btnStartService.setOnClickListener(v -> {

            startService(intent);
        });
        mBinding.btnStopService.setOnClickListener(v -> {
//            Intent intent = new Intent(this, TestService.class);
//            intent.putExtra("service_param", "111111111");
            stopService(intent);
        });
        mBinding.btnBindService.setOnClickListener(v -> {
//            Intent intent = new Intent(this, TestService.class);
//            intent.putExtra("service_param", "22222222");
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        });
        mBinding.btnUnbindService.setOnClickListener(v -> {
            unbindService(serviceConnection);
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "====onServiceConnected: ComponentName："+name+"==IBinder："+service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "====onServiceDisconnected: ComponentName："+name);
        }
    };
}
