package cc.echarger.echarger;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import cc.echarger.echarger.databinding.ActivityMainBinding;
import cc.echarger.echarger.ui.component.MovableLinearLayout;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private LocationClient mLocationClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
        } else {
            try {
                initSDK(true);
                binding = ActivityMainBinding.inflate(getLayoutInflater());

                StatusBar statusBar = new StatusBar(MainActivity.this);
                //设置颜色为透明
                statusBar.setColor(R.color.transparent);
                setContentView(binding.getRoot());
                MovableLinearLayout moveBox = findViewById(R.id.include);
                moveBox.setTopNavi(findViewById(R.id.top_navi));
                initMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "没有ACCESS_FINE_LOCATION权限！", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            case 2:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "没有ACCESS_COARSE_LOCATION权限！", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            case 3:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "没有WRITE_EXTERNAL_STORAGE权限！", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
        try {
            initSDK(true);
            binding = ActivityMainBinding.inflate(getLayoutInflater());

            StatusBar statusBar = new StatusBar(MainActivity.this);
            //设置颜色为透明
            statusBar.setColor(R.color.transparent);
            setContentView(binding.getRoot());
            MovableLinearLayout moveBox = findViewById(R.id.include);
            moveBox.setTopNavi(findViewById(R.id.top_navi));
            initMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initSDK(boolean status) {
        LocationClient.setAgreePrivacy(status);
        SDKInitializer.setAgreePrivacy(getApplicationContext(), status);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(getApplicationContext());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    private void initMap() throws Exception {
        //定位初始化
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(new MyLocationListener());
        //获取地图控件引用
        mMapView = findViewById(R.id.bmapView);
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

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView = null;
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
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
