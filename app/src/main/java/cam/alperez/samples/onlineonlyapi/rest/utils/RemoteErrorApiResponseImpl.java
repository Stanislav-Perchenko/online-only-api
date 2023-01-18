package cam.alperez.samples.onlineonlyapi.rest.utils;

import androidx.annotation.Nullable;

class RemoteErrorApiResponseImpl<T> implements ApiResponse<T> {
    private final int httpResponseCode;
    private final String httpResponseMessage;
    private final T data;

    RemoteErrorApiResponseImpl(int httpResponseCode, String httpResponseMessage, T data) {
        this.httpResponseCode = httpResponseCode;
        this.httpResponseMessage = httpResponseMessage;
        this.data = data;
    }

    @Override
    public boolean isSuccessful() {
        return (httpResponseCode >= 200) && (httpResponseCode < 300);
    }

    @Nullable
    @Override
    public Throwable getLocalError() {
        return null;
    }

    @Override
    public int httpCode() {
        return httpResponseCode;
    }

    @Nullable
    @Override
    public String httpMessage() {
        return httpResponseMessage;
    }

    @Nullable
    @Override
    public T getResponseData() {
        return data;
    }
}
