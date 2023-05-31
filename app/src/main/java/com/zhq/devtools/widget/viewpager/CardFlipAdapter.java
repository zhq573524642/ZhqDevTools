package com.zhq.devtools.widget.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhq.devtools.R;
import com.zhq.toolslib.glide.GlideUtils;

import java.util.List;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/28 16:14
 * Description
 */
public class CardFlipAdapter extends RecyclerView.Adapter<CardFlipAdapter.ItemViewHolder> {

    private List<String> data;
    private Context context;

    public CardFlipAdapter(Context context, List<String> list) {
        data = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_flip, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String s = data.get(position);
        GlideUtils.getInstance().init(context)
                .configRequestOptions()
                .loadImageData(s)
                .loadImage(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
