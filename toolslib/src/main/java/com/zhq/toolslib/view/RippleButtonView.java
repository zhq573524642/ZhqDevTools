package com.zhq.toolslib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.zhq.toolslib.R;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/11/15 10:32
 * Description
 */
public class RippleButtonView extends androidx.appcompat.widget.AppCompatButton {

    private int TAP_TIMEOUT;                         //判断点击和长按的时间
    private static int DIFFUSE_GAP = 10;                  //扩散半径增量
    private static final int INVALIDATE_DURATION = 15;     //每次刷新的时间间隔
    private long downTime = 0;                 //按下的时间
    private int rippleColor;
    private int rippleBgColor;
    private Paint rippleColorPaint;
    private Paint rippleBgColorPaint;
    private int viewWidth, viewHeight;                   //控件宽高
    private int pointX, pointY;                          //控件原点坐标（左上角）
    private int maxRadio;                             //扩散的最大半径
    private int shaderRadio;                        //扩散的半径
    private int eventX, eventY;
    private boolean isPushButton;


    public RippleButtonView(Context context) {
        this(context, null);
    }

    public RippleButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleButtonView);
        rippleColor = typedArray.getColor(R.styleable.RippleButtonView_rippleColor, Color.WHITE);
        rippleBgColor = typedArray.getColor(R.styleable.RippleButtonView_rippleBgColor, Color.TRANSPARENT);
        typedArray.recycle();
        init();
    }

    private void init() {
        rippleColorPaint = new Paint();
        rippleBgColorPaint = new Paint();
        rippleColorPaint.setColor(rippleColor);
        rippleBgColorPaint.setColor(rippleBgColor);
        TAP_TIMEOUT = ViewConfiguration.getLongPressTimeout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (downTime == 0) downTime = SystemClock.elapsedRealtime();
                eventX = (int) event.getX();
                eventY = (int) event.getY();
                //计算最大半径
                countMaxRadius();
                isPushButton = true;
                postInvalidateDelayed(INVALIDATE_DURATION);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (SystemClock.elapsedRealtime() - downTime < TAP_TIMEOUT) {
                    DIFFUSE_GAP = 30;
                    postInvalidate();
                }else {
                    clearData();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!isPushButton)return;
        //绘制按下后的整个背景
        canvas.drawRect(pointX, pointY, pointX + viewWidth, pointY + viewHeight, rippleBgColorPaint);
        canvas.save();
        //绘制扩散圆形背景
        canvas.clipRect(pointX, pointY, pointX + viewWidth, pointY + viewHeight);
        canvas.drawCircle(eventX, eventY, shaderRadio, rippleColorPaint);
        canvas.restore();
        //直到半径等于最大半径
        if(shaderRadio < maxRadio){
            postInvalidateDelayed(INVALIDATE_DURATION,
                    pointX, pointY, pointX + viewWidth, pointY + viewHeight);
            shaderRadio += DIFFUSE_GAP;
        }else{
            clearData();
        }
    }

    private void clearData() {
        isPushButton=false;
        downTime=0;
        DIFFUSE_GAP=10;
        shaderRadio=0;
        postInvalidate();
    }

    private void countMaxRadius() {
        if (viewWidth > viewHeight) {
            if (eventX < viewWidth / 2) {
                maxRadio = viewWidth - eventX;
            } else {
                maxRadio = viewWidth / 2 + eventX;
            }
        } else {
            if (eventY < viewHeight / 2) {
                maxRadio = viewHeight - eventY;
            } else {
                maxRadio = viewHeight / 2 + eventY;
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viewWidth = w;
        this.viewHeight = h;
    }
}
