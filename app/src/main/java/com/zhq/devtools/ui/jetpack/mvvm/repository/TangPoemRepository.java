package com.zhq.devtools.ui.jetpack.mvvm.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.zhq.devtools.ui.jetpack.mvvm.api.ApiService;
import com.zhq.devtools.ui.jetpack.mvvm.db.TangPoemDao;
import com.zhq.devtools.ui.jetpack.mvvm.db.UserDao;
import com.zhq.devtools.ui.jetpack.mvvm.model.TangPoem;
import com.zhq.devtools.ui.jetpack.mvvm.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/6 11:00
 * Description
 */
public class TangPoemRepository {
    private TangPoemDao tangPoemDao;

    public TangPoemRepository(TangPoemDao tangPoemDao) {
        this.tangPoemDao = tangPoemDao;

    }


    public LiveData<List<TangPoem>> getTangPoem() {
        return tangPoemDao.getTangPoemList();
    }

}
