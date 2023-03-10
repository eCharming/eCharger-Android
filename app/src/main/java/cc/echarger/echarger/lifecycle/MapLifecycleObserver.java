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
        // ??????????????????
        mLocationManager = TencentLocationManager.getInstance(context.getApplicationContext());
        locationRequest = TencentLocationRequest.create();
        locationRequest.setInterval(3000);
        locationRequest.setAllowDirection(true);
        locationRequest.setIndoorLocationMode(true);
        mLocationManager.requestLocationUpdates(locationRequest, this);
        // ???????????????
        mTencentMap.setLocationSource(this);
        mTencentMap.setMyLocationEnabled(true);
        // ????????????????????????
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        mTencentMap.setMyLocationStyle(myLocationStyle);
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        if (i == TencentLocation.ERROR_OK && locationChangedListener != null) {
            Location location = new Location(tencentLocation.getProvider());
            //???????????????
            location.setLatitude(tencentLocation.getLatitude());
            location.setLongitude(tencentLocation.getLongitude());
            //??????????????????????????????????????????????????????????????????????????????
            location.setAccuracy(tencentLocation.getAccuracy());
            //??????????????????????????????????????? tencentLocation.getBearing() ????????? gps ?????????????????????
            location.setBearing(tencentLocation.getBearing());
            //??????????????????????????????
            locationChangedListener.onLocationChanged(location);
        }
        // ???????????????????????????
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
                Toast.makeText(activity, "?????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(activity, "manifest ???????????? key ?????????", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(activity, "????????????libtencentloc.so??????", Toast.LENGTH_SHORT).show();
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
