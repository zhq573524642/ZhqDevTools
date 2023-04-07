package com.zhq.devtools;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhq.devtools.databinding.FragmentPendingIntentBinding;

public class PendingIntentFragment extends Fragment {


    private com.zhq.devtools.databinding.FragmentPendingIntentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPendingIntentBinding.inflate(inflater);
        initView();
        return binding.getRoot();
    }

    //https://blog.csdn.net/stephen_sun_/article/details/123339025 DeepLink解析
    private void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String username = bundle.getString("username");
            int age = bundle.getInt("age");
            String sex = bundle.getString("sex");
            String className = bundle.getString("className");
            binding.tvTitle.setText("这是点击通知通过PendingIntent跳进来，参数：" +
                    "username:" + username
                    + "age:" + age
                    + "sex:" + sex
                    + "className:" + className);
        }
    }
}