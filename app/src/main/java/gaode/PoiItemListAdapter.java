package gaode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.PoiItemV2;
import com.zhq.devtools.R;

import java.util.List;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/4/18 16:49
 * Description
 */
public class PoiItemListAdapter extends RecyclerView.Adapter<PoiItemListAdapter.ItemViewHolder> {
    private List<PoiItemV2> data;

    public PoiItemListAdapter(List<PoiItemV2> list) {
        data = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poi_info, parent, false);
        ItemViewHolder holder = new ItemViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        PoiItemV2 item = data.get(position);
        String s = "title:" + item.getTitle() + "\n" +
                "cityName:" + item.getCityName() + " cityCode:" + item.getCityCode() + "\n" +
                "ProvinceName:"+item.getProvinceName()+" ProvinceCode:"+item.getProvinceCode()+"\n"+
                "adName:" + item.getAdName() + " adCode:" + item.getAdCode() + "\n" +
                "ParkingType:" +  "\n" +
                "PostCode:" + "\n"+
                "PoiId:"+item.getPoiId()+"\n"+
                "Snippet:"+item.getSnippet()+"\n"+
                "TypeDesc:"+item.getTypeDes()+" TypeCode:"+item.getTypeCode();

        holder.tvItemName.setText(s);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvItemName;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
        }
    }
}
