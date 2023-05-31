package com.zhq.devtools.ui.jetpack.api;


import com.zhq.devtools.ui.jetpack.entity.DataBase;
import com.zhq.devtools.ui.jetpack.entity.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/6 10:51
 * Description
 */
public interface ApiService {

    @GET("users/{userId}")
    Call<User> getUser(@Path("userId") String userId);

    @POST("user/register")
    Call<DataBase> registerUser(@Field("username") String username,
                                @Field("password") String password,
                                @Field("repassword") String repassword);

    @POST("user/login")
    Call<DataBase> login(@Field("username") String username,
                         @Field("password") String password);

}
