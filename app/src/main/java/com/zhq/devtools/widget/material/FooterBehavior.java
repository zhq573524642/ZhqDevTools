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

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/26 14:47
 * Description
 */
public class FooterBehavior extends CoordinatorLayout.Behavior<View> {

    private int directionChange;

    public FooterBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (dy > 0 && directionChange < 0 || dy < 0 && directionChange > 0) {
            child.animate().cancel();
            directionChange = 0;
        }
        directionChange += dy;
        if (directionChange > child.getHeight() && child.getVisibility() == View.VISIBLE) {
            hide(child);
        } else if (directionChange < 0 && child.getVisibility() == View.GONE) {
            show(child);
        }
    }

    private void show(View view) {
        view.animate().translationY(0)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                    }
                })
                .start();
    }

    private void hide(View view) {
        view.animate().translationY(view.getHeight())
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.GONE);
                    }
                })
                .start();
    }
}
