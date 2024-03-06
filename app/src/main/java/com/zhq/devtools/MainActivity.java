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

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.zhq.devtools.adapter.MainMenuListAdapter;
import com.zhq.devtools.databinding.ActivityMainBinding;
import com.zhq.devtools.ui.basic.AndroidBasicActivity;
import com.zhq.devtools.ui.java_basic.JavaBasicActivity;
import com.zhq.devtools.ui.jetpack.JetpackActivity;
import com.zhq.devtools.ui.media.AndroidMediaActivity;
import com.zhq.devtools.widget.AndroidViewActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import gaode.MapInfoActivity;


public class MainActivity extends AppCompatActivity {

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
                switch (position) {
                    case 0://Android基础
                        startActivity(new Intent(MainActivity.this, AndroidBasicActivity.class));
                        break;
                    case 1://四大组件
                        startActivity(new Intent(MainActivity.this,AndroidFourComponentActivity.class));
                        break;
                    case 2://数据库
                        startActivity(new Intent(MainActivity.this, TestDatabaseActivity.class));
                        break;
                    case 3://多媒体
                        startActivity(new Intent(MainActivity.this, AndroidMediaActivity.class));
                        break;
                    case 4://View相关
                        startActivity(new Intent(MainActivity.this, AndroidViewActivity.class));
                        break;
                    case 5://Jetpack
                        startActivity(new Intent(MainActivity.this, JetpackActivity.class));
                        break;
                    case 6:
                        PermissionX.init(MainActivity.this)
                                .permissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION)
                                .request(new RequestCallback() {
                                    @Override
                                    public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                                        for (int i = 0; i < deniedList.size(); i++) {
                                            Log.d(TAG, "===deniedList: " + deniedList.get(i));
                                        }
                                        if (allGranted) {
                                            if (isLocationEnabled()) {
                                                startActivity(new Intent(MainActivity.this, MapInfoActivity.class));
                                            } else {

                                            }
                                        }
                                    }
                                });
                        String sHA1 = sHA1(getApplicationContext());
                        Log.d(TAG, "===SHA1：: "+sHA1);
                        break;
                    case 7://java基础
                        JavaBasicActivity.start(MainActivity.this);
                        break;
                }
            }
        });
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean isLocationEnabled() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                || lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


}
