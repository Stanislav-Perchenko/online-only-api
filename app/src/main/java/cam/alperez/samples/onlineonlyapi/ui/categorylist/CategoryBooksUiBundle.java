package cam.alperez.samples.onlineonlyapi.ui.categorylist;

import androidx.annotation.Nullable;

import java.util.List;

import cam.alperez.samples.onlineonlyapi.entity.BookEntity;
import cam.alperez.samples.onlineonlyapi.entity.CategoryEntity;
import cam.alperez.samples.onlineonlyapi.ui.common.ApiListResponseUiBundle;
import cam.alperez.samples.onlineonlyapi.ui.common.ErrorUiMessage;
import cam.alperez.samples.onlineonlyapi.utils.IntId;

public class CategoryBooksUiBundle extends ApiListResponseUiBundle<BookEntity> {

    @Nullable
    public final CategoryEntity categoryToDownload;

    public CategoryBooksUiBundle(@Nullable CategoryEntity categoryToDownload,
                                 boolean isLoading,
                                 boolean isSuccess,
                                 @Nullable List<BookEntity> data,
                                 @Nullable ErrorUiMessage error,
                                 boolean isErrorMessageShow) {
        super(isLoading, isSuccess, data, error, isErrorMessageShow);
        this.categoryToDownload = categoryToDownload;
    }

    public CategoryBooksUiBundle withCategoryId(CategoryEntity categoryToDownload) {
        return new CategoryBooksUiBundle(categoryToDownload, isLoading, isSuccess, data, error, isErrorMessageShow);
    }

    public CategoryBooksUiBundle withIsLoading(boolean newIsLoading) {
        return new CategoryBooksUiBundle(categoryToDownload, newIsLoading, isSuccess, data, error, isErrorMessageShow);
    }

    public CategoryBooksUiBundle withErrorShow(boolean newIsErrShow) {
        return new CategoryBooksUiBundle(categoryToDownload, isLoading, isSuccess, data, error, newIsErrShow);
    }

    public static CategoryBooksUiBundle createSuccess(CategoryEntity categoryToDownload, List<BookEntity> data) {
        return new CategoryBooksUiBundle(categoryToDownload,
                false,
                true,
                data,
                null,
                false);
    }

    public static CategoryBooksUiBundle createError(CategoryEntity categoryToDownload, ErrorUiMessage err) {
        return new CategoryBooksUiBundle(categoryToDownload,
                false,
                false,
                null,
                err,
                true);
    }


}
