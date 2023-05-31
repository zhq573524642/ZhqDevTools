package com.zhq.toolslib.window.menu;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhq.toolslib.R;

import java.util.ArrayList;
import java.util.List;


public class DropdownAdapter extends RecyclerView.Adapter<DropdownAdapter.ItemViewHolder> {

    private int mSelectPosition = -1;
    private List<String> data = new ArrayList<>();
    private int selectedBg = R.drawable.shape_cbt_607ff0;
    private int unselectedBg = R.drawable.shape_cbf_f0f0;

    private int selectedTextColor = 0xFFFFFFFF;
    private int unselectedTextColor = 0xFF595959;

    public void setNewData(List<String> list) {
        data = list;
        notifyDataSetChanged();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvDrop;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDrop = itemView.findViewById(R.id.tv_drop);
        }
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drop_menu, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            if (onDropMenuItemClickListener != null) {
                onDropMenuItemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvDrop.setText(data.get(position));
        if (position == mSelectPosition) {
            holder.tvDrop.setBackgroundResource(selectedBg);
            holder.tvDrop.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.tvDrop.setBackgroundResource(unselectedBg);
            holder.tvDrop.setTextColor(Color.parseColor("#595959"));
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setItemStateBg(int selectedBg, int unselectedBg) {
        this.selectedBg = selectedBg;
        this.unselectedBg = unselectedBg;
    }

    public void setItemTextStateBg(int selectedTextColor, int unselectedTextColor) {
        this.selectedTextColor = selectedTextColor;
        this.unselectedTextColor = unselectedTextColor;
    }

    public String getTitleString(int position) {
        return data == null ? "" : data.get(position);
    }

    public void setSelectPosition(int selectPosition) {
        this.mSelectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public interface OnDropMenuItemClickListener {
        void onItemClick(int position);
    }

    private OnDropMenuItemClickListener onDropMenuItemClickListener;

    public void setOnDropMenuItemClickListener(OnDropMenuItemClickListener onDropMenuItemClickListener) {
        this.onDropMenuItemClickListener = onDropMenuItemClickListener;
    }
}
