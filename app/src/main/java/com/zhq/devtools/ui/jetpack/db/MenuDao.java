package com.zhq.devtools.ui.jetpack.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.zhq.devtools.ui.jetpack.entity.Menu;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/6 16:48
 * Description
 */
@Dao
public interface MenuDao {

    @Query("SELECT * FROM menu WHERE volume = :volume")
    LiveData<Menu> getPoemMenu(int volume);
}
