package cam.alperez.samples.onlineonlyapi.rest.model;

import androidx.annotation.Nullable;

import retrofit2.Response;

public interface ApiResponse<T> {

    public static <R> ApiResponse<R> create(Response<R> response) {
        //TODO Implement this
        throw new RuntimeException("Not implemented yet");
    }

    public static <R> ApiResponse<R> create(Throwable t) {
        //TODO Implement this
        throw new RuntimeException("Not implemented yet");
    }

    boolean isSuccessful();
    @Nullable Throwable getLocalError();
    int httpCode();
    @Nullable String httpMessage();
    @Nullable T getResponseData();
}
