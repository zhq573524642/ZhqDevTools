package com.zhq.devtools.ui.jetpack.databinding;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/3 15:21
 * Description
 */
public class TwoWayBindingViewModel2  {

    private UserInfoBean userInfoBean;
    private ObservableField<UserInfoBean> testLoginBeanObservableField;

    public TwoWayBindingViewModel2() {
        userInfoBean = new UserInfoBean();
        userInfoBean.username = "这是默认值";
        testLoginBeanObservableField = new ObservableField<>();
        testLoginBeanObservableField.set(userInfoBean);
    }

    private static final String TAG = "TwoWayBindingViewModel2";

    public String getUsername() {
        if (testLoginBeanObservableField == null || testLoginBeanObservableField.get() == null) {
            return "";
        }
        Log.d(TAG, "===getUsername: " + testLoginBeanObservableField.get().username);
        return testLoginBeanObservableField.get().username;
    }

    public void setUsername(String username) {
        testLoginBeanObservableField.get().username = username;
        Log.d(TAG, "===setUsername: "+testLoginBeanObservableField.get().username);
    }
}
