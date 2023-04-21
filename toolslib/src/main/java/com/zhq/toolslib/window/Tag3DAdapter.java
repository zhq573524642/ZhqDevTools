package com.zhq.toolslib.window;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moxun.tagcloudlib.view.TagsAdapter;
import com.zhq.toolslib.glide.GlideUtils;
import com.zhq.toolslib.R;

import java.util.List;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/3/29 17:42
 * Description
 */
public class Tag3DAdapter extends TagsAdapter {

    private List<TagsBean> data;

    public Tag3DAdapter(List<TagsBean> list) {
        data = list;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public View getView(Context context, int position, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_30_tags, null);
        ImageView ivAvatar = view.findViewById(R.id.iv_avatar);
        TextView tvName = view.findViewById(R.id.tv_name);
        TagsBean bean = data.get(position);
        tvName.setText(bean.name);
        GlideUtils.getInstance().init(parent.getContext())
                .loadImageData(bean.image).configRequestOptions().loadImage(ivAvatar);
        return view;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return position % 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {

    }
}
