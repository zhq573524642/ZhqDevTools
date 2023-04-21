package com.zhq.devtools.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewbinding.ViewBinding;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/20 14:37
 * Description
 */
public abstract class BaseActivity<T> extends AppCompatActivity {

    protected T mBinding;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mBinding = (T) DataBindingUtil.setContentView(this, getLayoutId());
        initView();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();
}
