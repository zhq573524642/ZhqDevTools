package com.zhq.devtools.widget.material.palette;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhq.devtools.R;

import java.util.List;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/26 11:12
 * Description
 */
public class ColorShowListAdapter extends RecyclerView.Adapter<ColorShowListAdapter.ItemViewHolder> {

    private List<ItemColorBean> data;

    public ColorShowListAdapter(List<ItemColorBean> list) {
        data = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color_show, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemColorBean bean = data.get(position);
        holder.tvItemName.setText(bean.title);
        holder.tvItemName.setTextColor(bean.titleColor);
        holder.llParentLayout.setBackgroundColor(bean.bgColor);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout llParentLayout;
        private final TextView tvItemName;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            llParentLayout = itemView.findViewById(R.id.ll_parent_layout);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
        }
    }
}
