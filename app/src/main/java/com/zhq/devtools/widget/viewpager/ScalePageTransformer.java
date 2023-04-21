package com.zhq.devtools.widget.viewpager;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/20 15:53
 * Description
 */
public class ScalePageTransformer implements ViewPager.PageTransformer {
    private ViewPager mViewPager;

    private static final float MIN_SCALE = 0.70f;

    public ScalePageTransformer(ViewPager viewPager) {
        mViewPager = viewPager;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        page.setAlpha(1.0f);
        if (position < -1 || position > 1) {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        } else if (position <= 1) {
            //不透明->半透明
            if (position < 0) {
                float scaleX = 1 + 0.3f * position;
                page.setScaleX(scaleX);
                page.setScaleY(scaleX);
            } else {
                float scaleX = 1 - 0.3f * position;
                page.setScaleX(scaleX);
                page.setScaleY(scaleX);
            }
        }
    }
}
