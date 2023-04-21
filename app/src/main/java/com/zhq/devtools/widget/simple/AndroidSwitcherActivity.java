package com.zhq.devtools.widget.simple;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ActivityAndroidSwitcherBinding;
import com.zhq.toolslib.density.DensityUtil;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/19 17:06
 * Description
 */
public class AndroidSwitcherActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityAndroidSwitcherBinding binding;
    private String[] text = {"哈哈哈", "啦啦啦", "嘿嘿嘿", "嘎嘎嘎", "呵呵呵", "嘻嘻嘻"};
    private int[] images = {R.drawable.ic1, R.drawable.ic2, R.drawable.ic3, R.drawable.ic4,
            R.drawable.ic5, R.drawable.ic6, R.drawable.ic7, R.drawable.ic8, R.drawable.ic9,
            R.drawable.ic10, R.drawable.ic11, R.drawable.ic12};
    private int textIndex = 0;
    private int imageIndex = 0;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        binding = ActivityAndroidSwitcherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        initTextSwitcher();
        initImageSwitcher();
        initFlipperAdapter();
        initStackView();
    }

    private void initTextSwitcher() {
        binding.textPre.setOnClickListener(v -> {
            binding.textSwitcher.showPrevious();
        });
        binding.textNext.setOnClickListener(v -> {
            binding.textSwitcher.showNext();
        });
        binding.textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER_HORIZONTAL;
                TextView textView = new TextView(AndroidSwitcherActivity.this);
                textView.setTextColor(Color.RED);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                textView.setLayoutParams(params);
                return textView;
            }
        });
        binding.textSwitcher.setText(text[textIndex]);
        binding.textSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textIndex++;
                if (textIndex >= text.length) {
                    textIndex = 0;
                }
                binding.textSwitcher.setText(text[textIndex]);
            }
        });
    }

    private void initImageSwitcher() {
        binding.imagePre.setOnClickListener(v -> {
            binding.imageSwitcher.showPrevious();
        });
        binding.imageNext.setOnClickListener(v -> {
            binding.imageSwitcher.showNext();
        });
        binding.imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(DensityUtil.dp2px(mContext, 90), DensityUtil.dp2px(mContext, 160));
                ImageView imageView = new ImageView(AndroidSwitcherActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                params.gravity=Gravity.CENTER;
                imageView.setLayoutParams(params);
                return imageView;
            }
        });

        binding.imageSwitcher.setImageResource(images[imageIndex]);
        binding.imageSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageIndex++;
                if (imageIndex >= images.length) {
                    imageIndex = 0;
                }
                binding.imageSwitcher.setImageResource(images[imageIndex]);
            }
        });
    }

    private void initFlipperAdapter(){
        //设置是否自动切换
        binding.adapterViewFlipper.setAutoStart(true);
        //设置自动切换的时间间隔
        binding.adapterViewFlipper.setFlipInterval(3000);
        binding.adapterViewFlipper.setAnimateFirstView(false);
        binding.adapterViewFlipper.startFlipping();
        //设置adapter
        ViewFlipperAdapter viewFlipperAdapter = new ViewFlipperAdapter();
        binding.adapterViewFlipper.setAdapter(viewFlipperAdapter);
        binding.btnFlipperPrevious.setOnClickListener(v -> {
            binding.adapterViewFlipper.showPrevious();
        });
        binding.btnFlipperNext.setOnClickListener(v -> {
            binding.adapterViewFlipper.showNext();
        });
    }

    private void initStackView(){
        StackViewAdapter stackViewAdapter = new StackViewAdapter();
        binding.stackView.setAdapter(stackViewAdapter);
        binding.stackView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, position+"", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnStackPrevious.setOnClickListener(v -> {
            binding.stackView.showPrevious();
        });
        binding.btnStackNext.setOnClickListener(v -> {
            binding.stackView.showNext();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding.adapterViewFlipper.isFlipping()){
            binding.adapterViewFlipper.stopFlipping();
        }

    }

    public class ViewFlipperAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return images[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_flipper, null);
                viewHolder = new ViewHolder();
                viewHolder.mImageView = convertView.findViewById(R.id.image_view);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.mImageView.setImageResource(images[position]);
            return convertView;
        }

        class ViewHolder {
            ImageView mImageView;
        }
    }

    public class StackViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return images[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_flipper, null);
                viewHolder = new ViewHolder();
                viewHolder.mImageView = convertView.findViewById(R.id.image_view);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.mImageView.setImageResource(images[position]);
            return convertView;
        }

        class ViewHolder {
            ImageView mImageView;
        }
    }
}
