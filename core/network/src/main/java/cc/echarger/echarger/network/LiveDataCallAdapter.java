package cc.echarger.echarger.network;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import cc.echarger.echarger.network.loader.BaseResponse;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

public class LiveDataCallAdapter<T> implements CallAdapter<T, LiveData<T>> {

    private final Type responseType;
    private final boolean isBaseResponse;

    LiveDataCallAdapter(Type responseType, boolean isBaseResponse) {
        this.responseType = responseType;
        this.isBaseResponse = isBaseResponse;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<T> adapt(final Call<T> call) {
        return new MyLiveData<>(call, isBaseResponse);
    }

    private static class MyLiveData<T> extends LiveData<T> {
        private final AtomicBoolean start = new AtomicBoolean(false);
        private final Call<T> call;
        private final boolean isBaseResponse;

        MyLiveData(Call<T> call, boolean isBaseResponse) {
            this.call = call;
            this.isBaseResponse = isBaseResponse;
        }

        @Override
        protected void onActive() {
            super.onActive();
            if (start.compareAndSet(false, true)) {
                call.enqueue(new Callback<T>() {
                    @Override
                    public void onResponse(@Nullable Call<T> call, @Nullable Response<T> response) {
                        T body = response.body();
                        postValue(body);
                    }

                    @Override
                    public void onFailure(@Nullable Call<T> call, @Nullable Throwable t) {
                        if (isBaseResponse) {
                            postValue((T) new BaseResponse<>(500, t.getMessage(), null));
                        } else {
                            postValue(null);
                        }
                    }
                });
            }
        }
    }
}
