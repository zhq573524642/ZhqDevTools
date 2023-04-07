package com.zhq.toolslib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;



/**
 * Created by Huiqiang Zhang
 * on 2019/3/5.
 */

public class DensityUtil {



    /**
     * 单位转化 dp转px
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context,final float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dpValue * scale);
    }


    /**
     * 描述：px转换为dp
     * @param context
     * @param pxValue
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(pxValue / scale);
    }

    /**
     *
     * 描述：px转换为sp
     * @param context
     * @param pxValue
     */
    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return Math.round(pxValue / scale);
    }

    /**
     *
     * 描述：sp转换为px
     * @param context
     * @param spValue
     */
    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return Math.round(spValue * scale);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
//    public static int sp2px(Context context, float spValue) {
//        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
//        return (int) (spValue * fontScale + 0.5f);
//    }

    /**
     * 获取屏幕的宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        Resources res = context.getResources();
        return res.getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度 包含状态栏的高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        Resources res = context.getResources();
        return res.getDisplayMetrics().heightPixels;
    }

    /**
     * 获取View的高(View宽度match_parent，设置margin)
     * @param context
     * @param marginSize
     * @param ratioWidth
     * @param ratioHeight
     * @return
     */
    public  int getViewHeight(Context context, int marginSize, int ratioWidth, int ratioHeight){
        Resources res = context.getResources();
        int screenWidth = res.getDisplayMetrics().widthPixels;
        final float scale =res.getDisplayMetrics().density;
        int margin = (int) (marginSize * scale + 0.5f);
        int imageWidth = screenWidth - margin;
        int imageHeight = (imageWidth * ratioHeight) / ratioWidth;
        return imageHeight;
    }


    /**
     * 设置margin
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }



    /**
     * 描述：根据分辨率获得字体大小.
     *
     * @param screenWidth the screen width
     * @param screenHeight the screen height
     * @param textSize the text size
     * @return the int
     */
    public static int resizeTextSize(int screenWidth,int screenHeight,int textSize){
        float ratio =  1;
        try {
            float ratioWidth = (float)screenWidth / 480;
            float ratioHeight = (float)screenHeight / 800;
            ratio = Math.min(ratioWidth, ratioHeight);
        } catch (Exception e) {
        }
        return Math.round(textSize * ratio);
    }


}
