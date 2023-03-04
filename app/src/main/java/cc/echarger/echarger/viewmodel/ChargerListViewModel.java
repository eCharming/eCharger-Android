package cc.echarger.echarger.viewmodel;

import android.app.Activity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cc.echarger.echarger.model.dto.ChargerListDto;
import cc.echarger.echarger.model.vo.ChargerVo;
import cc.echarger.echarger.network.loader.AppLoader;
import cc.echarger.echarger.network.loader.BaseResponse;

import java.util.List;

public class ChargerListViewModel extends ViewModel {

    private MutableLiveData<List<ChargerVo>> chargers;


    public MutableLiveData<List<ChargerVo>> getMutableChargers() {
        if(chargers == null) {
            chargers = new MutableLiveData<>();
        }
        return chargers;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // TODO
    }

    public LiveData<BaseResponse<ChargerListDto>> getChargers(LifecycleOwner owner) {
        AppLoader appLoader = new AppLoader();
        LiveData<BaseResponse<ChargerListDto>> chargerList = appLoader.getChargerList();
        chargerList.observe(owner, chargerListDto ->{
            chargers.postValue(chargerListDto.getData().chargers);
        });
        return null;
    }
}
