package cc.echarger.echarger.network.loader;

import androidx.lifecycle.LiveData;
import cc.echarger.echarger.model.dto.ChargerListDto;
import cc.echarger.echarger.network.RetrofitServiceManager;
import retrofit2.http.GET;

public class AppLoader {
    private final AppService appService;

    public AppLoader() {
        appService = RetrofitServiceManager.getInstance().create(AppService.class);
    }

    public LiveData<BaseResponse<ChargerListDto>> getChargerList() {
        return appService.getChargerList();
    }
    public interface AppService {
        @GET("/api/chargers")
        LiveData<BaseResponse<ChargerListDto>> getChargerList();
    }
}
