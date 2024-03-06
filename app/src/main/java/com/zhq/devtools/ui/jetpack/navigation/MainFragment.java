package com.zhq.devtools.ui.jetpack.navigation;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;

import androidx.navigation.NavDeepLinkRequest;
import androidx.navigation.Navigation;

import com.zhq.devtools.R;
import com.zhq.devtools.databinding.FragmentMainBinding;
import com.zhq.devtools.ui.jetpack.viewmodel.MainViewModel;
import com.zhq.devtoolslib.base.BaseFragment;
import com.zhq.toolslib.notification.NotificationUtils;


public class MainFragment extends BaseFragment<FragmentMainBinding, MainViewModel> {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData() {
        VMProviders(MainViewModel.class);
        initView();
    }

    private void initView() {
        mBinding.btnStartLogin.setOnClickListener(v -> {
//            Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_loginFragment);//跳不了
            Bundle bundle = new MainFragmentArgs.Builder()
                    .setUserName("张三")
                    .setAge(20)
                    .build().toBundle();
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_loginFragment, bundle);

            //打开DeepLink
            NavDeepLinkRequest navDeepLinkRequest = NavDeepLinkRequest.Builder.fromUri(Uri.parse("xxxx")).build();
            Navigation.findNavController(v).navigate(navDeepLinkRequest);
        });

        mBinding.btnSendPendingIntentNotification.setOnClickListener(v -> {
            NotificationUtils.getInstance().initNotificationManager(getActivity())
                    .setNotificationId(1)
                    .createNotificationChannel("channel_nav_1",
                            "channel_nav_noti", "测试Navigation的推送",
                            NotificationManager.IMPORTANCE_HIGH, true)
                    .setPendingIntent(getPendingIntent())
                    .setContentTitle("这是通知aaaa")
                    .setSmallIcon(R.mipmap.ic_test_icon)
                    .isAutoCancel(true)
                    .createNormalNotification();
        });

    }

    private PendingIntent getPendingIntent() {
        Bundle bundle = new Bundle();
        bundle.putString("username", "张三");
        bundle.putInt("age",28);
        bundle.putString("sex","男");
        bundle.putString("className","1304班");
        return Navigation.findNavController(getActivity(), R.id.nav_host_fragment_container)
                .createDeepLink()
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.pendingIntentFragment)
                .setArguments(bundle).createPendingIntent();
    }


}