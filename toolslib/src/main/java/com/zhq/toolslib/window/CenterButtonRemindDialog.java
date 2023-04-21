package com.zhq.toolslib.window;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.zhq.toolslib.R;


public class CenterButtonRemindDialog extends Dialog {


    protected CenterButtonRemindDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    public static class Builder {

        private final CenterButtonRemindDialog centerButtonRemindDialog;
        private final Context mContext;
        private final LinearLayout llDialogLayout;
        private final TextView tvDialogTitle;
        private final TextView tvLeftHandle;
        private final TextView tvRightHandle;
        private final View lineVertical;
        private final View lineHorizontal;
        private final View contentView;
        private final TextView tvDialogContent;
        private final FrameLayout flLeftHandle;
        private final FrameLayout flRightHandle;
        private final FrameLayout flWindowParent;

        private String title = "";
        private String titleColor;
        private float titleTextSize;
        private String content = "";
        private String contentColor;
        private float contentTextSize;
        private String leftText = "取消";
        private String rightText = "确定";
        private String leftTextColor;
        private String rightTextColor;
        private float buttonTextSize;
        private String lineColor;
        private OnCenterButtonRemindDialogListener onCenterButtonRemindDialogListener;

        public Builder(Context context) {
            mContext = context;
            centerButtonRemindDialog = new CenterButtonRemindDialog(mContext, R.style.DialogNoTitleStyleBg);
            contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_common_simple_remind, null);
            llDialogLayout = contentView.findViewById(R.id.ll_dialog_layout);
            tvDialogTitle = contentView.findViewById(R.id.tv_dialog_title);
            tvDialogContent = contentView.findViewById(R.id.tv_dialog_content);
            flLeftHandle = contentView.findViewById(R.id.fl_left_handle);
            flRightHandle = contentView.findViewById(R.id.fl_right_handle);
            tvLeftHandle = contentView.findViewById(R.id.tv_left_handle);
            tvRightHandle = contentView.findViewById(R.id.tv_right_handle);
            lineVertical = contentView.findViewById(R.id.line_vertical);
            lineHorizontal = contentView.findViewById(R.id.line_horizontal);
            flWindowParent = contentView.findViewById(R.id.fl_window_parent);
            flWindowParent.setOnClickListener(v -> {
                if (isCanceledOnTouchOutside) {
                    centerButtonRemindDialog.dismiss();
                }
            });
            flLeftHandle.setOnClickListener(v -> {
                if (onCenterButtonRemindDialogListener != null) {
                    onCenterButtonRemindDialogListener.onLeftClick();
                }
            });
            flRightHandle.setOnClickListener(v -> {
                if (onCenterButtonRemindDialogListener != null) {
                    onCenterButtonRemindDialogListener.onRightClick();
                }
            });
            centerButtonRemindDialog.setOnDismissListener(dialog -> {
                if (onCenterButtonRemindDialogListener != null) {
                    onCenterButtonRemindDialogListener.onDialogDismiss();
                }
            });
        }

        public Builder setDialogTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDialogTitleTextColor(String colorStr) {
            this.titleColor = colorStr;
            return this;
        }

        public Builder setDialogTitleTextSize(float textSize) {
            this.titleTextSize = textSize;
            return this;
        }

        public Builder setDialogContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setDialogContentColor(String colorStr) {
            this.contentColor = colorStr;
            return this;
        }

        public Builder setDialogContentTextSize(float textSize) {
            this.contentTextSize = textSize;
            return this;
        }

        public Builder setLeftText(String leftText) {
            this.leftText = leftText;
            return this;
        }

        public Builder setRightText(String rightText) {
            this.rightText = rightText;
            return this;
        }

        public Builder setLeftTextColor(String colorStr) {
            this.leftTextColor = colorStr;
            return this;
        }

        public Builder setRightTextColor(String colorStr) {
            this.rightTextColor = colorStr;
            return this;
        }

        public Builder setButtonTextSize(float textSize) {
            this.buttonTextSize = textSize;
            return this;
        }

        public Builder setLineColor(String colorStr) {
            this.lineColor = colorStr;
            return this;
        }

        public Builder setCancelable(boolean isCancel) {
            centerButtonRemindDialog.setCancelable(isCancel);
            return this;
        }

        private boolean isCanceledOnTouchOutside;

        public Builder setCanceledOnTouchOutside(boolean isCancel) {
            this.isCanceledOnTouchOutside = isCancel;
            centerButtonRemindDialog.setCanceledOnTouchOutside(isCancel);
            return this;
        }

        public Builder setCallback(OnCenterButtonRemindDialogListener onCenterButtonRemindDialogListener) {
            this.onCenterButtonRemindDialogListener = onCenterButtonRemindDialogListener;
            return this;
        }

        private int animStyleId;

        public Builder setDialogAnimationStyle(@StyleRes int animStyleId) {
            this.animStyleId = animStyleId;
            return this;
        }

        public CenterButtonRemindDialog create() {
            initData();
            centerButtonRemindDialog.setContentView(contentView);
            Window window = centerButtonRemindDialog.getWindow();
            WindowManager.LayoutParams aWmLp = window.getAttributes();
            aWmLp.width = WindowManager.LayoutParams.MATCH_PARENT;
            aWmLp.height = WindowManager.LayoutParams.MATCH_PARENT;
            aWmLp.gravity = Gravity.CENTER;
            window.setAttributes(aWmLp);
            if (animStyleId != 0) {
                window.setWindowAnimations(animStyleId);
            } else {
                window.setWindowAnimations(R.style.anim_style_center);
            }
            return centerButtonRemindDialog;
        }

        private void initData() {
            tvDialogTitle.setText(title);
            if (!TextUtils.isEmpty(titleColor)) {
                tvDialogTitle.setTextColor(Color.parseColor(titleColor));
            }
            if (titleTextSize > 0) {
                tvDialogTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize);
            }
            tvDialogContent.setText(content);
            if (!TextUtils.isEmpty(contentColor)) {
                tvDialogContent.setTextColor(Color.parseColor(contentColor));
            }
            if (contentTextSize > 0) {
                tvDialogContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, contentTextSize);
            }
            tvLeftHandle.setText(leftText);
            if (!TextUtils.isEmpty(leftTextColor)) {
                tvLeftHandle.setTextColor(Color.parseColor(leftTextColor));
            }
            tvRightHandle.setText(rightText);
            if (!TextUtils.isEmpty(rightTextColor)) {
                tvRightHandle.setTextColor(Color.parseColor(rightTextColor));
            }
            if (buttonTextSize > 0) {
                tvLeftHandle.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
                tvRightHandle.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize);
            }

            if (!TextUtils.isEmpty(lineColor)) {
                lineVertical.setBackgroundColor(Color.parseColor(lineColor));
                lineHorizontal.setBackgroundColor(Color.parseColor(lineColor));
            }
        }


    }


    public interface OnCenterButtonRemindDialogListener {
        void onLeftClick();

        void onRightClick();

        void onDialogDismiss();
    }

    private OnCenterButtonRemindDialogListener onCenterButtonRemindDialogListener;

    public void setOnCenterButtonRemindDialogListener(OnCenterButtonRemindDialogListener onCenterButtonRemindDialogListener) {
        this.onCenterButtonRemindDialogListener = onCenterButtonRemindDialogListener;
    }
}
