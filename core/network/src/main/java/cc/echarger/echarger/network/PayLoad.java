package cc.echarger.echarger.network;

import cc.echarger.echarger.network.exception.BadRequestException;
import io.reactivex.functions.Function;

public class PayLoad<T> implements Function<BaseResponse<T>, T> {

    @Override
    public T apply(BaseResponse<T> baseResponse) {
        if (!baseResponse.isSuccess()) {
            throw new BadRequestException(baseResponse.getCode(), baseResponse.getMessage());
        }
        return baseResponse.getData();
    }
}
