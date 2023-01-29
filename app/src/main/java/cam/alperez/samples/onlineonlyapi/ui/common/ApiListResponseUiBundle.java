package cam.alperez.samples.onlineonlyapi.ui.common;

import androidx.annotation.Nullable;

import java.util.List;

public class ApiListResponseUiBundle<T> {
    public final boolean isLoading;

    public final boolean isSuccess;

    @Nullable
    public final List<T> data;

    @Nullable
    public final ErrorUiMessage error;

    public final boolean isErrorMessageShow;

    public ApiListResponseUiBundle(boolean isLoading,
                                   boolean isSuccess,
                                   @Nullable List<T> data,
                                   @Nullable ErrorUiMessage error,
                                   boolean isErrorMessageShow)
    {
        this.isLoading = isLoading;
        this.isSuccess = isSuccess;
        this.data = data;
        this.error = error;
        this.isErrorMessageShow = isErrorMessageShow;
    }

    public ApiListResponseUiBundle<T> withIsLoading(boolean newIsLoading) {
        return new ApiListResponseUiBundle<>(newIsLoading, isSuccess, data, error, isErrorMessageShow);
    }

    public ApiListResponseUiBundle<T> withErrorShow(boolean newIsErrShow) {
        return new ApiListResponseUiBundle<>(isLoading, isSuccess, data, error, newIsErrShow);
    }


    public static <S> ApiListResponseUiBundle<S> createSuccess(List<S> data) {
        return new ApiListResponseUiBundle<>(false, true, data, null, false);
    }

    public static <S> ApiListResponseUiBundle<S> createError(ErrorUiMessage err) {
        return new ApiListResponseUiBundle<>(false, false, null, err, true);
    }
}
