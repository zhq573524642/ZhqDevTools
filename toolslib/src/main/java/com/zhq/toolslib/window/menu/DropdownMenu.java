package com.zhq.toolslib.window.menu;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhq.toolslib.R;

import java.util.List;


public class DropdownMenu extends RelativeLayout {

    @SuppressWarnings("FieldCanBeLocal")
    private Context mContext;
    private PopupWindow mPopupWindow;
    private FixedHeightListView mListView;
    @SuppressWarnings("FieldCanBeLocal")
    private RelativeLayout mShadowLayout;
    private OnDropdownItemClickListener mItemClickListener;
    private TextView mTextTitle;
    private ImageView mIconView;
    private DropdownAdapter mDropdownAdapter;
    private OnClickListener mSecondClickListener;

    private static final int NO_HIGHLIGHT = -1;
    private int highLightColor;
    private int textColor;
    private int iconExpanded, iconCollapse;
    private int showPaddingLeft, showPaddingRight;
    private String titleText;
    private int listItemSelectedBg;
    private int listItemUnSelectedBg;
    private int listItemTextSelectedColor;
    private int listItemTextUnselectedColor;

    public DropdownMenu(Context context) {
        super(context);
        init(context, null);
    }

    public DropdownMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DropdownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DropdownMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        //自定义属性
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.DropdownMenu);
        titleText = attributes.getString(R.styleable.DropdownMenu_titleText);
        float textSize = attributes.getDimensionPixelSize(R.styleable.DropdownMenu_titleTextSize, 14);
        textColor = attributes.getColor(R.styleable.DropdownMenu_titleColor, 0xff595959);
        int titleBgColor = attributes.getColor(R.styleable.DropdownMenu_titleBgColor, 0x00ffffff);
        int listBgColor = attributes.getColor(R.styleable.DropdownMenu_listBgColor, 0xffffffff);
        highLightColor = attributes.getColor(R.styleable.DropdownMenu_titleHighLight, 0xffffffff);

        showPaddingLeft = attributes.getDimensionPixelOffset(R.styleable.DropdownMenu_showPaddingLeft, 20);
        showPaddingRight = attributes.getDimensionPixelOffset(R.styleable.DropdownMenu_showPaddingRight, 20);
        listItemSelectedBg = attributes.getResourceId(R.styleable.DropdownMenu_listItemSelectedBg, R.drawable.shape_cbt_607ff0);
        listItemUnSelectedBg = attributes.getResourceId(R.styleable.DropdownMenu_listItemUnSelectedBg, R.drawable.shape_cbf_f0f0);
        listItemTextSelectedColor = attributes.getColor(R.styleable.DropdownMenu_listItemTextSelectedColor, 0xffffffff);
        listItemTextUnselectedColor = attributes.getColor(R.styleable.DropdownMenu_listItemTextUnSelectedColor, 0xff595959);
        iconExpanded = attributes.getResourceId(R.styleable.DropdownMenu_iconExpanded, R.drawable.ic_drop_exp);
        iconCollapse = attributes.getResourceId(R.styleable.DropdownMenu_iconExpanded, R.drawable.ic_drop_coll);
        mIconView = new ImageView(mContext);
        mIconView.setImageResource(iconCollapse);
        attributes.recycle();

        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.CENTER);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupWindow = inflater.inflate(R.layout.dropdown_popup, (ViewGroup) getParent(), false);
        mPopupWindow = new PopupWindow(popupWindow, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mListView = (FixedHeightListView) popupWindow.findViewById(R.id.lv_menu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mListView.setLayoutManager(layoutManager);
        mListView.setBackgroundColor(listBgColor);
        mListView.setPadding(showPaddingLeft, 30, showPaddingRight, 30);
        mDropdownAdapter = new DropdownAdapter();
        mListView.setAdapter(mDropdownAdapter);
        mDropdownAdapter.setItemStateBg(listItemSelectedBg, listItemUnSelectedBg);
        mDropdownAdapter.setItemTextStateBg(listItemTextSelectedColor,listItemTextUnselectedColor);
        mShadowLayout = (RelativeLayout) popupWindow.findViewById(R.id.rl_menu_shadow);
        mShadowLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        mDropdownAdapter.setOnDropMenuItemClickListener(position -> {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(position);
            }
            mDropdownAdapter.setSelectPosition(position);
//            mTextTitle.setText(mDropdownAdapter.getTitleString(position));
////            if (highLightColor != -1)//选择后标题修改颜色
////                mTextTitle.setTextColor(highLightColor);
//            setBackgroundResource(R.drawable.shape_cbt_607ff0);
//            mTextTitle.setTextColor(highLightColor);
//            mIconView.setImageResource(iconExpanded);
            select(mDropdownAdapter.getTitleString(position));
            mPopupWindow.dismiss();
        });

        mTextTitle = new TextView(mContext);

        LayoutParams titleParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(CENTER_IN_PARENT, TRUE);
        mTextTitle.setLayoutParams(titleParams);
        titleText = TextUtils.isEmpty(titleText) ? "<请选择>" : titleText;
        mTextTitle.setText(titleText);
//        mTextTitle.setMaxLines(1);
        mTextTitle.setSingleLine(true);
        mTextTitle.setTextColor(textColor);
        mTextTitle.setPadding(20, 0, 72, 0);
        mTextTitle.setEllipsize(TextUtils.TruncateAt.END);
        mTextTitle.setGravity(Gravity.CENTER);
        if (textSize > 0) {
            mTextTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }

        LayoutParams iconParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iconParams.addRule(ALIGN_PARENT_RIGHT, TRUE);
        iconParams.addRule(CENTER_VERTICAL, TRUE);
        mIconView.setLayoutParams(iconParams);

        mIconView.setPadding(20, 0, 32, 0);

        addView(mTextTitle);
        addView(mIconView);
        setBackgroundResource(R.drawable.shape_cbf_f0f0);
        mPopupWindow.setOnDismissListener(() -> finish());
        mPopupWindow.setOutsideTouchable(true);
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
//                    finish();
                } else {
                    expand();

                }
                if (mSecondClickListener != null) {
                    mSecondClickListener.onClick(DropdownMenu.this);
                }
            }
        });

    }

    private void finish() {
//        setBackgroundResource(R.drawable.shape_cbf_f0f0);
//        mTextTitle.setTextColor(textColor);
//        mIconView.setImageResource(iconCollapse);
    }


    public void setAdapter(DropdownAdapter adapter) {
        mListView.setAdapter(mDropdownAdapter = adapter);
    }

    public void setData(List<String> list) {
        mDropdownAdapter.setNewData(list);
    }

    public void setOnItemClickListener(final OnDropdownItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void setCustomView(final ViewGroup contentView) {
        LinearLayout container = (LinearLayout) mPopupWindow.getContentView().findViewById(R.id.container);
        container.removeAllViews();
        container.addView(contentView);
    }

    public void setCustomViewClick(
            final ViewGroup contentView,
            final View customView,
            final OnClickListener listener) {

        LinearLayout container = (LinearLayout) mPopupWindow.getContentView().findViewById(R.id.container);

        container.removeAllViews();
        container.addView(contentView);
        if (customView != null) {
            customView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v);
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                }
            });
        }
    }

    @SuppressWarnings("unused")
    public void setTitle(String title) {
        titleText = title;
        mTextTitle.setText(title);
    }

    @SuppressWarnings("unused")
    public boolean isDropdown() {
        return mPopupWindow != null && mPopupWindow.isShowing();
    }

    @SuppressWarnings("unused")
    public void expand() {
//        setBackgroundResource(R.drawable.shape_cbt_607ff0);
//        mTextTitle.setTextColor(highLightColor);
//        mIconView.setImageResource(iconExpanded);
        if (mPopupWindow != null) {
//            if (Build.VERSION.SDK_INT >= 24) { // 解决部分手机位置错误的问题
//                Rect visibleFrame = new Rect();
//                this.getGlobalVisibleRect(visibleFrame);
////                int height = this.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
////                mPopupWindow.setHeight(height);
////                mPopupWindow.showAsDropDown(this, 0, 0);
//                MarginLayoutParams mparams = (MarginLayoutParams) getLayoutParams();//为解决双向转诊
//                int topMargin = 0;
//                if (mparams.topMargin < 0) {
//                    topMargin = mparams.topMargin;
//                }
//                LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, visibleFrame.top + topMargin + 20);
//                mVTop.setLayoutParams(layoutParams);
//                mPopupWindow.showAtLocation(this, Gravity.NO_GRAVITY, 0, 0);
//            } else {
//                mPopupWindow.showAsDropDown(this, 0, 0);
//            }
            mPopupWindow.showAsDropDown(this);
        }
    }

    public void collapse() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    public void addOnClickListener(OnClickListener l) {
        mSecondClickListener = l;
    }

    public FixedHeightListView getListView() {
        return mListView;
    }

    @SuppressWarnings("unused")
    public TextView getTitleView() {
        return mTextTitle;
    }

    public void setSelectPositon(int positon) {
        if (mDropdownAdapter != null) mDropdownAdapter.setSelectPosition(positon);
    }

    /**
     * 选择后
     *
     * @param str
     */
    public void select(String str) {
        mTextTitle.setText(TextUtils.isEmpty(str) ? titleText : str);
        setBackgroundResource(R.drawable.shape_cbt_607ff0);
        mTextTitle.setTextColor(highLightColor);
        mIconView.setImageResource(iconExpanded);
    }

    /**
     * 重置
     */
    public void reset() {
        mTextTitle.setText(titleText);
        setBackgroundResource(R.drawable.shape_cbf_f0f0);
        mTextTitle.setTextColor(textColor);
        mIconView.setImageResource(iconCollapse);
    }
}
