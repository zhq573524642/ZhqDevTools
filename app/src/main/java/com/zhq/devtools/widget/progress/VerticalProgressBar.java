package com.zhq.devtools.widget.progress;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zhq.devtools.R;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/11/13 10:19
 * Description
 */
public class VerticalProgressBar extends LinearLayout {

    private boolean isShowTopProgressText = true;
    private TextView tvTopProgress;
    private ProgressBar progressBar;
    private View view;

    public VerticalProgressBar(Context context) {
        super(context);
        init(context);
    }

    public VerticalProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VerticalProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        if (getChildCount() > 0) {
            removeAllViews();
        }
        view = LayoutInflater.from(context).inflate(R.layout.layout_vertical_progress_bar, null);
        findView(view);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        addView(view);

    }


    private void findView(View view) {
        tvTopProgress = view.findViewById(R.id.tv_progress);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    public void setShowTopProgressText(boolean showTopProgressText) {
        isShowTopProgressText = showTopProgressText;
    }

    public void setProgressTextSize(int spSize) {
        if (tvTopProgress == null) return;
        tvTopProgress.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }

    public void setProgressTextColor(int colorRes){
        if (tvTopProgress==null)return;
        tvTopProgress.setTextColor(getResources().getColor(colorRes));
    }

    public void setProgress(int progress){
        if (progressBar==null)return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(progress,true);
        }else {
            progressBar.setProgress(progress);
        }
        if (isShowTopProgressText){
            if (tvTopProgress==null)return;
            tvTopProgress.setText(progress+"%");
        }
    }

    public void setProgressMax(int max){
        if (progressBar==null)return;
        progressBar.setMax(max);
    }

}
