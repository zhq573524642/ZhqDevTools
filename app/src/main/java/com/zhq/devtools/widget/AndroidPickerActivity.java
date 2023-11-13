package com.zhq.devtools.widget;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zhq.devtools.R;
import com.zhq.devtools.base.BaseActivity;
import com.zhq.devtools.databinding.ActivityAndroidPickerBinding;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AndroidPickerActivity extends BaseActivity<ActivityAndroidPickerBinding> {

    private String[] sexList = {"男", "女", "未知"};
    private String[] cardList={"身份证","驾驶证","出生证","居住证","暂住证","结婚证"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_android_picker;
    }

    @Override
    protected void initView() {
        mBinding.btnTimePicker.setOnClickListener(v -> {
            timePicker();
        });
        mBinding.btnOptionPicker.setOnClickListener(v -> {
            optionsPicker();
        });
    }

    @SuppressLint("SimpleDateFormat")
    private String getTime(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    private void timePicker() {
        //时间选择器
        TimePickerView timePickerView = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mBinding.btnTimePicker.setText("时间选择器：" +getTime(date,"yyyy-MM-dd HH:mm"));
            }
        })
                .setDate(Calendar.getInstance())
                .isAlphaGradient(true)
                .setType(new boolean[]{true,true,true,true,true,true})
                .setLabel("年","月","日","时","分","秒")
                .build();
        timePickerView.show(true);
    }

    private void optionsPicker() {
        OptionsPickerView<Object> optionsPickerView = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

            }
        }).build();
//        optionsPickerView.setPicker(Arrays.asList(sexList));
        optionsPickerView.show(true);
    }
}