package cc.echarger.echarger.network.loader;

import androidx.lifecycle.LiveData;
import cc.echarger.echarger.network.RetrofitServiceManager;
import cc.echarger.echarger.network.dto.MockDto;
import retrofit2.http.GET;


public class MockLoader {
    private final MockService mockService;

    public MockLoader() {
        mockService = RetrofitServiceManager.getInstance().create(MockService.class);
    }


    public LiveData<BaseResponse<MockDto>> getMock() {
        return mockService.getMock();
    }

    public interface MockService {

        @GET("/api/mock")
        LiveData<BaseResponse<MockDto>> getMock();

    }
}
