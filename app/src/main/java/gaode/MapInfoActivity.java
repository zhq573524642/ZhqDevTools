package gaode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.PoiItemV2;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiResultV2;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearchV2;
import com.amap.api.services.poisearch.SubPoiItem;
import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ActivityMapInfoBinding;

import java.util.ArrayList;
import java.util.List;

public class MapInfoActivity extends AppCompatActivity implements PoiSearchV2.OnPoiSearchListener {

    private com.zhq.devtools.databinding.ActivityMapInfoBinding binding;
    private List<PoiItemV2> poiItemList = new ArrayList<>();
    private PoiItemListAdapter poiItemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.btnSearch.setOnClickListener(v -> {
            String inputContent = binding.etInputKey.getText().toString();
            PoiSearchV2.Query query = new PoiSearchV2.Query(inputContent, "", "北京市");
            try {
                PoiSearchV2 poiSearchV2 = new PoiSearchV2(this,query);
                poiSearchV2.setOnPoiSearchListener(this);
                poiSearchV2.searchPOIAsyn();
            } catch (AMapException e) {
                e.printStackTrace();
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        poiItemListAdapter = new PoiItemListAdapter(poiItemList);
        binding.recyclerView.setAdapter(poiItemListAdapter);
    }

    private static final String TAG = "MapInfoActivity";


    @Override
    public void onPoiSearched(PoiResultV2 result, int code) {
        if (code == 1000) {
            ArrayList<PoiItemV2> pois = result.getPois();
            poiItemList.clear();
            if (pois != null && pois.size() > 0) {
                poiItemList.addAll(pois);
            }
            poiItemListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItemV2 poiItemV2, int i) {

    }
}