package com.zhq.devtools.widget.viewpager;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/20 15:53
 * Description
 */
public class AlphaPageTransformer implements ViewPager.PageTransformer {
    private ViewPager mViewPager;

    private static final float MIN_ALPHA = 0.5f;

    public AlphaPageTransformer(ViewPager viewPager) {
        mViewPager = viewPager;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        page.setScaleX(1.0f);
        page.setScaleY(1.0f);
        if (position < -1 || position > 1) {
            page.setAlpha(MIN_ALPHA);
        } else {
            //不透明->半透明
            if (position < 0) {//[0,-1]
                page.setAlpha(MIN_ALPHA + (1 + position) * (1 - MIN_ALPHA));
            } else {//[1,0]
                //半透明->不透明
                page.setAlpha(MIN_ALPHA + (1 - position) * (1 - MIN_ALPHA));
            }
        }
    }
}
