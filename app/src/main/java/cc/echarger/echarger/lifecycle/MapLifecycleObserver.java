package cc.echarger.echarger.lifecycle;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import cc.echarger.echarger.MainActivity;
import cc.echarger.echarger.R;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;

public class MapLifecycleObserver implements DefaultLifecycleObserver {

    public static final int RC_MAP = 1;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;


    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        if (owner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
            ((MainActivity) owner).requirePermissions();
        }
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        if (mLocationClient != null) {
            mLocationClient.stop();
            mLocationClient = null;
        }
        if (mBaiduMap != null) {
            mBaiduMap.setMyLocationEnabled(false);
            mBaiduMap = null;
        }
        if (mMapView != null) {
            mMapView.onDestroy();
            mMapView = null;
        }
    }

    public void initMap(Activity context) {
        //定位初始化
        try {
            mLocationClient = new LocationClient(context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        mLocationClient.registerLocationListener(new MyLocationListener());
        //获取地图控件引用
        mMapView = context.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //开启地图的定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        //设置locationClientOption
        mLocationClient.setLocOption(option);
        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //initOrientationListener();
        //开启地图定位图层
        mLocationClient.start();
        //myOrientationListener.onStart();
    }

    //构造地图数据
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            MyLocationConfiguration configuration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,
                    true, null);
            mBaiduMap.setMyLocationConfiguration(configuration);
            mBaiduMap.animateMapStatus(update);
            mBaiduMap.setMyLocationData(locData);
        }
    }
}
