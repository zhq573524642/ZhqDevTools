package com.zhq.devtools.ui.jetpack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/3/31 15:57
 * Description
 */
public class TestViewModel extends AndroidViewModel {

    public TestViewModel(@NonNull Application application) {
        super(application);
    }


    private MutableLiveData<Integer> progress;

    public MutableLiveData<Integer> getProgress() {
        if (progress == null) {
            progress = new MutableLiveData<>();
        }
        return progress;
    }
}
