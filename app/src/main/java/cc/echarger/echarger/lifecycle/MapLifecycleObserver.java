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
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;

public class MapLifecycleObserver implements DefaultLifecycleObserver, TencentLocationListener, LocationSource {

    public static final int RC_MAP = 1;
    private MapView mMapView;
    private TencentLocationManager mLocationManager;
    private OnLocationChangedListener locationChangedListener;
    private TencentLocationRequest locationRequest;

    private MainActivity activity;

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
        TencentMap mTencentMap = mMapView.getMap();
        mTencentMap.setBuildingEnable(false);
        mLocationManager = TencentLocationManager.getInstance(context.getApplicationContext());
        locationRequest = TencentLocationRequest.create();
        locationRequest.setInterval(3000);
        locationRequest.setAllowDirection(true);
        locationRequest.setIndoorLocationMode(true);

        mLocationManager.requestLocationUpdates(locationRequest, this);
        //地图上设置定位数据源
        mTencentMap.setLocationSource(this);
        //设置当前位置可见
        mTencentMap.setMyLocationEnabled(true);
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
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        locationChangedListener = onLocationChangedListener;
        int err = mLocationManager.requestLocationUpdates(
                locationRequest, this, Looper.myLooper());
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
