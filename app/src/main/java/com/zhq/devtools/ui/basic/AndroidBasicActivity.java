package com.zhq.devtools.ui.basic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.zhq.devtools.NFCShareActivity;
import com.zhq.devtools.NotificationActivity;
import com.zhq.devtools.adapter.MainMenuListAdapter;
import com.zhq.devtools.databinding.ActivityAndroidBasicBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AndroidBasicActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityAndroidBasicBinding binding;
    private String[] itemNameArray={"Android系统分享","通知"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAndroidBasicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainMenuListAdapter mainMenuListAdapter = new MainMenuListAdapter(Arrays.asList(itemNameArray));
        binding.recyclerView.setAdapter(mainMenuListAdapter);
        mainMenuListAdapter.setOnRecyclerViewItemClickListener(new MainMenuListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position){
                    case 0://Android分享
                        startActivity(new Intent(AndroidBasicActivity.this,AndroidShareActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(AndroidBasicActivity.this, NotificationActivity.class));
                        break;
                }
            }
        });

    }
}