package com.zhq.devtools.widget.material;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.AppBarLayout;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/26 14:47
 * Description
 */
public class FooterBehaviorAppbar extends CoordinatorLayout.Behavior<View> {


    public FooterBehaviorAppbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        float translationY = Math.abs(dependency.getY());
        child.setTranslationY(translationY);
        return true;
    }
}
