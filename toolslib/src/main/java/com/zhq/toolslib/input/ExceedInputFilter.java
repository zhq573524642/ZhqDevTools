package com.zhq.toolslib.input;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * @Author ZhangHuiQiang
 * @Date 2022/12/6 11:26
 * Description 最大可输入监听
 *
 * ExceedInputFilter exceedInputFilter = new ExceedInputFilter(maxInputCount);
 *             exceedInputFilter.setListener(new ExceedInputFilter.OnTextExceedListener() {
 *                 @Override
 *                 public void onTextExceed() {
 *                     ToastUtils.getInstance().showShortToast(mContext, "最多可输入" + maxInputCount + "字");
 *                 }
 *             });
 *             etInput.setFilters(new InputFilter[]{exceedInputFilter});
 */
public class ExceedInputFilter extends InputFilter.LengthFilter{

    private int max;
    private OnTextExceedListener listener;

    public void setListener(OnTextExceedListener listener) {
        this.listener = listener;
    }

    public interface OnTextExceedListener {
        void onTextExceed();
    }

    public ExceedInputFilter(int max) {
        super(max);
        this.max = max;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        boolean notified = false;
        int keep = max - (dest.length() - (dend - dstart));
        if (keep <= 0) {
            // 1：当前字数已经是max那么多个了
            if (null != listener) {
                notified = true;
                listener.onTextExceed();
            }
        }
        CharSequence tmp = super.filter(source, start, end, dest, dstart, dend);
        if (null != tmp) {
            String result = tmp.toString();
            if (result.length() < (end - start)
                    && !result.contains("'")
                    && !notified) {
                if (null != listener) {
                    listener.onTextExceed();
                }
            }
        }
        return tmp;
    }
}
