package cc.echarger.echarger.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cc.echarger.echarger.network.dto.MockDto;
import cc.echarger.echarger.network.loader.BaseResponse;
import cc.echarger.echarger.network.loader.MockLoader;

public class MockViewModel extends ViewModel {

    private MutableLiveData<BaseResponse<MockDto>> mock;


    public MutableLiveData<BaseResponse<MockDto>> getMockLiveData() {
        if(mock == null) {
            mock = new MutableLiveData<>();
        }
        return mock;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        // TODO
    }

    public LiveData<BaseResponse<MockDto>> getLiveDataLoginEntity() {
        MockLoader mockLoader = new MockLoader();
        return mockLoader.getMock();
    }
}


