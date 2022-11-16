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
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.zhq.devtools.databinding.ActivityMainBinding;
import com.zhq.toolslib.NotificationUtils;


public class MainActivity extends Activity {

    private com.zhq.devtools.databinding.ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //创建NotificationManager
        NotificationUtils.getInstance().initNotificationManager(MainActivity.this);
        binding.btnNotificationNormal.setOnClickListener(v -> {
            NotificationUtils.getInstance().createNotificationChannel("channel_1",
                    "normal_channel", NotificationManager.IMPORTANCE_LOW,
                    "普通通知", false);
            NotificationUtils.getInstance().createNotificationForNormal(MainActivity.this,
                    1, new Intent(this,NotificationTargetActivity.class),
                    "普通通知标题", "这是普通通知啦啦啦啦啦",
                    "channel_1", R.mipmap.ic_launcher,
                    BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher),
                    NotificationCompat.PRIORITY_DEFAULT, false);
        });
        binding.btnNotificationHigh.setOnClickListener(v -> {
            NotificationUtils.getInstance().createNotificationChannel("channel_2",
                    "high_channel", NotificationManager.IMPORTANCE_HIGH,
                    "重要通知", true);
            NotificationUtils.getInstance().createNotificationForHigh(MainActivity.this,
                    2, "channel_2",
                    new Intent(this,NotificationTargetActivity.class), "重要通知标题", "这是重要通知啦啦啦啦",
                    R.mipmap.ic_launcher,
                    BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher),
                    NotificationCompat.PRIORITY_HIGH, true, 66,
                    R.mipmap.ic_launcher_round, "点击看看");
        });
    }
}
