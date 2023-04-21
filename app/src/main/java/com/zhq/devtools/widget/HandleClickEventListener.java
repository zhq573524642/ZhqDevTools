package com.zhq.devtools.widget;

import android.content.Context;
import android.view.View;

import com.zhq.toolslib.toast.ToastUtils;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/3 13:55
 * Description
 */
public class HandleClickEventListener {

    private Context context;

    public HandleClickEventListener(Context context) {
        this.context = context;
    }

    public void setButtonClick(View view){
        ToastUtils.getInstance().showShortToast(context,"哈哈哈哈哈");
    }
}
