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
import android.util.Log;
import android.util.SparseArray;
import android.widget.RemoteViews;

import com.zhq.devtools.databinding.ActivityMainBinding;
import com.zhq.toolslib.NotificationUtils;

import java.io.File;


public class MainActivity extends Activity {

    private com.zhq.devtools.databinding.ActivityMainBinding binding;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnAndroidView.setOnClickListener(v -> {
            startActivity(new Intent(this, AndroidBasicActivity.class));
        });
        binding.btnMedia.setOnClickListener(v -> {
            startActivity(new Intent(this, MediaTestActivity.class));
        });
        binding.btnNotification.setOnClickListener(v -> {
            startActivity(new Intent(this, NotificationActivity.class));
        });
        binding.btnCameraX.setOnClickListener(v -> {
            startActivity(new Intent(this, TestCameraXActivity.class));
        });
        binding.btnDatabase.setOnClickListener(v -> {
            startActivity(new Intent(this, TestDatabaseActivity.class));
        });

    }


}
