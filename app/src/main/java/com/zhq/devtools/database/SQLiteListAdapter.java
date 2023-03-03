package com.zhq.devtools.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhq.devtools.App;
import com.zhq.devtools.R;
import com.zhq.toolslib.GlideUtils;

import java.util.List;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/2/7 15:24
 * Description
 */
public class SQLiteListAdapter extends RecyclerView.Adapter<SQLiteListAdapter.MyViewHolder> {

    private List<PersonInfoBean> data;

    public SQLiteListAdapter(List<PersonInfoBean> list) {
        data = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person_info, parent, false);
        MyViewHolder holder = new MyViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PersonInfoBean bean = data.get(position);
        GlideUtils.getInstance()
                .init(App.getAppContext())
                .loadImageData(bean.avatar)
                .skipMemoryCache(false)
                .setDiskCacheStrategy(GlideUtils.DiskCacheStrategy_AUTOMATIC)
                .dontAnimate(false)
                .setErrorImage(R.mipmap.ic_launcher)
                .setPlaceholder(R.mipmap.ic_launcher_round)
                .setCropType(GlideUtils.SHOW_CIRCLE_CROP)
                .configRequestOptions()
                .loadImageUrl(holder.ivAvatar);
        holder.tvName.setText(bean.name + "  " + bean.age + "岁");
        holder.tvDesc.setText(bean.desc);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivAvatar;
        private final TextView tvName;
        private final TextView tvDesc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }
}
