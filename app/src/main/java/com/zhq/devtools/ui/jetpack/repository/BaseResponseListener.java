package com.zhq.devtools.ui.jetpack.repository;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/21 14:51
 * Description
 */
public interface BaseResponseListener<T> {

    void onSuccess(T data);

    void onFailed(int error_code,String error_msg);

    void onThrowable(Throwable throwable);

    void onReLogin(int error_code,String error_msg);
}
