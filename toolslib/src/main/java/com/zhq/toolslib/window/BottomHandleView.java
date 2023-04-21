package com.zhq.toolslib.window;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/3/29 13:43
 * Description
 */
public class BottomHandleView extends BottomSheetDialog{

    public BottomHandleView(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
