package com.zhq.devtools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zhq.devtools.databinding.ActivityAndroidViewBinding;

public class AndroidViewActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityAndroidViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAndroidViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.textView.setText("As you know, Android provide shouldShowRequestPermissionRationale method " +
                "to indicate us if we should show a rationale dialog to explain to user why we need this permission." +
                " Otherwise user may deny the permissions we requested and checked never ask again option.");
    }
}