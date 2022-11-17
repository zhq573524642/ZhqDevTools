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
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.zhq.devtools.databinding.ActivityMainBinding;
import com.zhq.toolslib.NotificationUtils;

import java.io.File;


public class MainActivity extends Activity {

    private com.zhq.devtools.databinding.ActivityMainBinding binding;
    private int progress = 0;
    private String bigText = "A notification is a message that Android displays outside your app's UI to provide the user with reminders, communication from other people, or other timely information from your app. Users can tap the notification to open your app or take an action directly from the notification.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //创建NotificationManager
        binding.btnNotificationNormal.setOnClickListener(v -> {
            handleNotificationConfigAndCreate();
        });
        binding.btnNotificationHigh.setOnClickListener(v -> {

        });

        binding.btnDownloadNotification.setOnClickListener(v -> {


        });
        binding.btnBigTextNotification.setOnClickListener(v -> {

        });

        binding.btnBigPicNotification.setOnClickListener(v -> {

        });

        binding.btnCustomNotification.setOnClickListener(v -> {
            handleNotificationConfigAndCreate();
        });

    }

    private void handleNotificationConfigAndCreate() {
        //PendingIntent
        Intent intent = new Intent(this, NotificationTargetActivity.class);
        int flag = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flag = PendingIntent.FLAG_IMMUTABLE;
        } else {
            flag = PendingIntent.FLAG_UPDATE_CURRENT;
        }
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, flag);
        //Action
        NotificationCompat.Action action = new NotificationCompat.Action(R.mipmap.ic_launcher, "点击看看", pi);
        //RemoteViews
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_custom_notification_remote_view);
        remoteViews.setTextViewText(R.id.tv_remote_text, "这是自定义通知");
        Intent intentStart = new Intent(MainActivity.this, NotificationTargetActivity.class);
        PendingIntent startPendingIntent = PendingIntent.getBroadcast(this, 0, intentStart, flag);
        remoteViews.setOnClickPendingIntent(R.id.btn_start, startPendingIntent);
        Intent intentStop = new Intent(MainActivity.this, NotificationTargetActivity.class);
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(this, 0, intentStop, flag);
        remoteViews.setOnClickPendingIntent(R.id.btn_stop, stopPendingIntent);

        NotificationUtils.getInstance()
                .initNotificationManager(MainActivity.this)
                .createNotificationChannel("channel_1", "normal_channel", "普通通知", NotificationManager.IMPORTANCE_LOW, false)
                .setNotificationId(1)
                .setContentTitle("普通通知标题")
                .setContentText("这是普通通知")
                .setSubText("子标题")
                .isAutoCancel(true)
                .isShowWhen(true)
                .setWhen(System.currentTimeMillis())
                .setNotificationDeskNumber(99)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_test_img))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setNotificationAction(action)
                .setPendingIntent(pi)
                .setDeletePendingIntent(pi)
                .setFullScreenPendingIntent(pi)
                .isFullScreenIntentHighPriority(false)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setLightArgb(Color.GREEN)
                .setLightOnMs(1000)
                .setLightOffMs(1000)
                .setSound(Uri.fromFile(new File("....")))
                .setVibrate(new long[]{0, 1000, 1000, 1000})
                .setProgressMax(100)
                .setProgressCurrent(50)
                .createNormalNotification()
                .createBigTextNotification("长文本。。。")
                .createBigPictureNotification(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .createProgressNotification()
                .updateProgressNotification()
                .createCustomNotification(remoteViews);
    }
}
