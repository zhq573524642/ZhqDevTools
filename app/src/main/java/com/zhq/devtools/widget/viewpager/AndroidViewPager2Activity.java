package com.zhq.devtools.widget.viewpager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zhq.devtools.R;
import com.zhq.devtools.base.BaseActivity;
import com.zhq.devtools.databinding.ActivityAndroidViewPager2Binding;
import com.zhq.devtools.widget.DataConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AndroidViewPager2Activity extends BaseActivity<ActivityAndroidViewPager2Binding> {

    private List<String> imageList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_android_view_pager2;
    }

    @Override
    protected void initView() {
        imageList.clear();
        imageList.addAll(Arrays.asList(DataConstants.IMAGE_URL));
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(mContext, imageList);
        mBinding.viewPager.setAdapter(viewPager2Adapter);
        mBinding.viewPager.setOffscreenPageLimit(5);

    }
}