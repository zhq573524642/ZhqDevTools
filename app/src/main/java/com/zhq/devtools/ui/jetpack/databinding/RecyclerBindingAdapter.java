package com.zhq.devtools.ui.jetpack.databinding;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ItemBindingDataViewBinding;

import java.util.List;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/3 15:48
 * Description
 */
public class RecyclerBindingAdapter extends RecyclerView.Adapter<RecyclerBindingAdapter.MyViewHolder> {

    private List<UserInfoBean> data;

    public RecyclerBindingAdapter(List<UserInfoBean> list) {
        data = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBindingDataViewBinding viewBinding = (ItemBindingDataViewBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_binding_data_view, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(viewBinding);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserInfoBean bean = data.get(position);
        holder.itemBindingDataViewBinding.setUserInfo(bean);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final ItemBindingDataViewBinding itemBindingDataViewBinding;

        public MyViewHolder(@NonNull ItemBindingDataViewBinding itemView) {
            super(itemView.getRoot());
            itemBindingDataViewBinding = itemView;

        }
    }
}
