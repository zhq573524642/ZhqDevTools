package com.zhq.devtools.ui.media;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.zhq.devtools.adapter.MainMenuListAdapter;

import java.util.Arrays;

public class AndroidMediaActivity extends AppCompatActivity {
    private static final String TAG = "MediaTestActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSION_CODE = 1001;
    private static final int REQUEST_VIDEO_CAPTURE = 2;

    private com.zhq.devtools.databinding.ActivityAndroidMediaBinding binding;
    private String[] itemNameArray = {"拍照与图片", "音频", "视频","打印机"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.zhq.devtools.databinding.ActivityAndroidMediaBinding.inflate(getLayoutInflater());
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
                switch (position) {
                    case 0:
                        startActivity(new Intent(AndroidMediaActivity.this, AndroidPictureActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(AndroidMediaActivity.this, AndroidAudioActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(AndroidMediaActivity.this, AndroidVideoActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(AndroidMediaActivity.this,AndroidPrintActivity.class));
                        break;
                }
            }
        });
    }


}