package com.zhq.devtools.widget.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhq.devtools.App;
import com.zhq.devtools.R;
import com.zhq.toolslib.glide.GlideUtils;

import java.util.List;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/20 16:34
 * Description
 */
public class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ItemViewHolder> {

    private final LayoutInflater layoutInflater;
    private Context context;
    private List<String> data;
    public ViewPager2Adapter(Context context,List<String> list) {
        this.context=context;
        layoutInflater = LayoutInflater.from(context);
        data=list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_view_pager_2, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String s = data.get(position);
        GlideUtils.getInstance().init(App.getAppContext())
                .configRequestOptions()
                .loadImageData(s)
                .loadImage(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
