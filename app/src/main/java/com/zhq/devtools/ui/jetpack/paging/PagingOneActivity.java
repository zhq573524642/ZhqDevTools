package com.zhq.devtools.ui.jetpack.paging;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ActivityPagingOneBinding;

public class PagingOneActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityPagingOneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = (ActivityPagingOneBinding) DataBindingUtil.setContentView(this, R.layout.activity_paging_one);
        initView();
    }

    private void initView() {

    }
}