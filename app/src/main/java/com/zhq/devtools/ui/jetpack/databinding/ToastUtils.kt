package com.zhq.devtools.ui.jetpack.databinding

import android.app.Application
import android.content.Context
import android.widget.Toast

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/13 14:30
 * Description
 */
object ToastUtils {
    lateinit var mContext: Application
    fun init(context: Application) {
        mContext = context
    }

    fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(mContext, this, duration).show()
    }

    fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(mContext, this, duration).show()
    }
}