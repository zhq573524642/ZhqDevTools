package com.zhq.devtools.ui.jetpack.databinding;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.zhq.devtools.BR;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/3 15:21
 * Description
 */
public class TwoWayBindingViewModel extends BaseObservable {

    private final UserInfoBean userInfoBean;

    public TwoWayBindingViewModel() {
        userInfoBean = new UserInfoBean();
        userInfoBean.username = "默认的";
    }

    @Bindable
    public String getUsername() {
        return userInfoBean.username;
    }

    public void setUsername(String username) {
        if (!TextUtils.equals(username, userInfoBean.username)) {
            userInfoBean.username = username;
            notifyPropertyChanged(BR.username);
        }
    }
}
