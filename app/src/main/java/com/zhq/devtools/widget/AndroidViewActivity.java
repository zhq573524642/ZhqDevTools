package com.zhq.devtools.widget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.zhq.devtools.databinding.ActivityAndroidViewBinding;
import com.zhq.devtools.widget.anim.AnimActivity;
import com.zhq.devtools.widget.flipview.view.FlipViewActivity;
import com.zhq.devtools.widget.progress.AndroidProgressActivity;
import com.zhq.devtools.widget.simple.AndroidSwitcherActivity;
import com.zhq.devtools.widget.touch.TouchTestActivity;
import com.zhq.devtools.widget.viewpager.AndroidViewPager2Activity;
import com.zhq.devtools.widget.viewpager.AndroidViewPagerActivity;

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
        binding.btnGeneralView.setOnClickListener(v -> {
            startActivity(new Intent(this,GeneralViewActivity.class));
        });
        binding.btnSwitcher.setOnClickListener(v -> {
            startActivity(new Intent(this, AndroidSwitcherActivity.class));
        });
        binding.btnAnimation.setOnClickListener(v -> {
            startActivity(new Intent(this, AnimActivity.class));
        });
        binding.btnViewPager.setOnClickListener(v -> {
            startActivity(new Intent(this, AndroidViewPagerActivity.class));
        });
        binding.btnViewPager2.setOnClickListener(v -> {
            startActivity(new Intent(this, AndroidViewPager2Activity.class));
        });
        binding.btnProgress.setOnClickListener(v -> {
            startActivity(new Intent(this, AndroidProgressActivity.class));
        });
        binding.btnDialog.setOnClickListener(v -> {
            startActivity(new Intent(this, CommonDialogActivity.class));
        });
        binding.btnNineGridView.setOnClickListener(v -> {
            startActivity(new Intent(this, NineImageGridActivity.class));
        });
        binding.btnTags3D.setOnClickListener(v -> {
            startActivity(new Intent(this, Tags3DActivity.class));
        });
        binding.btnTouchTest.setOnClickListener(v -> {
            startActivity(new Intent(this, TouchTestActivity.class));
        });
        binding.btnMaterialDesign.setOnClickListener(v -> {
            startActivity(new Intent(this, MaterialDesignActivity.class));
        });
        binding.btnAndroidPicker.setOnClickListener(v -> {
            startActivity(new Intent(this, AndroidPickerActivity.class));
        });
        binding.btnCustomFlipView.setOnClickListener(v -> {
            startActivity(new Intent(this, FlipViewActivity.class));
        });
    }
}