package cc.echarger.echarger.network.loader;

import cc.echarger.echarger.network.BaseResponse;
import cc.echarger.echarger.network.RetrofitServiceManager;
import cc.echarger.echarger.network.dto.MockDto;
import io.reactivex.Observable;
import cc.echarger.echarger.network.PayLoad;
import retrofit2.http.GET;

public class MockLoader extends ObjectLoader {
    private final MockService mockService;

    public MockLoader() {
        mockService = RetrofitServiceManager.getInstance().create(MockService.class);
    }

    public Observable<MockDto> getMock() {
        return observe(mockService.getMock()).map(new PayLoad<>());
    }

    public interface MockService {
        @GET("/api/mock")
        Observable<BaseResponse<MockDto>> getMock();

    }
}
