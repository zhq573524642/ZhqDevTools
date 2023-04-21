package com.zhq.devtools.widget.viewpager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.zhq.devtools.App;
import com.zhq.devtools.R;
import com.zhq.devtools.base.BaseActivity;
import com.zhq.devtools.databinding.ActivityAndroidViewPagerBinding;
import com.zhq.devtools.widget.DataConstants;
import com.zhq.toolslib.density.DensityUtil;
import com.zhq.toolslib.glide.GlideUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/20 14:37
 * Description
 */
public class AndroidViewPagerActivity extends BaseActivity<ActivityAndroidViewPagerBinding> {

    private static final String TAG = "AndroidViewPagerActivit";
    private List<String> imageList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_android_view_pager;
    }

    @Override
    protected void initView() {
        imageList.clear();
        imageList.addAll(Arrays.asList(DataConstants.IMAGE_URL_9));
        mBinding.viewPager.setAdapter(new ViewPagerAdapter());
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBinding.viewPager.setCurrentItem(1);
        mBinding.viewPager.setOffscreenPageLimit(imageList.size());
        mBinding.viewPager.setPageMargin(50);
        mBinding.viewPager.setPageTransformer(false, new AlphaPageTransformer(mBinding.viewPager));
        mBinding.rb1.setChecked(true);
        mBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb1:
                        mBinding.viewPager.setPageTransformer(false, new AlphaPageTransformer(mBinding.viewPager));
                        break;
                    case R.id.rb2:
                        mBinding.viewPager.setPageTransformer(false, new ScalePageTransformer(mBinding.viewPager));
                        break;
                }
            }
        });
    }


    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageList == null ? 0 : imageList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ViewGroup.LayoutParams parentParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            FrameLayout frameLayout = new FrameLayout(container.getContext());
            frameLayout.setLayoutParams(parentParams);
            ImageView imageView = new ImageView(container.getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.height = DensityUtil.dp2px(container.getContext(), 200);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            frameLayout.addView(imageView);
            container.addView(frameLayout);
            GlideUtils.getInstance().init(App.getAppContext())
                    .configRequestOptions()
                    .loadImageData(imageList.get(position))
                    .loadImage(imageView);
            return frameLayout;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

}
