package cam.alperez.samples.onlineonlyapi.rest.utils;

import androidx.annotation.Nullable;

import retrofit2.Response;

public interface ApiResponse<T> {

    static <R> ApiResponse<R> create(Response<R> response) {
        return new RemoteErrorApiResponseImpl<>(
                response.code(),
                response.message(),
                response.body());
    }

    static <R> ApiResponse<R> create(Throwable t) {
        return new LocalErrorApiResponseImpl<>(t);
    }

    boolean isSuccessful();
    @Nullable Throwable getLocalError();
    int httpCode();
    @Nullable String httpMessage();
    @Nullable T getResponseData();
}
