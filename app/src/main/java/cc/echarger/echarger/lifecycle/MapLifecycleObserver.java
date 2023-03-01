package cc.echarger.echarger.lifecycle;

import android.app.Activity;
import android.location.Location;
import android.os.Looper;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import cc.echarger.echarger.MainActivity;
import cc.echarger.echarger.R;
import cc.echarger.echarger.ui.component.TopNavi;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.*;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.MyLocationStyle;

public class MapLifecycleObserver implements DefaultLifecycleObserver, TencentLocationListener, LocationSource {

    public static final int RC_MAP = 1;
    private MapView mMapView;
    private TencentLocationManager mLocationManager;
    private OnLocationChangedListener locationChangedListener;
    private TencentMap mTencentMap;
    private TencentLocationRequest locationRequest;

    private MainActivity activity;

    private boolean isFirstTry = true;

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        activity = (MainActivity) owner;
        if (owner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
            activity.requirePermissions();
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
    public void onStop(@NonNull LifecycleOwner owner) {
        if (mMapView != null) {
            mMapView.onStop();
        }
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        mLocationManager.removeUpdates(this);
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        mLocationManager = null;
        locationRequest = null;
        locationChangedListener = null;
    }

    public void initMap(Activity context) {
        mMapView = context.findViewById(R.id.tmapView);
        mTencentMap = mMapView.getMap();
        ((TopNavi) context.findViewById(R.id.top_navi)).registerMap(mTencentMap);
        mTencentMap.getUiSettings().setLogoPosition(
                TencentMapOptions.LOGO_POSITION_TOP_LEFT,
                new int[]{600, 50});
        mTencentMap.setBuildingEnable(false);
        // 发起定位请求
        mLocationManager = TencentLocationManager.getInstance(context.getApplicationContext());
        locationRequest = TencentLocationRequest.create();
        locationRequest.setInterval(3000);
        locationRequest.setAllowDirection(true);
        locationRequest.setIndoorLocationMode(true);
        mLocationManager.requestLocationUpdates(locationRequest, this);
        // 设置定位源
        mTencentMap.setLocationSource(this);
        mTencentMap.setMyLocationEnabled(true);
        // 防止地图自动居中
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        mTencentMap.setMyLocationStyle(myLocationStyle);
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        if (i == TencentLocation.ERROR_OK && locationChangedListener != null) {
            Location location = new Location(tencentLocation.getProvider());
            //设置经纬度
            location.setLatitude(tencentLocation.getLatitude());
            location.setLongitude(tencentLocation.getLongitude());
            //设置精度，这个值会被设置为定位点上表示精度的圆形半径
            location.setAccuracy(tencentLocation.getAccuracy());
            //设置定位标的旋转角度，注意 tencentLocation.getBearing() 只有在 gps 时才有可能获取
            location.setBearing(tencentLocation.getBearing());
            //将位置信息返回给地图
            locationChangedListener.onLocationChanged(location);
        }
        // 初次加载将视野居中
        if (isFirstTry) {
            CameraUpdate cameraSigma =
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude()),
                            19,
                            0,
                            0));
            mTencentMap.moveCamera(cameraSigma);
            isFirstTry = false;
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        locationChangedListener = onLocationChangedListener;
        int err = mLocationManager.requestLocationUpdates(locationRequest, this, Looper.myLooper());
        switch (err) {
            case 1:
                Toast.makeText(activity, "设备缺少使用腾讯定位服务需要的基本条件", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(activity, "manifest 中配置的 key 不正确", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(activity, "自动加载libtencentloc.so失败", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void deactivate() {
        mLocationManager.removeUpdates(this);
        mLocationManager = null;
        locationRequest = null;
        locationChangedListener = null;
    }
}
