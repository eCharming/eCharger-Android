package cc.echarger.echarger.network.loader;

import androidx.lifecycle.LiveData;
import cc.echarger.echarger.network.RetrofitServiceManager;
import cc.echarger.echarger.network.dto.MockDto;
import retrofit2.http.GET;


public class MockLoader extends ObjectLoader {
    private final MockService mockService;

    public MockLoader() {
        mockService = RetrofitServiceManager.getInstance().create(MockService.class);
    }

//    public Observable<MockDto> getMock() {
//        return observe(mockService.getMock()).map(new PayLoad<>());
//    }

    public LiveData<BaseResponse<MockDto>> getMock() {
        return mockService.getMock();
    }

    public interface MockService {
//        @GET("/api/mock")
//        Observable<BaseResponse<MockDto>> getMock();

        @GET("/api/mock")
        LiveData<BaseResponse<MockDto>> getMock();

    }
}
