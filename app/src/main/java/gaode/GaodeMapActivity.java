package gaode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.zhq.devtools.databinding.ActivityGaodeMapBinding;
import com.zhq.toolslib.toast.ToastUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GaodeMapActivity extends AppCompatActivity {

    private com.zhq.devtools.databinding.ActivityGaodeMapBinding binding;
    private AMap aMap;
    private static final String TAG = "GaodeMapActivity";
    private UiSettings mapUiSettings;
    private int mapScaleLevel = 10;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGaodeMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        binding.mapView.onCreate(savedInstanceState);
        getMap();
        initMapLocationStyle();
        changeMapModelStyle();
        downloadOfflineMap();
        handleOptionCheckBox();
    }

    /**
     * 获取AMap
     */
    private void getMap() {
        if (aMap == null) {
            aMap = binding.mapView.getMap();
        }
    }

    /**
     * 蓝点样式
     * myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
     * myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
     * myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
     * myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
     * myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
     * //以下三种模式从5.1.0版本开始提供
     * myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
     * myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
     * myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
     */
    private void initMapLocationStyle() {
        if (aMap == null) return;
        //当前定位显示样式
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        //连续定位有效
        myLocationStyle.interval(2000);
        ////设置定位蓝点精度圆圈的填充颜色的方法
        myLocationStyle.radiusFillColor(Color.parseColor("#8000ff00"));
        ////设置定位蓝点精度圆圈的边框颜色的方法。
        myLocationStyle.strokeColor(Color.parseColor("#80ff0000"));
        //设置定位蓝点精度圈的边框宽度的方法。
        myLocationStyle.strokeWidth(2f);
        //定位图标样式
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.outHeight = 50;
//        options.outWidth = 50;
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_test_img, options);
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
        myLocationStyle.anchor(0.0f, 1.0f);
        //方法自5.1.0版本后支持
        ////设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，
        // 设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
        myLocationStyle.showMyLocation(true);
        //当前位置监听
        aMap.setOnMyLocationChangeListener(myLocationChangeListener);
        //设置启用定位蓝点显示
        aMap.setMyLocationEnabled(true);
        //设置样式
        aMap.setMyLocationStyle(myLocationStyle);
        mapUiSettings = aMap.getUiSettings();

    }


    private AMap.OnMyLocationChangeListener myLocationChangeListener = new AMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            Log.d(TAG, "===onMyLocationChange: Latitude=" + location.getLatitude() + "==getLongitude=" + location.getLongitude());
        }
    };

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
        binding.mapView.onDestroy();
    }

    /**
     * 切换地图样式
     * public static final int MAP_TYPE_NORMAL = 1;
     * public static final int MAP_TYPE_SATELLITE = 2;
     * public static final int MAP_TYPE_NIGHT = 3;
     * public static final int MAP_TYPE_NAVI = 4;
     * public static final int MAP_TYPE_BUS = 5;
     */
    private int mapModel = AMap.MAP_TYPE_NORMAL;

    private void changeMapModelStyle() {
        binding.btnMapModel.setText("切换为：卫星地图");
        binding.btnMapModel.setOnClickListener(v -> {
            switch (mapModel) {
                case AMap.MAP_TYPE_NORMAL:
                    binding.btnMapModel.setText("切换为：夜景地图");
                    mapModel = AMap.MAP_TYPE_SATELLITE;
                    aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                    break;
                case AMap.MAP_TYPE_SATELLITE:
                    binding.btnMapModel.setText("切换为：导航地图");
                    mapModel = AMap.MAP_TYPE_NIGHT;
                    aMap.setMapType(AMap.MAP_TYPE_NIGHT);
                    break;
                case AMap.MAP_TYPE_NIGHT:
                    binding.btnMapModel.setText("切换为：公交线路地图");
                    mapModel = AMap.MAP_TYPE_BUS;
                    aMap.setMapType(AMap.MAP_TYPE_NAVI);
                    break;
                case AMap.MAP_TYPE_BUS:
                    binding.btnMapModel.setText("切换为：默认地图");
                    mapModel = -1;
                    aMap.setMapType(AMap.MAP_TYPE_BUS);
                    break;
                case -1:
                    binding.btnMapModel.setText("切换为：卫星地图");
                    mapModel = AMap.MAP_TYPE_SATELLITE;
                    aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                    break;
            }
        });
    }

    private void handleOptionCheckBox() {
        binding.cbShowIndoorMap.setOnCheckedChangeListener((buttonView, isChecked) -> {
            aMap.showIndoorMap(isChecked);
        });
        binding.cbShowBuildings.setOnCheckedChangeListener((buttonView, isChecked) -> {
            aMap.showBuildings(isChecked);
        });
        binding.cbTrafficEnabled.setOnCheckedChangeListener((buttonView, isChecked) -> {
            aMap.setTrafficEnabled(isChecked);
        });
        binding.cbLanguage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                aMap.setMapLanguage(AMap.ENGLISH);
            } else {
                aMap.setMapLanguage(AMap.CHINESE);
            }
        });
        binding.cbZoomControlsEnabled.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mapUiSettings.setZoomControlsEnabled(isChecked);
        });
        binding.cbCompassEnabled.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mapUiSettings.setCompassEnabled(isChecked);
        });
        binding.cbMyLocationButtonEnabled.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mapUiSettings.setMyLocationButtonEnabled(isChecked);
        });
        binding.cbScaleControlsEnabled.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mapUiSettings.setScaleControlsEnabled(isChecked);
        });
        binding.cbAllGesture.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mapUiSettings.setAllGesturesEnabled(isChecked);
        });
        binding.cbScaleGesture.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mapUiSettings.setZoomGesturesEnabled(isChecked);
        });
        binding.cbScrollGesture.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mapUiSettings.setScrollGesturesEnabled(isChecked);
        });
        binding.cbRotateGesture.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mapUiSettings.setRotateGesturesEnabled(isChecked);
        });
        binding.cbTiltGesture.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mapUiSettings.setTiltGesturesEnabled(isChecked);
        });
        binding.btnChangeMapCenter.setOnClickListener(v -> {
            //参数依次是：视角调整区域的中心点坐标、希望调整到的缩放级别、俯仰角0°~45°（垂直与地图时为0）、偏航角 0~360° (正北方为0)
            LatLng latLng = new LatLng(39.977290, 116.337000);
            //
            CameraPosition cameraPosition = new CameraPosition(latLng, 18, 30, 0);
            CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            aMap.animateCamera(mCameraUpdate);
        });
        binding.btnScaleMapUp.setOnClickListener(v -> {
            mapScaleLevel++;
            if (mapScaleLevel >= 19) mapScaleLevel = 19;
            binding.btnScaleMapUp.setText("缩放加：" + mapScaleLevel);
            binding.btnScaleMapDown.setText("缩放减：" + mapScaleLevel);
            CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(mapScaleLevel);
            aMap.animateCamera(cameraUpdate);
        });
        binding.btnScaleMapDown.setOnClickListener(v -> {
            mapScaleLevel--;
            if (mapScaleLevel <= 3) mapScaleLevel = 3;
            binding.btnScaleMapUp.setText("缩放加：" + mapScaleLevel);
            binding.btnScaleMapDown.setText("缩放减：" + mapScaleLevel);
            CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(mapScaleLevel);
            aMap.animateCamera(cameraUpdate);
        });

        binding.btnMapScreenShot.setOnClickListener(v -> {
            aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
                @Override
                public void onMapScreenShot(Bitmap bitmap) {

                }

                @Override
                public void onMapScreenShot(Bitmap bitmap, int status) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    if (null == bitmap) {
                        return;
                    }
                    try {
                        Log.d(TAG, "===onMapScreenShot前: "+bitmap.getByteCount());
                        FileOutputStream fos = new FileOutputStream(
                                getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/test_"
                                        + sdf.format(new Date()) + ".png");
                        boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        try {
                            fos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        StringBuffer buffer = new StringBuffer();
                        if (b)
                            buffer.append("截屏成功 ");
                        else {
                            buffer.append("截屏失败 ");
                        }
                        if (status != 0)
                            buffer.append("地图渲染完成，截屏无网格");
                        else {
                            buffer.append("地图未渲染完成，截屏有网格");
                        }
                        ToastUtils.getInstance().showShortToast(GaodeMapActivity.this, buffer.toString());

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            });
        });
    }

    private void downloadOfflineMap() {
        try {
            OfflineMapManager offlineMapManager = new OfflineMapManager(this, new OfflineMapManager.OfflineMapDownloadListener() {
                @Override
                public void onDownload(int i, int i1, String s) {

                }

                @Override
                public void onCheckUpdate(boolean b, String s) {

                }

                @Override
                public void onRemove(boolean b, String s, String s1) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.btnOfflineMap.setOnClickListener(v -> {
            startActivity(new Intent(this,
                    com.amap.api.maps.offlinemap.OfflineMapActivity.class));
        });
    }

}