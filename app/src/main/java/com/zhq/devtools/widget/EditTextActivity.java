package com.zhq.devtools.widget;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.zhq.devtools.R;
import com.zhq.devtools.base.BaseActivity;
import com.zhq.devtools.databinding.ActivityEditTextBinding;

public class EditTextActivity extends BaseActivity<ActivityEditTextBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_text;
    }

    @Override
    protected void initView() {
        mBinding.et1.setHint("请输入...");
        mBinding.et1.setHintTextColor(Color.GRAY);
        mBinding.et1.setTextColor(Color.BLUE);
        mBinding.btn1.setOnClickListener(v -> {
            SpannableString spanStr = new SpannableString("imge");
            Drawable drawable = getResources().getDrawable(R.drawable.ic_seek_bar_thumb);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
            spanStr.setSpan(span, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            int cursor = mBinding.et1.getSelectionStart();
            mBinding.et1.getText().insert(cursor, spanStr);
        });
    }
}