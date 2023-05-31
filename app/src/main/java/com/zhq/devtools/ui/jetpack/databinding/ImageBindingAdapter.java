package com.zhq.devtools.ui.jetpack.databinding;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.zhq.devtools.R;
import com.zhq.toolslib.glide.GlideUtils;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/3 14:37
 * Description
 */
public class ImageBindingAdapter {

    @BindingAdapter("image")
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            GlideUtils.getInstance().init(imageView.getContext())
                    .setPlaceholder(R.drawable.ic_test_img)
                    .configRequestOptions()
                    .loadImageData(imageUrl)
                    .loadImage(imageView);
        } else {
            imageView.setBackgroundColor(Color.RED);
        }
    }


    @BindingAdapter("padding")
    public static void setImageViewPadding(ImageView imageView,int oldPadding,int newPadding){
        if (newPadding!=oldPadding){
            imageView.setPadding(newPadding,newPadding,newPadding,newPadding);
        }
    }
}
