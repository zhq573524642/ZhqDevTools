package com.zhq.devtools.ui.jetpack.view_model;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zhq.devtools.entity.MyDatabase;
import com.zhq.devtools.entity.Student;

import java.util.List;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/3/31 17:26
 * Description
 */
public class LoginViewModel extends AndroidViewModel {
    private static final String TAG = "LoginViewModel";
    private MutableLiveData<String> usernameLivaData;
    private MutableLiveData<String> passwordLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);

    }

    public MutableLiveData<String> getUsername() {
        if (usernameLivaData == null) {
            usernameLivaData = new MutableLiveData<>();
        }
        return usernameLivaData;
    }


    public MutableLiveData<String> getPassword() {
        if (passwordLiveData == null) {
            passwordLiveData = new MutableLiveData<>();
        }
        return passwordLiveData;
    }




}
