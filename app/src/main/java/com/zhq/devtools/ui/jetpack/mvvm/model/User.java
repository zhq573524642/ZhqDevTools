package com.zhq.devtools.ui.jetpack.mvvm.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/6 10:35
 * Description
 */
@Entity(tableName = "user")
public class User {

    @PrimaryKey()
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    public String name;

    @ColumnInfo(name = "avatar_url", typeAffinity = ColumnInfo.TEXT)
    public String avatar_url;

    @ColumnInfo(name = "followers", typeAffinity = ColumnInfo.INTEGER)
    public int followers;

    @ColumnInfo(name = "following", typeAffinity = ColumnInfo.INTEGER)
    public int following;

    @ColumnInfo(name = "blog", typeAffinity = ColumnInfo.TEXT)
    public String blog;

    @ColumnInfo(name = "company", typeAffinity = ColumnInfo.TEXT)
    public String company;

    @ColumnInfo(name = "bio", typeAffinity = ColumnInfo.TEXT)
    public String bio;

    @ColumnInfo(name = "location", typeAffinity = ColumnInfo.TEXT)
    public String location;

    @ColumnInfo(name = "html_url", typeAffinity = ColumnInfo.TEXT)
    public String html_url;

    public User(int id, String name, String avatar_url, int followers, int following, String blog, String company, String bio, String location, String html_url) {
        this.id = id;
        this.name = name;
        this.avatar_url = avatar_url;
        this.followers = followers;
        this.following = following;
        this.blog = blog;
        this.company = company;
        this.bio = bio;
        this.location = location;
        this.html_url = html_url;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", blog='" + blog + '\'' +
                ", company='" + company + '\'' +
                ", bio='" + bio + '\'' +
                ", location='" + location + '\'' +
                ", html_url='" + html_url + '\'' +
                '}';
    }
}
