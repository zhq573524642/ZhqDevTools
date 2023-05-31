package com.zhq.devtools.widget;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.palette.graphics.Palette;

import com.zhq.devtools.R;
import com.zhq.devtools.base.BaseActivity;
import com.zhq.devtools.databinding.ActivityMaterialDesignBinding;
import com.zhq.devtools.widget.material.CoordinatorLayoutActivity;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/26 10:20
 * Description
 */
public class MaterialDesignActivity extends BaseActivity<ActivityMaterialDesignBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_material_design;
    }

    @Override
    protected void initView() {
        mBinding.btnPalette.setOnClickListener(v -> {
            startActivity(new Intent(this, PaletteTestActivity.class));
        });
        mBinding.btnCoordinatorLayout.setOnClickListener(v -> {
            startActivity(new Intent(this, CoordinatorLayoutActivity.class));
        });

        ViewOutlineProvider viewOutlineProvider1 = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 30);
            }
        };
        ViewOutlineProvider viewOutlineProvider2 = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getWidth(), view.getHeight());
            }
        };
        mBinding.textView1.setOutlineProvider(viewOutlineProvider1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mBinding.textView1.setOutlineAmbientShadowColor(Color.GREEN);
            mBinding.textView1.setOutlineSpotShadowColor(Color.RED);
        }
        mBinding.textView2.setOutlineProvider(viewOutlineProvider2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mBinding.textView2.setOutlineAmbientShadowColor(Color.YELLOW);
            mBinding.textView2.setOutlineSpotShadowColor(Color.BLUE);
        }
    }
}
