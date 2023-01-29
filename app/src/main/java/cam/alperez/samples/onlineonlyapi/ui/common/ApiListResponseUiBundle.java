package cam.alperez.samples.onlineonlyapi.ui.common;

import java.util.List;

public class ApiListResponseUiBundle<T> {
    public final boolean isLoading;

    public final boolean isSuccess;
    public final List<T> data;
    public final ErrorUiMessage error;
    public final boolean isErrorMessageShow;

    public ApiListResponseUiBundle(boolean isLoading, boolean isSuccess, List<T> data, ErrorUiMessage error, boolean isErrorMessageShow) {
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
