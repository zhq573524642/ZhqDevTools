package com.zhq.toolslib.window;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.zhq.toolslib.density.DensityUtil;
import com.zhq.toolslib.R;

import java.util.List;

public class BottomHandleDialog extends BottomSheetDialog {

    public BottomHandleDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    public static class Builder {

        private final Context mContext;
        private final BottomHandleDialog bottomPopupWindow;
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
        private final RecyclerView recyclerView;
        private final LinearLayout llCancel;
        private final TextView tvCancel;
        private final LinearLayout llDialogParent;


        private String title;
        private View containerView;
        private OnCommonBottomSelectWindowListener onCommonBottomSelectWindowListener;
        private int leftImage;
        private String leftText;
        private int rightImage;
        private String rightText;
        private List<ButtonItemBean> buttonItemList;
        private boolean isShowBottomCancel = true;
        private int animStyleResId;
        private int dialogBgResId;
        private boolean isCancelable = true;
        private boolean isCanceledOnTouchOutside = true;

        public Builder(Context context) {
            mContext = context;
            bottomPopupWindow = new BottomHandleDialog(context, R.style.DialogNoTitleStyleBg);
            contentView = LayoutInflater.from(context).inflate(R.layout.layout_bottom_handle_dialog, null);
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
            recyclerView = contentView.findViewById(R.id.recyclerView);
            llCancel = contentView.findViewById(R.id.ll_cancel);
            tvCancel = contentView.findViewById(R.id.tv_cancel);
            llDialogParent = contentView.findViewById(R.id.ll_dialog_parent);
            llCancel.setOnClickListener(view -> {
                bottomPopupWindow.dismiss();
                if (onCommonBottomSelectWindowListener != null) {
                    onCommonBottomSelectWindowListener.onBottomCancelClick();
                }
            });
            flWindowParent.setOnClickListener(v -> {
                if (isCanceledOnTouchOutside) {
                    bottomPopupWindow.dismiss();
                }
            });
            flLeftImage.setOnClickListener(v -> {
                if (onCommonBottomSelectWindowListener != null) {
                    onCommonBottomSelectWindowListener.onLeftImageClick();
                }
            });
            flLeftText.setOnClickListener(view -> {
                if (onCommonBottomSelectWindowListener != null) {
                    onCommonBottomSelectWindowListener.onLeftTextClick();
                }
            });
            flRightImage.setOnClickListener(v -> {
                if (onCommonBottomSelectWindowListener != null) {
                    onCommonBottomSelectWindowListener.onRightImageClick();
                }
            });
            flRightText.setOnClickListener(view -> {
                if (onCommonBottomSelectWindowListener != null) {
                    onCommonBottomSelectWindowListener.onRightTextClick();
                }
            });
            bottomPopupWindow.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (onCommonBottomSelectWindowListener != null) {
                        onCommonBottomSelectWindowListener.onWindowDismiss();
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

        public Builder setButtonItemList(List<ButtonItemBean> buttonItemList) {
            this.buttonItemList = buttonItemList;
            return this;
        }

        public Builder isShowBottomCancel(boolean isShowBottomCancel) {
            this.isShowBottomCancel = isShowBottomCancel;
            return this;
        }

        public Builder setBottomDialogAnimations(@StyleRes int animStyleResId) {
            this.animStyleResId = animStyleResId;
            return this;
        }

        public Builder setDialogBgResId(int dialogBgResId) {
            this.dialogBgResId = dialogBgResId;
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
            this.isCancelable = isCancel;
            bottomPopupWindow.setCancelable(isCancel);
            return this;
        }

        public Builder setCallback(OnCommonBottomSelectWindowListener onCommonBottomSelectWindowListener) {
            this.onCommonBottomSelectWindowListener = onCommonBottomSelectWindowListener;
            return this;
        }


        private void initData() {
            if (dialogBgResId != 0) {
                llDialogParent.setBackgroundResource(dialogBgResId);
            }
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
            llCancel.setVisibility(isShowBottomCancel ? View.VISIBLE : View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            ButtonItemListAdapter buttonItemListAdapter = new ButtonItemListAdapter(buttonItemList);
            recyclerView.setAdapter(buttonItemListAdapter);
            buttonItemListAdapter.setOnButtonItemClickListener(new ButtonItemListAdapter.OnButtonItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    bottomPopupWindow.dismiss();
                    if (onCommonBottomSelectWindowListener != null) {
                        onCommonBottomSelectWindowListener.onButtonItemClick(position);
                    }
                }
            });
        }


        public BottomHandleDialog create() {
            initData();
            bottomPopupWindow.setContentView(contentView);
            // 设置对话框宽
            Window window = bottomPopupWindow.getWindow();
            WindowManager.LayoutParams aWmLp = window.getAttributes();
            aWmLp.width = DensityUtil.getScreenWidth(mContext);
            aWmLp.gravity = Gravity.BOTTOM;
            window.setAttributes(aWmLp);
            if (animStyleResId != 0) {
                window.setWindowAnimations(animStyleResId);
            } else {
                window.setWindowAnimations(R.style.anim_style_bottom);
            }
            return bottomPopupWindow;
        }

    }

    public interface OnCommonBottomSelectWindowListener {
        default void onLeftImageClick() {

        }

        default void onLeftTextClick() {

        }

        default void onRightImageClick() {

        }

        default void onRightTextClick() {

        }

        default void onBottomCancelClick() {

        }

        default void onButtonItemClick(int position) {

        }

        default void onWindowDismiss() {

        }

    }
}
