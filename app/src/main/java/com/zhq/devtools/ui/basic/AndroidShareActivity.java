package com.zhq.devtools.ui.basic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.zhq.devtools.NFCShareActivity;
import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ActivityAndroidShareBinding;

import java.util.ArrayList;

public class AndroidShareActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityAndroidShareBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAndroidShareBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        //Android 分享
        //1；分享文本
        binding.btnShareText.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            //。。。还可加其他标准的附加值
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "我想发送"));
        });
        //2；分享二进制内容
        binding.btnShareUri.setOnClickListener(v -> {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_STREAM, "这是字节数组");
            //。。。还可加其他标准的附加值
            sendIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(sendIntent, "我想发送"));
        });
        //3：分享多个二进制内容
        binding.btnMultiUri.setOnClickListener(v -> {
            ArrayList<Uri> imageUris = new ArrayList<Uri>();
            imageUris.add(Uri.parse("imageUri1")); // Add your image URIs here
            imageUris.add(Uri.parse("imageUri2"));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
            shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
            shareIntent.setType("image/*");
            startActivity(Intent.createChooser(shareIntent, "Share images to.."));

        });

        //4.NFC分享数据
        binding.btnNfcShare.setOnClickListener(v -> {
            startActivity(new Intent(this, NFCShareActivity.class));
        });
    }
}