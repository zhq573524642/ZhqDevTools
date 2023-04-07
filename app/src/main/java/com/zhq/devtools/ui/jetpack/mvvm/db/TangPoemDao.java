package com.zhq.devtools.ui.jetpack.mvvm.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.zhq.devtools.ui.jetpack.mvvm.model.TangPoem;

import java.util.List;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/6 15:44
 * Description
 */
@Dao
public interface TangPoemDao {

    @Query("SELECT * FROM poem")
    LiveData<List<TangPoem>> getTangPoemList();
}
