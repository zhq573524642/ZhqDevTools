package com.zhq.toolslib.window.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;

import com.zhq.toolslib.R;


public class FixedHeightListView extends RecyclerView {

    private int mMaxHeight = 0;

    public FixedHeightListView(Context context) {
        super(context);
        init(context, null);
    }

    public FixedHeightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FixedHeightListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    public void setMaxHeight(int maxHeight) {
        this.mMaxHeight = maxHeight;
        invalidate();
    }

    private void init(@SuppressWarnings("UnusedParameters") Context context, AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.FixedHeightListView);
        mMaxHeight = attributes.getDimensionPixelOffset(R.styleable.FixedHeightListView_maxHeight, dp2px(300));
        attributes.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public int dp2px(int dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
