package com.zhq.devtools.ui.jetpack.repository;

import androidx.lifecycle.LiveData;

import com.zhq.devtools.ui.jetpack.db.TangPoemDao;
import com.zhq.devtools.ui.jetpack.entity.TangPoem;

import java.util.List;

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
