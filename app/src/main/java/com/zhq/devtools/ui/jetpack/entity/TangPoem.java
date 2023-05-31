package com.zhq.devtools.ui.jetpack.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/6 15:39
 * Description
 */
@Entity(tableName = "poem")
public class TangPoem {

    @PrimaryKey()
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;
    @ColumnInfo(name = "volume", typeAffinity = ColumnInfo.INTEGER,index = true)
    public int volume;
    @ColumnInfo(name = "sequence", typeAffinity = ColumnInfo.INTEGER,index = true)
    public int sequence;
    @ColumnInfo(name = "title", typeAffinity = ColumnInfo.TEXT,index = true)
    public String title;
    @ColumnInfo(name = "author", typeAffinity = ColumnInfo.TEXT,index = true)
    public String author;
    @ColumnInfo(name = "text", typeAffinity = ColumnInfo.TEXT)
    public String text;

    public TangPoem(int id, int volume, int sequence, String title, String author, String text) {
        this.id = id;
        this.volume = volume;
        this.sequence = sequence;
        this.title = title;
        this.author = author;
        this.text = text;
    }
}
