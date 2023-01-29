package cam.alperez.samples.onlineonlyapi.ui.categorylist;

import java.util.List;

import cam.alperez.samples.onlineonlyapi.entity.BookEntity;
import cam.alperez.samples.onlineonlyapi.entity.CategoryEntity;
import cam.alperez.samples.onlineonlyapi.ui.common.ApiListResponseUiBundle;
import cam.alperez.samples.onlineonlyapi.ui.common.ErrorUiMessage;
import cam.alperez.samples.onlineonlyapi.utils.IntId;

public class CategoryBooksUiBundle extends ApiListResponseUiBundle<BookEntity> {

    public final IntId<CategoryEntity> categoryId;

    public CategoryBooksUiBundle(IntId<CategoryEntity> categoryId,
                                 boolean isLoading,
                                 boolean isSuccess,
                                 List<BookEntity> data,
                                 ErrorUiMessage error,
                                 boolean isErrorMessageShow) {
        super(isLoading, isSuccess, data, error, isErrorMessageShow);
        this.categoryId = categoryId;
    }

    public CategoryBooksUiBundle withIsLoading(boolean newIsLoading) {
        return new CategoryBooksUiBundle(categoryId, newIsLoading, isSuccess, data, error, isErrorMessageShow);
    }

    public CategoryBooksUiBundle withErrorShow(boolean newIsErrShow) {
        return new CategoryBooksUiBundle(categoryId, isLoading, isSuccess, data, error, newIsErrShow);
    }

    public static CategoryBooksUiBundle createSuccess(IntId<CategoryEntity> categoryId, List<BookEntity> data) {
        return new CategoryBooksUiBundle(categoryId,
                false,
                true,
                data,
                null,
                false);
    }

    public static CategoryBooksUiBundle createError(IntId<CategoryEntity> categoryId, ErrorUiMessage err) {
        return new CategoryBooksUiBundle(categoryId,
                false,
                false,
                null,
                err,
                true);
    }


}
