package com.zhq.devtoolslib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;




/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/21 13:39
 * Description
 */
public abstract class BaseFragment<T extends ViewDataBinding, M extends BaseViewModel> extends Fragment {

    protected T mBinding;
    protected M mViewModel;
    protected View mView;
    protected Context mContext;
    protected Activity mActivity;


    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mActivity = activity;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        mBinding = DataBindingUtil.bind(mView);
        initData();
        return mView;

    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected void VMProviders(@NonNull Class<M> modelClass) {
        if (modelClass != null) {
            mViewModel = (M) ViewModelProviders.of(this).get(modelClass);
        }
    }
}
