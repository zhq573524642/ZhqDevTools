package com.zhq.devtools.ui.java_basic;

import android.util.Log;

/**
 * @Author ZhangHuiQiang
 * @Date 2024/3/6 14:47
 * Description
 */
public class TestReflectBean {

    private static final String TAG = "TestReflectBean";
    public int code = 100;
    public String nickname = "张三";
    private int status = 10;
    private float price = 1.0f;
    private String info = "默认info";

    public TestReflectBean() {
        Log.d(TAG, "===TestReflectBean: 空参构造");
    }

    public TestReflectBean(int code, String nickname, int status) {
        this.code = code;
        this.nickname = nickname;
        this.status = status;
        Log.d(TAG, "===TestReflectBean: 含参构造：" + code + "--" + nickname + "--" + status);
    }

    public void showInfoPublic() {

    }

    public void showInfoPublic(int code) {

    }

    public String returnInfoPublic(int code) {
        return "返回：" + code;
    }

    private void showInfoPrivate() {

    }

    private void showInfoPrivate(String nickname) {

    }

    private String returnInfoPrivate(String nickname) {
        return "返回：" + nickname;
    }

    public static void showMsg(String msg) {

    }

    private static void showMsgPrivate(String msg) {

    }


}
