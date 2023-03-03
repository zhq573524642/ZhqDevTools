package com.zhq.devtools.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.zhq.devtools.R;
import com.zhq.devtools.bean.MainMenuItem;

import java.util.List;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/2/20 13:38
 * Description
 */
public class MainMenuListAdapter extends RecyclerView.Adapter<MainMenuListAdapter.MyViewHolder> {
    private List<MainMenuItem> data;

    public MainMenuListAdapter(List<MainMenuItem> list) {
        data = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_menu_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvItemName.setText(data.get(position).name);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final CardView cvItem;
        private final TextView tvItemName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cvItem = itemView.findViewById(R.id.cv_item);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
        }
    }
}
