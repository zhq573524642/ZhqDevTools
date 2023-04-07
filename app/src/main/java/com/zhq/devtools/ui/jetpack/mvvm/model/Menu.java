package com.zhq.devtools.ui.jetpack.mvvm.model;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/6 16:45
 * Description
 */
@Entity(tableName = "menu")
public class Menu {

    @PrimaryKey()
    @ColumnInfo(name = "volume",typeAffinity = ColumnInfo.INTEGER)
    public int volume;

    @ColumnInfo(name = "comment",typeAffinity = ColumnInfo.TEXT)
    public String comment;

    @ColumnInfo(name = "count",typeAffinity = ColumnInfo.INTEGER)
    public int count;

}
