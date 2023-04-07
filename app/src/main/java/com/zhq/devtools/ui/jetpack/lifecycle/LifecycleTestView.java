package com.zhq.devtools.ui.jetpack.lifecycle;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/3/30 14:15
 * Description
 */
public class LifecycleTestView extends androidx.appcompat.widget.AppCompatImageView implements LifecycleObserver {
    private static final String TAG = "LifecycleTestView";
    public LifecycleTestView(@NonNull Context context) {
        super(context);
    }

    public LifecycleTestView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void startLoad(){
        Log.d(TAG, "===startLoad: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void stopLoad(){
        Log.d(TAG, "===stopLoad: ");
    }
}
