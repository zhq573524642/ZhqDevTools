package com.zhq.toolslib.window;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zhq.toolslib.density.DensityUtil;
import com.zhq.toolslib.input.ExceedInputFilter;
import com.zhq.toolslib.R;
import com.zhq.toolslib.toast.ToastUtils;

public class BottomInputDialog extends Dialog {

    public BottomInputDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    public static class Builder {

        private final Context mContext;
        private final BottomInputDialog bottomPopupWindow;
        private final View contentView;
        private final TextView tvTitle;
        private final FrameLayout flWindowParent;
        private final FrameLayout flLeftImage;
        private final ImageView ivLeftImage;
        private final FrameLayout flLeftText;
        private final TextView tvLeftText;
        private final FrameLayout flRightImage;
        private final ImageView ivRightImage;
        private final FrameLayout flRightText;
        private final TextView tvRightText;
        private final FrameLayout flContainer;
        private final EditText etInput;
        private String title;
        private View containerView;
        private OnCommonInputWindowListener onCommonInputWindowListener;
        private int leftImage;
        private String leftText;
        private int rightImage;
        private String rightText;
        private int maxInputCount = 500;
        private String hintText = "请输入";
        private String oldContent;
        private boolean isCanceledOnTouchOutside;

        public Builder(Context context) {
            mContext = context;
            bottomPopupWindow = new BottomInputDialog(context, R.style.DialogNoTitleStyleBg);
            contentView = LayoutInflater.from(context).inflate(R.layout.layout_bottom_input_dialog, null);
            flWindowParent = contentView.findViewById(R.id.fl_window_parent);
            tvTitle = contentView.findViewById(R.id.tv_title);
            flLeftImage = contentView.findViewById(R.id.fl_left_image);
            ivLeftImage = contentView.findViewById(R.id.iv_left_image);
            flLeftText = contentView.findViewById(R.id.fl_left_text);
            tvLeftText = contentView.findViewById(R.id.tv_left_text);
            flRightImage = contentView.findViewById(R.id.fl_right_image);
            ivRightImage = contentView.findViewById(R.id.iv_right_image);
            flRightText = contentView.findViewById(R.id.fl_right_text);
            tvRightText = contentView.findViewById(R.id.tv_right_text);
            flContainer = contentView.findViewById(R.id.fl_container);
            etInput = contentView.findViewById(R.id.et_input);
            flWindowParent.setOnClickListener(v -> {
                if (isCanceledOnTouchOutside) {
                    bottomPopupWindow.dismiss();
                }
            });
            flLeftImage.setOnClickListener(v -> {
                if (onCommonInputWindowListener != null) {
                    onCommonInputWindowListener.onLeftImageClick();
                }
            });
            flLeftText.setOnClickListener(view -> {
                if (onCommonInputWindowListener != null) {
                    String s = etInput.getText().toString();
                    onCommonInputWindowListener.onLeftTextClick(s);
                }
            });
            flRightImage.setOnClickListener(v -> {
                if (onCommonInputWindowListener != null) {
                    onCommonInputWindowListener.onRightImageClick();
                }
            });
            flRightText.setOnClickListener(view -> {
                if (onCommonInputWindowListener != null) {
                    String s = etInput.getText().toString();
                    onCommonInputWindowListener.onRightTextClick(s);
                }
            });
            bottomPopupWindow.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (onCommonInputWindowListener != null) {
                        onCommonInputWindowListener.onWindowDismiss();
                    }
                }
            });

        }

        public Builder setTitle(String title) {
            this.title = title;
            if (tvTitle != null) {
                tvTitle.setText(title);
            }
            return this;
        }


        public Builder setLeftImage(int leftImage) {
            this.leftImage = leftImage;
            return this;
        }

        public Builder setLeftText(String leftText) {
            this.leftText = leftText;
            return this;
        }

        public Builder setRightImage(int rightImage) {
            this.rightImage = rightImage;
            return this;
        }

        public Builder setRightText(String rightText) {
            this.rightText = rightText;
            return this;
        }

        public Builder setOldContent(String oldContent) {
            this.oldContent = oldContent;
            return this;
        }

        public Builder setHintText(String hintText) {
            this.hintText = hintText;
            return this;
        }

        public Builder setInoutMaxCount(int maxInputCount) {
            this.maxInputCount = maxInputCount;
            return this;
        }

        /**
         * 用户不可以点击外部来关闭
         *
         * @param isCancel
         * @return
         */
        public Builder setCanceledOnTouchOutside(boolean isCancel) {
            this.isCanceledOnTouchOutside = isCancel;
            bottomPopupWindow.setCanceledOnTouchOutside(isCancel);
            return this;
        }

        /**
         * 用户可以点击后退键关闭 Dialog
         *
         * @param isCancel
         * @return
         */
        public Builder setCancelable(boolean isCancel) {
            bottomPopupWindow.setCancelable(isCancel);
            return this;
        }

        public Builder setCallback(OnCommonInputWindowListener onCommonInputWindowListener) {
            this.onCommonInputWindowListener = onCommonInputWindowListener;
            return this;
        }


        private void initData() {
            tvTitle.setText(title);
            if (leftImage != 0) {
                flLeftImage.setVisibility(View.VISIBLE);
                ivLeftImage.setImageResource(leftImage);
            } else if (!TextUtils.isEmpty(leftText)) {
                flLeftText.setVisibility(View.VISIBLE);
                tvLeftText.setText(leftText);
            }
            if (rightImage != 0) {
                flRightImage.setVisibility(View.VISIBLE);
                ivRightImage.setImageResource(rightImage);
            } else if (!TextUtils.isEmpty(rightText)) {
                flRightText.setVisibility(View.VISIBLE);
                tvRightText.setText(rightText);
            }
            if (!TextUtils.isEmpty(hintText)) {
                etInput.setHint(hintText);
            }
            if (!TextUtils.isEmpty(oldContent)) {
                etInput.setText(oldContent);
                etInput.setSelection(oldContent.length());
            }
            ExceedInputFilter exceedInputFilter = new ExceedInputFilter(maxInputCount);
            exceedInputFilter.setListener(new ExceedInputFilter.OnTextExceedListener() {
                @Override
                public void onTextExceed() {
                    ToastUtils.getInstance().showShortToast(mContext, "最多可输入" + maxInputCount + "字");
                }
            });
            etInput.setFilters(new InputFilter[]{exceedInputFilter});
        }

        public BottomInputDialog create() {
            initData();
            bottomPopupWindow.setContentView(contentView);
            // 设置对话框宽
            Window window = bottomPopupWindow.getWindow();
            WindowManager.LayoutParams aWmLp = window.getAttributes();
            aWmLp.width = DensityUtil.getScreenWidth(mContext);
            aWmLp.gravity = Gravity.BOTTOM;
            window.setAttributes(aWmLp);
            window.setWindowAnimations(R.style.anim_style_bottom);
            return bottomPopupWindow;
        }

    }

    public interface OnCommonInputWindowListener {
        default void onLeftImageClick() {

        }

        default void onLeftTextClick(String s) {

        }

        default void onRightImageClick() {

        }

        default void onRightTextClick(String s) {

        }

        default void onWindowDismiss() {

        }

    }
}
