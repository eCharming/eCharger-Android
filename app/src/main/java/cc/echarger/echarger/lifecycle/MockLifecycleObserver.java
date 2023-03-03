package cc.echarger.echarger.lifecycle;

import android.annotation.SuppressLint;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import cc.echarger.echarger.MainActivity;
import cc.echarger.echarger.network.exception.BadRequestException;
import cc.echarger.echarger.network.loader.MockLoader;

public class MockLifecycleObserver implements DefaultLifecycleObserver {

    @SuppressLint("CheckResult")
    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        MockLoader mockLoader = new MockLoader();
        mockLoader.getMock().subscribe(mockDto -> {
            Toast.makeText(((MainActivity) owner), mockDto.name, Toast.LENGTH_SHORT).show();
        }, throwable -> {
            throwable.printStackTrace();
        });
    }
}
