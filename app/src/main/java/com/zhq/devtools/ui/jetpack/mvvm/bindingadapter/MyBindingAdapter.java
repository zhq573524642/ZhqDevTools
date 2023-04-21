package com.zhq.devtools.ui.jetpack.mvvm.bindingadapter;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.zhq.devtools.R;
import com.zhq.toolslib.glide.GlideUtils;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/6 11:20
 * Description
 */
public class MyBindingAdapter {


    @BindingAdapter(value = {"image", "defaultImage"}, requireAll = false)
    public static void imageViewAdapter(ImageView imageView, String url,int defaultImage) {
        if (!TextUtils.isEmpty(url)) {
            GlideUtils.getInstance().init(imageView.getContext())
                    .configRequestOptions()
                    .loadImageData(url)
                    .setErrorImage(R.drawable.ic_test_img)
                    .setPlaceholder(defaultImage)
                    .loadImage(imageView);
        }else {
            imageView.setImageResource(R.mipmap.ic_launcher);
        }
    }
}
