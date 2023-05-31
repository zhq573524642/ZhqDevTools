package com.zhq.devtools.ui.jetpack.databinding

import android.text.Editable
import android.view.View
import com.zhq.devtools.ui.jetpack.databinding.ToastUtils.showToast

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/23 17:40
 * Description
 */
class UserHandleEvent {

    fun userOnClick(view: View) {
        "无参单击".showToast()
    }

    fun userOnClick2(userInfo: UserInfo) {
        ("有参数的点击" + userInfo.name).showToast()
    }

    //长按
    fun userOnLongClick(view: View) {
        "无参长按".showToast()
    }

    fun userOnLongClick2(userInfo: UserInfo) {
        ("有参数的长按${userInfo.name}").showToast()
    }

    fun afterTextChanged(editable: Editable) {
        ("afterTextChanged：$editable").showToast()
    }

    fun afterUserTextChanged(editable: Editable) {
        ("afterUserTextChanged：$editable").showToast()
    }


}