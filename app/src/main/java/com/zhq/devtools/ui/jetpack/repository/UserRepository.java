package com.zhq.devtools.ui.jetpack.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.zhq.devtools.ui.jetpack.api.ApiService;
import com.zhq.devtools.ui.jetpack.db.UserDao;
import com.zhq.devtools.ui.jetpack.entity.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/6 11:00
 * Description
 */
public class UserRepository {
    private static final String TAG = "UserRepository";
    private UserDao userDao;
    private ApiService apiService;

    public UserRepository(UserDao userDao, ApiService apiService) {
        this.userDao = userDao;
        this.apiService = apiService;
    }


    public LiveData<User> getUser(String username, String nickname) {
        refresh(username);
        return userDao.queryUserByName(nickname);
    }


    public void refresh(String username) {
        apiService.getUser(username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null) {
                    insertUser(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "===onFailure: " + t.getMessage());
            }
        });
    }

    private void insertUser(final User user) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insertUser(user);
            }
        });
    }
}
