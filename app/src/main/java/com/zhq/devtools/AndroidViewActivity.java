package com.zhq.devtools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.zhq.devtools.databinding.ActivityAndroidViewBinding;
import com.zhq.devtools.entity.MyDatabase;
import com.zhq.devtools.widget.CommonDialogActivity;
import com.zhq.toolslib.widget.Tag3DAdapter;
import com.zhq.toolslib.widget.TagsBean;

import java.util.ArrayList;
import java.util.List;

public class AndroidViewActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityAndroidViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAndroidViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private List<TagsBean> tagsList = new ArrayList<>();

    private void initView() {
        binding.btnDialog.setOnClickListener(v -> {
            startActivity(new Intent(this, CommonDialogActivity.class));
        });
        for (int i = 0; i < 100; i++) {
            TagsBean bean = new TagsBean();
            bean.image="https://lmg.jj20.com/up/allimg/4k/s/02/210924233115O14-0-lp.jpg";
            bean.name="逝去的青春"+i;
            tagsList.add(bean);
        }
        Tag3DAdapter tag3DAdapter = new Tag3DAdapter(tagsList);
        binding.tagCloudView.setAdapter(tag3DAdapter);
    }
}