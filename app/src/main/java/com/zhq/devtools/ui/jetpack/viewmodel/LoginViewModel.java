package com.zhq.devtools.ui.jetpack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zhq.devtools.App;
import com.zhq.devtools.ui.jetpack.api.ApiService;
import com.zhq.devtools.ui.jetpack.entity.DataBase;
import com.zhq.devtools.ui.jetpack.repository.LoginRepository;
import com.zhq.devtools.ui.jetpack.repository.LoginResult;
import com.zhq.devtoolslib.base.BaseViewModel;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/3/31 17:26
 * Description
 */
public class LoginViewModel extends BaseViewModel{
    private static final String TAG = "LoginViewModel";
    private MutableLiveData<String> usernameLivaData;
    private MutableLiveData<String> passwordLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        ApiService apiService = App.getApiService();

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
