package com.zhq.devtools.ui.media;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.zhq.devtools.R;
import com.zhq.devtools.WebViewActivity;
import com.zhq.devtools.databinding.ActivityAndroidPrintBinding;

public class AndroidPrintActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityAndroidPrintBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAndroidPrintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.btnPrintImage.setOnClickListener(v -> {
            PrintHelper photoPrinter = new PrintHelper(this);
            photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_test_img);
            photoPrinter.printBitmap("androids.jpg - test print", bitmap);
        });
        binding.btnPrintHtml.setOnClickListener(v -> {
            WebViewActivity.start(this, "https://beta-front.yiboshi.com/resident-h5/#/");
        });
    }
}