package com.zhq.devtools.widget.viewpager;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/20 15:26
 * Description
 */
public class MyViewPageTransformer implements ViewPager.PageTransformer {

    private ViewPager mViewPager;
    public MyViewPageTransformer(ViewPager viewPager) {
        mViewPager=viewPager;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {

    }
}
