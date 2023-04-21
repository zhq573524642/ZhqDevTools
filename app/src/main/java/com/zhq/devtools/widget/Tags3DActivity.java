package com.zhq.devtools.widget;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.zhq.devtools.databinding.ActivityTags3DactivityBinding;
import com.zhq.toolslib.window.Tag3DAdapter;
import com.zhq.toolslib.window.TagsBean;

import java.util.ArrayList;
import java.util.List;

public class Tags3DActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityTags3DactivityBinding binding;
    private List<TagsBean> tagsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTags3DactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        for (int i = 0; i < 100; i++) {
            TagsBean bean = new TagsBean();
            bean.image = "https://lmg.jj20.com/up/allimg/4k/s/02/210924233115O14-0-lp.jpg";
            bean.name = "逝去的青春" + i;
            tagsList.add(bean);
        }
        Tag3DAdapter tag3DAdapter = new Tag3DAdapter(tagsList);
        binding.tagCloudView.setAdapter(tag3DAdapter);
    }
}