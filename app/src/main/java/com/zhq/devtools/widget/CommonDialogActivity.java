package com.zhq.devtools.widget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.zhq.devtools.App;
import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ActivityCommonDialogBinding;
import com.zhq.toolslib.ToastUtils;
import com.zhq.toolslib.widget.BottomHandleDialog;
import com.zhq.toolslib.widget.BottomInputDialog;
import com.zhq.toolslib.widget.ButtonItemBean;
import com.zhq.toolslib.widget.CenterButtonRemindDialog;

import java.util.ArrayList;
import java.util.List;

public class CommonDialogActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityCommonDialogBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=(ActivityCommonDialogBinding) DataBindingUtil.setContentView(this,R.layout.activity_common_dialog);
        initView();
    }


    private void initView() {
        binding.btnBottomHandleDialog.setOnClickListener(v -> {
            showBottomHandleDialog();
        });
        binding.btnBottomInputDialog.setOnClickListener(v -> {
            showBottomInputDialog();
        });
        binding.btnCenterSimpleRemindDialog.setOnClickListener(v -> {
            showCenterSimpleRemindDialog();
        });
    }

    private CenterButtonRemindDialog centerButtonRemindDialog;

    private void showCenterSimpleRemindDialog() {
        if (centerButtonRemindDialog == null) {
            centerButtonRemindDialog = new CenterButtonRemindDialog.Builder(CommonDialogActivity.this)
                    .setDialogTitle("温馨提示")
                    .setDialogTitleTextSize(16)
                    .setDialogContent("确定要执行此操作吗？")
                    .setLeftText("取消")
                    .setRightText("确定")
                    .setRightTextColor("#ff0000")
                    .setCancelable(true)
                    .setCanceledOnTouchOutside(true)
                    .setCallback(new CenterButtonRemindDialog.OnCenterButtonRemindDialogListener() {
                        @Override
                        public void onLeftClick() {
                            if (centerButtonRemindDialog != null) {
                                centerButtonRemindDialog.dismiss();
                                centerButtonRemindDialog = null;
                            }
                        }

                        @Override
                        public void onRightClick() {
                            if (centerButtonRemindDialog != null) {
                                centerButtonRemindDialog.dismiss();
                                centerButtonRemindDialog = null;
                            }
                        }

                        @Override
                        public void onDialogDismiss() {
                            if (centerButtonRemindDialog != null) {
                                centerButtonRemindDialog.dismiss();
                                centerButtonRemindDialog = null;
                            }
                        }
                    }).create();
            centerButtonRemindDialog.show();
        }
    }

    private BottomInputDialog bottomInputDialog;

    private void showBottomInputDialog() {
        if (bottomInputDialog == null) {
            bottomInputDialog = new BottomInputDialog.Builder(CommonDialogActivity.this)
                    .setTitle("请输入")
                    .setHintText("请输入最多500字")
                    .setLeftText("取消")
                    .setRightText("确定")
                    .setInoutMaxCount(500)
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(false)
                    .setCallback(new BottomInputDialog.OnCommonInputWindowListener() {
                        @Override
                        public void onLeftTextClick(String s) {
                            if (bottomInputDialog != null) {
                                bottomInputDialog.dismiss();
                                bottomInputDialog = null;
                            }
                        }

                        @Override
                        public void onRightTextClick(String s) {
                            if (bottomInputDialog != null) {
                                bottomInputDialog.dismiss();
                                bottomInputDialog = null;
                            }
                        }

                        @Override
                        public void onWindowDismiss() {
                            if (bottomInputDialog != null) {
                                bottomInputDialog.dismiss();
                                bottomInputDialog = null;
                            }
                        }
                    }).create();
            bottomInputDialog.show();
        }
    }

    private List<ButtonItemBean> buttonItemList = new ArrayList<>();
    private BottomHandleDialog bottomHandleDialog;

    private void showBottomHandleDialog() {
        if (buttonItemList.size() == 0) {
            buttonItemList.add(new ButtonItemBean("图片"));
            buttonItemList.add(new ButtonItemBean("拍照"));
            buttonItemList.add(new ButtonItemBean("文件"));
        }
        if (bottomHandleDialog == null) {
            bottomHandleDialog = new BottomHandleDialog.Builder(CommonDialogActivity.this)
                    .setTitle("请选择")
                    .setCancelable(true)
                    .setCanceledOnTouchOutside(true)
                    .setButtonItemList(buttonItemList)
                    .setLeftText("取消")
                    .setRightText("确定")
                    .setCallback(new BottomHandleDialog.OnCommonBottomSelectWindowListener() {
                        @Override
                        public void onLeftTextClick() {
                            if (bottomHandleDialog != null) {
                                bottomHandleDialog.dismiss();
                                bottomHandleDialog = null;
                            }
                            ToastUtils.getInstance().showShortToast(App.getAppContext(), "取消");
                        }

                        @Override
                        public void onRightTextClick() {
                            if (bottomHandleDialog != null) {
                                bottomHandleDialog.dismiss();
                                bottomHandleDialog = null;
                            }
                            ToastUtils.getInstance().showShortToast(App.getAppContext(), "确定");
                        }

                        @Override
                        public void onButtonItemClick(int position) {
                            if (bottomHandleDialog != null) {
                                bottomHandleDialog.dismiss();
                                bottomHandleDialog = null;
                            }
                            ToastUtils.getInstance().showShortToast(App.getAppContext(), buttonItemList.get(position).itemName);
                        }

                        @Override
                        public void onWindowDismiss() {
                            if (bottomHandleDialog != null) {
                                bottomHandleDialog.dismiss();
                                bottomHandleDialog = null;
                            }
                        }
                    }).create();
            bottomHandleDialog.show();
        }
    }
}