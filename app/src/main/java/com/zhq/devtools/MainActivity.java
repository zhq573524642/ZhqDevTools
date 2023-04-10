// Copyright 2016 Google Inc.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//      http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.zhq.devtools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zhq.devtools.adapter.MainMenuListAdapter;
import com.zhq.devtools.databinding.ActivityMainBinding;
import com.zhq.devtools.ui.basic.AndroidBasicActivity;
import com.zhq.devtools.ui.jetpack.JetpackActivity;
import com.zhq.devtools.ui.media.AndroidMediaActivity;
import com.zhuiq.fileslib.PathsUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private List<String> itemList = new ArrayList<>();
    private com.zhq.devtools.databinding.ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = (ActivityMainBinding) DataBindingUtil.setContentView(this, R.layout.activity_main);
        String[] itemName = getResources().getStringArray(R.array.array_main_menu);
        itemList.addAll(Arrays.asList(itemName));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainMenuListAdapter mainMenuListAdapter = new MainMenuListAdapter(itemList);
        binding.recyclerView.setAdapter(mainMenuListAdapter);
        mainMenuListAdapter.setOnRecyclerViewItemClickListener(new MainMenuListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position){
                    case 0://Android基础
                        startActivity(new Intent(MainActivity.this, AndroidBasicActivity.class));
                        break;
                    case 1://四大组件
                        break;
                    case 2://数据库
                        startActivity(new Intent(MainActivity.this, TestDatabaseActivity.class));
                        break;
                    case 3://多媒体
                        startActivity(new Intent(MainActivity.this, AndroidMediaActivity.class));
                        break;
                    case 4://View相关
                        startActivity(new Intent(MainActivity.this,AndroidViewActivity.class));
                        break;
                    case 5://Jetpack
                        startActivity(new Intent(MainActivity.this, JetpackActivity.class));
                        break;
                }
            }
        });
    }


}
