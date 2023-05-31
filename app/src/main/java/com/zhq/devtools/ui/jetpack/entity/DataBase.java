package com.zhq.devtools.ui.jetpack.entity;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/21 14:14
 * Description
 */
public class DataBase<T> {

    public static final int SUCCESS = 0;
    public static final int LOGIN_FAIL = -1001;
    public int errorCode;//0 成功 -1001登录失败 需要重新登录 其他 请求失败
    public String errorMsg;
    public T data;

}
