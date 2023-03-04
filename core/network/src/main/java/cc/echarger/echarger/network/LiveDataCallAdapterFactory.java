package cc.echarger.echarger.network;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import cc.echarger.echarger.network.loader.BaseResponse;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?, ?> get(@Nullable Type returnType, @Nullable Annotation[] annotations, @Nullable Retrofit retrofit) {
        if (getRawType(returnType) != LiveData.class) {
            return null;
        }
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Type rawType = getRawType(observableType);
        boolean isBaseResponse = rawType == BaseResponse.class;
        if (!(observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }
        return new LiveDataCallAdapter<>(observableType, isBaseResponse);
    }
}
