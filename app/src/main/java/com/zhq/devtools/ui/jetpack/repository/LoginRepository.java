package com.zhq.devtools.ui.jetpack.repository;

import com.zhq.devtools.ui.jetpack.api.ApiService;
import com.zhq.devtools.ui.jetpack.entity.DataBase;
import com.zhq.toolslib.toast.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/21 14:30
 * Description
 */
public class LoginRepository {

    private ApiService apiService;
    private LoginResult<DataBase<String>> loginResult;

    public LoginRepository(ApiService apiService, LoginResult<DataBase<String>> loginResult) {
        this.apiService = apiService;
        this.loginResult = loginResult;
    }

    public void registerUser(String username, String password, String repassword) {
        apiService.registerUser(username, password, repassword).enqueue(new Callback<DataBase>() {
            @Override
            public void onResponse(Call<DataBase> call, Response<DataBase> response) {
                if (response.body() != null) {
                    DataBase dataBase = response.body();
                    int errorCode = dataBase.errorCode;
                    if (errorCode == DataBase.SUCCESS) {
                        loginResult.onSuccess(dataBase);
                    } else if (errorCode == DataBase.LOGIN_FAIL) {
                        //重新登录
                        loginResult.onReLogin(dataBase.errorCode, dataBase.errorMsg);
                    } else {
                        loginResult.onFailed(dataBase.errorCode, dataBase.errorMsg);
                    }
                }
            }

            @Override
            public void onFailure(Call<DataBase> call, Throwable t) {
                loginResult.onThrowable(t);
            }
        });

    }

}
