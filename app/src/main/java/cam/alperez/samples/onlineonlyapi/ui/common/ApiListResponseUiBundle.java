package cam.alperez.samples.onlineonlyapi.ui.common;

import java.util.List;

public class ApiListResponseUiBundle<T> {
    public final boolean isSuccess;
    public final List<T> data;
    public final ErrorUiMessage error;


    private ApiListResponseUiBundle(boolean isSuccess, List<T> data, ErrorUiMessage error) {
        this.isSuccess = isSuccess;
        this.data = data;
        this.error = error;
    }

    public static <S> ApiListResponseUiBundle<S> createSuccess(List<S> data) {
        return new ApiListResponseUiBundle<>(true, data, null);
    }

    public static <S> ApiListResponseUiBundle<S> createError(ErrorUiMessage err) {
        return new ApiListResponseUiBundle<>(false, null, err);
    }
}
