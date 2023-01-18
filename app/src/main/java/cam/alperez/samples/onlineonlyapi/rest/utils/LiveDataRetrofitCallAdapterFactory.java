package cam.alperez.samples.onlineonlyapi.rest.utils;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataRetrofitCallAdapterFactory extends CallAdapter.Factory {

    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != LiveData.class) {
            return null;
        }

        final Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        if (!(observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }

        Class<?> rawObservableType = getRawType(observableType);
        if (!(rawObservableType == ApiResponse.class)) {
            throw new IllegalArgumentException("type must be a resource");
        }

        Type bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);
        return new LiveDataRetrofitCallAdapter<>(bodyType);
    }
}
