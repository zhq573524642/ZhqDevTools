package com.zhq.devtools.widget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.zhq.devtools.R;
import com.zhq.devtools.base.BaseActivity;
import com.zhq.devtools.databinding.ActivityBottomSheetBinding;

public class BottomSheetActivity extends BaseActivity<ActivityBottomSheetBinding> {

    private static final String TAG = "BottomSheetActivity";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_bottom_sheet;
    }

    @Override
    protected void initView() {

        BottomSheetBehavior.from(mBinding.layoutBottomSheet.llBottomSheetContent)
                .addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        switch (newState) {
                            case BottomSheetBehavior.STATE_COLLAPSED://折叠状态
                                Log.d(TAG, "===STATE_COLLAPSED");
                                break;
                            case BottomSheetBehavior.STATE_DRAGGING://过渡状态，此时用户正在向上或者向下拖动bottom sheet
                                Log.d(TAG, "===STATE_DRAGGING");
                                break;
                            case BottomSheetBehavior.STATE_EXPANDED:
                                Log.d(TAG, "===STATE_EXPANDED");
                                break;
                            case BottomSheetBehavior.STATE_HIDDEN://隐藏状态。默认是false，可通过app:behavior_hideable属性设置是否能隐藏
                                Log.d(TAG, "===STATE_HIDDEN");
                                break;
                            case BottomSheetBehavior.STATE_SETTLING:////视图从脱离手指自由滑动到最终停下的这一小段时间
                                Log.d(TAG, "===STATE_SETTLING");
                                break;
                        }

                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                        Log.d(TAG, "===onSlide: "+slideOffset);
                    }
                });
    }
}