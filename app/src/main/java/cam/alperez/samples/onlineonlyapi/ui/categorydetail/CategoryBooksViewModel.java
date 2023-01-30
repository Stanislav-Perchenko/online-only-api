package cam.alperez.samples.onlineonlyapi.ui.categorydetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cam.alperez.samples.onlineonlyapi.entity.BookEntity;
import cam.alperez.samples.onlineonlyapi.entity.CategoryEntity;
import cam.alperez.samples.onlineonlyapi.rest.ApplicationRestService;
import cam.alperez.samples.onlineonlyapi.rest.utils.ApiResponse;
import cam.alperez.samples.onlineonlyapi.ui.common.ApiListResponseUiBundle;
import cam.alperez.samples.onlineonlyapi.ui.common.ErrorUiMessageMapper;
import cam.alperez.samples.onlineonlyapi.utils.MapMutableLiveData;
import cam.alperez.samples.onlineonlyapi.utils.ReplaceableSourceMediatorLiveData;

public class CategoryBooksViewModel  extends AndroidViewModel {
    private final CategoryEntity category;

    private final ErrorUiMessageMapper errorMsgMapper;

    private final ReplaceableSourceMediatorLiveData<ApiResponse<List<BookEntity>>> booksResponseMediator;

    private final MutableLiveData<ApiListResponseUiBundle<BookEntity>> booksUiState;

    public CategoryBooksViewModel(@NonNull Application app, @NonNull CategoryEntity category, @NonNull ErrorUiMessageMapper errorMsgMapper) {
        super(app);
        this.category = category;
        this.errorMsgMapper = errorMsgMapper;

        booksResponseMediator = new ReplaceableSourceMediatorLiveData<>();

        booksUiState = MapMutableLiveData.create(
                booksResponseMediator,
                (ApiResponse<List<BookEntity>> input) ->
                        (input.isSuccessful() && input.getResponseData() != null)
                                ? ApiListResponseUiBundle.createSuccess(input.getResponseData())
                                : ApiListResponseUiBundle.createError(errorMsgMapper.apply(input))
        );

        booksUiState.setValue(ApiListResponseUiBundle.createSuccess(new ArrayList<>()));
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setBooks(Collection<BookEntity> books) {
        List<BookEntity> items = new ArrayList<>(books.size());
        items.addAll(books);
        booksUiState.setValue(ApiListResponseUiBundle.createSuccess(items));
    }

    public void fetchBooks() {
        ApiListResponseUiBundle<BookEntity> currentState = booksUiState.getValue();
        if (currentState != null) {
            booksUiState.setValue(currentState.withIsLoading(true));
        }

        LiveData<ApiResponse<List<BookEntity>>> result = ApplicationRestService
                .INSTANCE.getBooksForCategory(category.getBooksLink());

        booksResponseMediator.setSource(result);
    }

    public LiveData<ApiListResponseUiBundle<BookEntity>> getBooksUiState() {
        return booksUiState;
    }

    public void clearNeedShowBookError() {
        ApiListResponseUiBundle<BookEntity> currentState = booksUiState.getValue();
        if (currentState != null && currentState.isErrorMessageShow) {
            booksUiState.setValue(currentState.withErrorShow(false));
        }
    }


    /****************************  Factory for this ViewModel  ************************************/
    public static class Factory implements ViewModelProvider.Factory {

        private final CategoryEntity category;

        public Factory(CategoryEntity category) {
            this.category = category;
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
            final Application app = extras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
            if (app == null) {
                throw new RuntimeException("Application is null when ViewModel creation");
            }
            return (T) new CategoryBooksViewModel(app, category, new ErrorUiMessageMapper(app));
        }
    }
}
