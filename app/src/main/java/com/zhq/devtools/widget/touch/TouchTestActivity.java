package com.zhq.devtools.widget.touch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zhq.devtools.R;
import com.zhq.devtools.base.BaseActivity;
import com.zhq.devtools.databinding.ActivityTouchTestBinding;

public class TouchTestActivity extends BaseActivity<ActivityTouchTestBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_touch_test;
    }

    private static final String TAG = "TouchTestActivity";

    @Override
    protected void initView() {
        mBinding.scrollView.setSmoothScrollingEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBinding.scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    Log.d(TAG, "===onScrollChange: " + scrollY + "==" + oldScrollY);

                }
            });
        }
    }
}