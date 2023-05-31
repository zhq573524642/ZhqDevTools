package com.zhq.devtools.widget.material;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zhq.devtools.R;
import com.zhq.devtools.base.BaseActivity;
import com.zhq.devtools.databinding.ActivityCoordinatorLayoutBinding;
import com.zhq.toolslib.density.StatusBarUtils;

public class CoordinatorLayoutActivity extends BaseActivity<ActivityCoordinatorLayoutBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_coordinator_layout;
    }

    @Override
    protected void initView() {
        StatusBarUtils.setTransparentStatusBar(this);
        setSupportActionBar(mBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mBinding.collapsingToolbarLayout.setTitle("这是标题");
    }
}