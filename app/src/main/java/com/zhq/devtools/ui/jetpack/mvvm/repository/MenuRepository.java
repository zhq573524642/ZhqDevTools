package com.zhq.devtools.ui.jetpack.mvvm.repository;

import androidx.lifecycle.LiveData;

import com.zhq.devtools.ui.jetpack.mvvm.db.MenuDao;
import com.zhq.devtools.ui.jetpack.mvvm.db.TangPoemDao;
import com.zhq.devtools.ui.jetpack.mvvm.model.Menu;
import com.zhq.devtools.ui.jetpack.mvvm.model.TangPoem;

import java.util.List;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/6 11:00
 * Description
 */
public class MenuRepository {
    private MenuDao menuDao;

    public MenuRepository(MenuDao menuDao) {
        this.menuDao = menuDao;

    }

    public LiveData<Menu> getTangPoem(int volume) {
        return menuDao.getPoemMenu(volume);
    }

}
