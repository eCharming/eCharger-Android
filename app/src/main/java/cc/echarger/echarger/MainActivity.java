package cc.echarger.echarger;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import cc.echarger.echarger.databinding.ActivityMainBinding;
import cc.echarger.echarger.lifecycle.MapLifecycleObserver;
import cc.echarger.echarger.model.vo.ChargerVo;
import cc.echarger.echarger.network.dto.MockDto;
import cc.echarger.echarger.network.loader.BaseResponse;
import cc.echarger.echarger.ui.databinding.ChargerBinding;
import cc.echarger.echarger.viewmodel.ChargerListViewModel;
import cc.echarger.echarger.viewmodel.MockViewModel;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import java.util.List;

import static cc.echarger.echarger.lifecycle.MapLifecycleObserver.RC_MAP;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private ChargerBinding chargerBinding;
    private ChargerListViewModel chargerListViewModel;

    private final MapLifecycleObserver mapLifecycleObserver = new MapLifecycleObserver();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        StatusBar statusBar = new StatusBar(MainActivity.this);
        statusBar.setColor(R.color.transparent);
        setContentView(activityMainBinding.getRoot());
        getLifecycle().addObserver(mapLifecycleObserver);

        List<ChargerVo> chargerVoList = activityMainBinding.mainBox.getChargerVoList();

        chargerListViewModel = new ViewModelProvider(this).get(ChargerListViewModel.class);
        MockViewModel mockViewModel = new ViewModelProvider(this).get(MockViewModel.class);
        LiveData<BaseResponse<MockDto>> liveDataLoginEntity = mockViewModel.getLiveDataLoginEntity();
        liveDataLoginEntity.observe(this, mockDto -> {
            Toast.makeText(this, mockDto.getData().name, Toast.LENGTH_SHORT).show();
        });
        chargerListViewModel.getMutableChargers().observe(this, new Observer<List<ChargerVo>>() {
            @Override
            public void onChanged(List<ChargerVo> chargerVos) {
                chargerVoList.clear();
                chargerVoList.addAll(chargerVos);
                activityMainBinding.mainBox.getAdapter().notifyDataSetChanged();
            }
        });
        chargerListViewModel.getChargers(this);
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
