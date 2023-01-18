package cam.alperez.samples.onlineonlyapi.rest.utils;

import androidx.annotation.Nullable;

class LocalErrorApiResponseImpl<T> implements ApiResponse<T> {
    private final Throwable error;

    LocalErrorApiResponseImpl(Throwable error) {
        this.error = error;
    }

    @Override
    public boolean isSuccessful() {
        return false;
    }

    @Nullable
    @Override
    public Throwable getLocalError() {
        return error;
    }

    @Override
    public int httpCode() {
        return 0;
    }

    @Nullable
    @Override
    public String httpMessage() {
        return null;
    }

    @Nullable
    @Override
    public T getResponseData() {
        return null;
    }
}
