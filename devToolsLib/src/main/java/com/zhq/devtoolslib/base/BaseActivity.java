package com.zhq.devtoolslib.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;



/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/21 10:42
 * Description
 */
public abstract class BaseActivity<T extends ViewDataBinding, M extends BaseViewModel> extends AppCompatActivity {

    public T mBinding;
    public M mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
    }

    protected void VMProviders(@NonNull Class<M> modelClass) {
        if (modelClass != null) {
            mViewModel = (M) ViewModelProviders.of(this).get(modelClass);
        }
    }

    protected abstract int getLayoutId();
}
