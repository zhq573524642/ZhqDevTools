package com.zhq.devtools.ui.jetpack.mvvm.api;

import com.zhq.devtools.ui.jetpack.mvvm.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/6 10:51
 * Description
 */
public interface ApiService {

    @GET("users/{userId}")
    Call<User> getUser(@Path("userId") String userId);
}
