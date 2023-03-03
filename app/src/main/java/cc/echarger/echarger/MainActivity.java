package cc.echarger.echarger;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import cc.echarger.echarger.databinding.ActivityMainBinding;
import cc.echarger.echarger.lifecycle.MapLifecycleObserver;
import cc.echarger.echarger.lifecycle.MockLifecycleObserver;
import cc.echarger.echarger.ui.component.MovableLinearLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static cc.echarger.echarger.lifecycle.MapLifecycleObserver.RC_MAP;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private final MapLifecycleObserver mapLifecycleObserver = new MapLifecycleObserver();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        StatusBar statusBar = new StatusBar(MainActivity.this);
        statusBar.setColor(R.color.transparent);
        setContentView(binding.getRoot());
        getLifecycle().addObserver(mapLifecycleObserver);
        // Only For Test
        getLifecycle().addObserver(new MockLifecycleObserver());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_MAP)
    public void requirePermissions() {
        String[] perms = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (EasyPermissions.hasPermissions(this, perms)) {
            mapLifecycleObserver.initMap(this);
        } else {
            EasyPermissions.requestPermissions(this, "请求定位和存储权限", RC_MAP, perms);
        }
    }

}
