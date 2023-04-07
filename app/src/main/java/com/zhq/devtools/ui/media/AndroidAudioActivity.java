package com.zhq.devtools.ui.media;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ActivityAndroidAudioBinding;

public class AndroidAudioActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityAndroidAudioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAndroidAudioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
         binding.btnAudioFocus.setOnClickListener(v -> {
               startActivity(new Intent(this,AudioFocusActivity.class));
         });
    }
}