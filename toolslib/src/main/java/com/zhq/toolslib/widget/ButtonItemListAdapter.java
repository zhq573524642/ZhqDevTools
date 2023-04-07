package com.zhq.toolslib.widget;


import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhq.toolslib.R;

import java.util.List;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/3/10 17:17
 * Description
 */
public class ButtonItemListAdapter extends RecyclerView.Adapter<ButtonItemListAdapter.ItemViewHolder> {

    private List<ButtonItemBean> data;

    public ButtonItemListAdapter(List<ButtonItemBean> list) {
        data = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_select_window, parent, false);
        ItemViewHolder holder = new ItemViewHolder(inflate);
        holder.flItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonItemClickListener != null) {
                    onButtonItemClickListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ButtonItemBean bean = data.get(position);
        holder.tvItemName.setText(bean.itemName);
        if (!TextUtils.isEmpty(bean.itemNameColor)) {
            holder.tvItemName.setTextColor(Color.parseColor(bean.itemNameColor));
        }
        if (bean.itemTextSizeSp > 0) {
            holder.tvItemName.setTextSize(TypedValue.COMPLEX_UNIT_SP, bean.itemTextSizeSp);
        }
        if (bean.itemBgResId != 0) {
            holder.flItem.setBackgroundResource(bean.itemBgResId);
        }
        holder.viewLine.setVisibility(position == data.size() - 1 ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final FrameLayout flItem;
        private final TextView tvItemName;
        private final View viewLine;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            flItem = itemView.findViewById(R.id.fl_item);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
            viewLine = itemView.findViewById(R.id.view_line);
        }
    }

    public interface OnButtonItemClickListener {
        void onItemClick(int position);
    }

    private OnButtonItemClickListener onButtonItemClickListener;

    public void setOnButtonItemClickListener(OnButtonItemClickListener onButtonItemClickListener) {
        this.onButtonItemClickListener = onButtonItemClickListener;
    }
}
