package com.zhq.devtools.ui.jetpack.repository;

import androidx.lifecycle.LiveData;

import com.zhq.devtools.ui.jetpack.db.MenuDao;
import com.zhq.devtools.ui.jetpack.entity.Menu;

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
