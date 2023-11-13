package com.zhq.devtools.widget.progress;

import android.os.Build;
import android.os.Bundle;
import android.widget.RatingBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ActivityAndroidProgressBinding;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/20 10:54
 * Description
 */
public class AndroidProgressActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityAndroidProgressBinding binding;
    private int progressIndex = 0;
    private int secondaryProgressIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAndroidProgressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {

        binding.btnProgressUp.setOnClickListener(v -> {
            progressIndex += 10;
            secondaryProgressIndex += 20;
            if (progressIndex > 100) {
                progressIndex = 100;
            }
            if (secondaryProgressIndex > 100) {
                secondaryProgressIndex = 100;
            }
            setProgressIndex();
        });
        binding.btnProgressDown.setOnClickListener(v -> {
            progressIndex -= 10;
            secondaryProgressIndex -= 5;
            if (progressIndex < 0) {
                progressIndex = 0;
            }
            if (secondaryProgressIndex < 0) {
                secondaryProgressIndex = 0;
            }
            setProgressIndex();
        });

        binding.rbNormal.setNumStars(6);
        binding.rbNormal.setStepSize(1.5f);
        binding.rbNormal.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                binding.rbNormal.setRating(rating);
            }
        });


        binding.rbCustom.setNumStars(10);
        binding.rbCustom.setStepSize(1);
        binding.rbCustom.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                binding.rbCustom.setRating(rating);
            }
        });
    }

    private void setProgressIndex() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.pbNormal.setProgress(progressIndex, true);
            binding.pbCustom.setProgress(progressIndex, true);
            binding.sbNormal.setProgress(progressIndex, true);
            binding.sbDiscrete.setProgress(progressIndex, true);
            binding.colorArcProgressBar.setCurrentValues(progressIndex,1000);
        } else {
            binding.pbNormal.setProgress(progressIndex);
            binding.pbCustom.setProgress(progressIndex);
            binding.sbNormal.setProgress(progressIndex);
            binding.sbDiscrete.setProgress(progressIndex);
            binding.colorArcProgressBar.setCurrentValues(progressIndex,1000);
        }
        binding.pbNormal.setSecondaryProgress(secondaryProgressIndex);
        binding.pbCustom.setSecondaryProgress(secondaryProgressIndex);
        binding.sbNormal.setSecondaryProgress(secondaryProgressIndex);
        binding.sbDiscrete.setSecondaryProgress(secondaryProgressIndex);

        //垂直进度条
        binding.verticalProgressBar.setProgressMax(100);
        binding.verticalProgressBar.setProgress(progressIndex);
        binding.verticalProgressBar.setShowTopProgressText(true);
        binding.verticalProgressBar.setProgressTextSize(14);
        binding.verticalProgressBar.setProgressTextColor(R.color.purple_200);
    }
}
