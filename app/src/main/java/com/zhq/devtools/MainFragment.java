package com.zhq.devtools;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhq.devtools.databinding.FragmentMainBinding;
import com.zhq.toolslib.notification.NotificationUtils;


public class MainFragment extends Fragment {

    private com.zhq.devtools.databinding.FragmentMainBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        binding.btnStartLogin.setOnClickListener(v -> {
//            Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_loginFragment);//跳不了
            Bundle bundle = new MainFragmentArgs.Builder()
                    .setUserName("张三")
                    .setAge(20)
                    .build().toBundle();
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_loginFragment,bundle);
        });

        binding.btnSendPendingIntentNotification.setOnClickListener(v -> {
            NotificationUtils.getInstance().initNotificationManager(getActivity())
                    .setNotificationId(1)
                    .createNotificationChannel("channel_nav_1",
                            "channel_nav_noti","测试Navigation的推送",
                            NotificationManager.IMPORTANCE_HIGH,true)
                    .setPendingIntent(getPendingIntent())
                    .setContentTitle("这是通知aaaa")
                    .setSmallIcon(R.mipmap.ic_test_icon)
                    .isAutoCancel(true)
                    .createNormalNotification();
        });

    }

    private PendingIntent getPendingIntent() {
        Bundle bundle = new Bundle();
        bundle.putString("param","这是通知传的参数");
        return Navigation.findNavController(getActivity(), R.id.nav_host_fragment_container)
                .createDeepLink()
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.pendingIntentFragment)
                .setArguments(bundle).createPendingIntent();
    }


}