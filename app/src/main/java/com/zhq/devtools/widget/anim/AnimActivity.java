package com.zhq.devtools.widget.anim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.zhq.devtools.R;
import com.zhq.devtools.base.BaseActivity;
import com.zhq.devtools.databinding.ActivityAnimBinding;

import java.util.Arrays;

public class AnimActivity extends BaseActivity<ActivityAnimBinding> {

    private String[] interpolateArr = {"LinearInterpolator",
            "AccelerateInterpolator",
            "DecelerateInterpolator",
            "AccelerateDecelerateInterpolator",
            "AnticipateInterpolator",
            "OvershootInterpolator",
            "AnticipateOvershootInterpolator",
            "BounceInterpolator",
            "CycleInterpolator"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_anim;
    }

    @Override
    protected void initView() {
        mBinding.btnStartAnim.setOnClickListener(v -> {
            setAnimInterpolate();
        });
    }

    private void setAnimInterpolate() {
        OptionsPickerView<Object> optionsPickerView = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                startAnim(options1);
            }
        }).build();
        optionsPickerView.setPicker(Arrays.asList(interpolateArr));
        optionsPickerView.show(true);
    }

    private void startAnim(int type) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_translation);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        switch (type) {
            case 0:
                animation.setInterpolator(new LinearInterpolator());
                break;
            case 1:
                animation.setInterpolator(new AccelerateInterpolator());
                break;
            case 2:
                animation.setInterpolator(new DecelerateInterpolator());
                break;
            case 3:
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
                break;
            case 4:
                animation.setInterpolator(new AnticipateInterpolator());
                break;
            case 5:
                animation.setInterpolator(new OvershootInterpolator());
                break;
            case 6:
                animation.setInterpolator(new AnticipateOvershootInterpolator());
                break;
            case 7:
                animation.setInterpolator(new BounceInterpolator());
                break;
            case 8:
                animation.setInterpolator(new CycleInterpolator(2));
                break;
        }

        mBinding.viewAnimTarget.startAnimation(animation);

    }

}