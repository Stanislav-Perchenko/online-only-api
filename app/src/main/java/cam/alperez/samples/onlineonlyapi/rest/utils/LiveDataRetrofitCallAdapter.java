package cam.alperez.samples.onlineonlyapi.rest.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataRetrofitCallAdapter<T> implements CallAdapter<T, LiveData<ApiResponse<T>>> {

    private final Type responseType;

    public LiveDataRetrofitCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @NonNull
    @Override
    public Type responseType() {
        return responseType;
    }

    @NonNull
    @Override
    public LiveData<ApiResponse<T>> adapt(Call<T> call) {
        return new LiveData<ApiResponse<T>>() {

            private AtomicBoolean isStarted = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (isStarted.compareAndSet(false, true)) {
                    call.enqueue(new Callback<T>() {
                        @Override
                        public void onResponse(Call<T> c, Response<T> response) {
                            ApiResponse<T> apiResp = ApiResponse.create(response);
                            postValue(apiResp);
                        }

                        @Override
                        public void onFailure(Call<T> c, Throwable t) {
                            ApiResponse<T> apiResp = ApiResponse.create(t);
                            postValue(apiResp);
                        }
                    });
                }
            }
        };
    }
}
