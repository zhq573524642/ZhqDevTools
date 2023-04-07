package com.zhq.devtools.ui.jetpack.mvvm.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.zhq.devtools.ui.jetpack.mvvm.model.User;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/6 10:48
 * Description
 */
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user WHERE name = :name")
    LiveData<User> queryUserByName(String name);
}
