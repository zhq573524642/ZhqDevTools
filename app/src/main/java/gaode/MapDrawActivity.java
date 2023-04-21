package gaode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;


import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.zhq.devtools.R;
import com.zhq.devtools.databinding.ActivityMapDrawBinding;
import com.zhq.toolslib.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class MapDrawActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityMapDrawBinding binding;
    private AMap aMap;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapDrawBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.mapView.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (marker!=null){
            marker.destroy();
        }
        binding.mapView.onDestroy();
    }

    private void initView() {
        aMap = binding.mapView.getMap();
        binding.btnDrawPoint.setOnClickListener(v -> {
            drawMapPoint();
        });
        binding.btnDrawLine.setOnClickListener(v -> {
            drawMapLine();
        });
        binding.btnDrawCircle.setOnClickListener(v -> {
            drawMapCircle();
        });
    }

    private void drawMapPoint(){
        LatLng latLng = new LatLng(39.906901,116.397972);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("北京")
                .snippet("这是描述啊")
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        marker = aMap.addMarker(markerOptions);
        LatLng targetLatLng = new LatLng(40.04, 116.36);
        Animation animation = new TranslateAnimation(targetLatLng);
        animation.setDuration(2000);
        animation.setInterpolator(new LinearInterpolator());
        marker.setAnimation(animation);
        marker.startAnimation();
        binding.cbCustomInfoWindow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                setMarkerCustomInfoWindow();
            }else {
                aMap.setInfoWindowAdapter(null);
            }
        });
        binding.btnInfoWindowToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (marker!=null){
                     if (marker.isInfoWindowShown()){
                         marker.hideInfoWindow();
                     }else {
                         marker.showInfoWindow();
                     }
                 }
            }
        });
        aMap.setOnMarkerClickListener(marker -> {
            ToastUtils.getInstance().showShortToast(getApplicationContext(),marker.getTitle());
            return false;
        });
        aMap.setOnMarkerDragListener(new AMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        });
    }

    private void drawMapLine(){
        List<LatLng> latLngs = new ArrayList<LatLng>();
        latLngs.add(new LatLng(39.999391,116.135972));
        latLngs.add(new LatLng(39.898323,116.057694));
        latLngs.add(new LatLng(39.900430,116.265061));
        latLngs.add(new LatLng(39.955192,116.140092));
        Polyline polyline = aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));

    }

    private void drawMapCircle(){
        LatLng latLng = new LatLng(39.984059,116.307771);
        Circle circle = aMap.addCircle(new CircleOptions().
                center(latLng).
                radius(1000).
                fillColor(Color.parseColor("#80ff0000")).
                strokeColor(Color.parseColor("#262626")).
                strokeWidth(15));
    }

    private void setMarkerCustomInfoWindow() {
        aMap.setInfoWindowAdapter(new AMap.ImageInfoWindowAdapter() {
            @Override
            public long getInfoWindowUpdateTime() {
                return 0;
            }

            @SuppressLint("InflateParams")
            @Override
            public View getInfoWindow(Marker marker) {
                View inflate = LayoutInflater.from(MapDrawActivity.this).inflate(R.layout.layout_marker_custom_info_window, null);
                return inflate;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }

        });
        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

            }
        });
    }

}