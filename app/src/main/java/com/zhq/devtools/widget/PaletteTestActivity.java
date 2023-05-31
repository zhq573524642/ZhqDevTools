package com.zhq.devtools.widget;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.zhq.devtools.R;
import com.zhq.devtools.base.BaseActivity;
import com.zhq.devtools.databinding.ActivityPaletteTestBinding;
import com.zhq.devtools.widget.material.palette.ColorShowListAdapter;
import com.zhq.devtools.widget.material.palette.ItemColorBean;
import com.zhq.devtools.widget.nine_grid.NineGridView;
import com.zhq.devtools.widget.nine_grid.NineImageAdapter;
import com.zhq.toolslib.glide.GlideUtils;
import com.zhq.toolslib.thread.ObserverUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaletteTestActivity extends BaseActivity<ActivityPaletteTestBinding> {

    private NineImageAdapter nineImageAdapter;
    private RequestOptions requestOptions;
    private List<String> imageUrlList = new ArrayList<>();
    private List<ItemColorBean> itemColorBeanList = new ArrayList<>();
    private ColorShowListAdapter colorShowListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_palette_test;
    }

    @Override
    protected void initView() {
        imageUrlList.clear();
        imageUrlList.addAll(Arrays.asList(DataConstants.IMAGE_URL_9));
        requestOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        nineImageAdapter = new NineImageAdapter(this, requestOptions, DrawableTransitionOptions.withCrossFade(), imageUrlList);
        mBinding.nineGridView.setAdapter(nineImageAdapter);

        //设置颜色的显示adapter
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        colorShowListAdapter = new ColorShowListAdapter(itemColorBeanList);
        mBinding.recyclerView.setAdapter(colorShowListAdapter);
        mBinding.nineGridView.setOnImageClickListener(new NineGridView.OnImageClickListener() {
            @Override
            public void onImageClick(int position, View view) {
                String s = imageUrlList.get(position);
                GlideUtils.getInstance().loadBlurImage(mContext,s, mBinding.imageView);
                new ObserverUtil<Bitmap>()
                        .setObserverTask(new ObserverUtil.OnObserverHandleListener<Bitmap>() {
                            @Override
                            public Bitmap onHandleFunction() {
                                return GlideUtils.getInstance().getBitmapByUrl(mContext, s);
                            }

                            @Override
                            public void onNext(Bitmap value) {
                                if (value != null) {
                                    setPaletteColorData(value);
                                }
                            }
                        });

            }
        });
    }


    private void setPaletteColorData(Bitmap bitmap) {
        Palette.Builder from = Palette.from(bitmap);
        from.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                if (palette == null) return;
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();//有活力
                Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();//有活力暗色
                Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();//有活力 亮色
                Palette.Swatch mutedSwatch = palette.getMutedSwatch();//柔和
                Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();//柔和 暗色
                Palette.Swatch lightMutedSwatch = palette.getLightMutedSwatch();//柔和 亮色

                itemColorBeanList.clear();
                for (int i = 0; i < 6; i++) {
                    ItemColorBean bean = new ItemColorBean();
                    if (i == 0) {
                        if (vibrantSwatch != null) {
                            bean.title = "vibrantSwatch";
                            bean.titleColor = vibrantSwatch.getTitleTextColor();
                            bean.bgColor = vibrantSwatch.getRgb();
                        }else {
                            bean.title = "vibrantSwatch（未获取到）";
                        }
                    } else if (i == 1) {
                        if (darkVibrantSwatch != null) {
                            bean.title = "darkVibrantSwatch";
                            bean.titleColor = darkVibrantSwatch.getTitleTextColor();
                            bean.bgColor = darkVibrantSwatch.getRgb();
                        }else {
                            bean.title = "darkVibrantSwatch(未获取到)";
                        }
                    } else if (i == 2) {
                        if (lightVibrantSwatch != null) {
                            bean.title = "lightVibrantSwatch";
                            bean.titleColor = lightVibrantSwatch.getTitleTextColor();
                            bean.bgColor = lightVibrantSwatch.getRgb();
                        }else {
                            bean.title = "lightVibrantSwatch（未获取到）";
                        }
                    } else if (i == 3) {
                        if (mutedSwatch != null) {
                            bean.title = "mutedSwatch";
                            bean.titleColor = mutedSwatch.getTitleTextColor();
                            bean.bgColor = mutedSwatch.getRgb();
                        }else {
                            bean.title = "mutedSwatch（未获取到）";
                        }
                    } else if (i == 4) {
                        if (darkMutedSwatch != null) {
                            bean.title = "darkMutedSwatch";
                            bean.titleColor = darkMutedSwatch.getTitleTextColor();
                            bean.bgColor = darkMutedSwatch.getRgb();
                        }else {
                            bean.title = "darkMutedSwatch（未获取到）";
                        }
                    } else {
                        if (lightMutedSwatch != null) {
                            bean.title = "lightMutedSwatch";
                            bean.titleColor = lightMutedSwatch.getTitleTextColor();
                            bean.bgColor = lightMutedSwatch.getRgb();
                        }else {
                            bean.title = "lightMutedSwatch（未获取到）";
                        }
                    }
                    itemColorBeanList.add(bean);
                }
                colorShowListAdapter.notifyDataSetChanged();
            }
        });
    }

}