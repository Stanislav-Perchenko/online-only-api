package cam.alperez.samples.onlineonlyapi.ui.categorylist;

import android.app.Application;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cam.alperez.samples.onlineonlyapi.entity.BookEntity;
import cam.alperez.samples.onlineonlyapi.entity.CategoryEntity;
import cam.alperez.samples.onlineonlyapi.rest.ApplicationRestService;
import cam.alperez.samples.onlineonlyapi.rest.utils.ApiResponse;
import cam.alperez.samples.onlineonlyapi.ui.Navigation;
import cam.alperez.samples.onlineonlyapi.ui.common.ApiListResponseUiBundle;
import cam.alperez.samples.onlineonlyapi.ui.common.ErrorUiMessageMapper;
import cam.alperez.samples.onlineonlyapi.utils.MapMutableLiveData;
import cam.alperez.samples.onlineonlyapi.utils.ReplaceableSourceMediatorLiveData;

public class CategoryListViewModel extends AndroidViewModel {

    public static final String TAG = "CategoryListViewModel";

    private final ReplaceableSourceMediatorLiveData<ApiResponse<List<CategoryEntity>>> categoriesResponseMediator;

    private final MutableLiveData<ApiListResponseUiBundle<CategoryEntity>> categoriesUiState;

    private final ReplaceableSourceMediatorLiveData<ApiResponse<List<BookEntity>>> categoryBooksResponseMediator;

    private MutableLiveData<CategoryBooksUiBundle> categoryBooksUiState;


    private final ErrorUiMessageMapper errorMsgMapper;

    public CategoryListViewModel(Application app) {
        super(app);
        Log.i("CategoryListActivity", "----> ViewModel create!");
        errorMsgMapper = new ErrorUiMessageMapper(app);
        categoriesResponseMediator = new ReplaceableSourceMediatorLiveData<>();
        categoryBooksResponseMediator = new ReplaceableSourceMediatorLiveData<>();

        categoriesUiState = MapMutableLiveData.create(
                categoriesResponseMediator,
                (ApiResponse<List<CategoryEntity>> input) ->
                        (input.isSuccessful() && input.getResponseData() != null)
                                ? ApiListResponseUiBundle.createSuccess(input.getResponseData())
                                : ApiListResponseUiBundle.createError(errorMsgMapper.apply(input))
        );

        categoryBooksUiState = MapMutableLiveData.create(
                categoryBooksResponseMediator,
                (ApiResponse<List<BookEntity>> input) -> {
                    CategoryBooksUiBundle oldUiState = categoryBooksUiState.getValue();
                    final CategoryEntity category = (oldUiState != null)
                            ? oldUiState.categoryToDownload
                            : null;
                    return (input.isSuccessful() && input.getResponseData() != null)
                            ? CategoryBooksUiBundle.createSuccess(category, input.getResponseData())
                            : CategoryBooksUiBundle.createError(category, errorMsgMapper.apply(input));
                }
        );


        categoriesUiState.setValue(ApiListResponseUiBundle.createSuccess(new ArrayList<>()));

        categoryBooksUiState.setValue(CategoryBooksUiBundle.createSuccess(null, null));

        observeCategoriesLiveDataForLogs();
    }

    private void observeCategoriesLiveDataForLogs() {
        categoriesResponseMediator.observeForever(categoriesResponseMediatorObserver);
        categoriesUiState.observeForever(categoriesObserver);
    }

    public LiveData<ApiListResponseUiBundle<CategoryEntity>> getCategoriesUiState() {
        return categoriesUiState;
    }

    public void fetchCategories() {
        Log.i("CategoryListActivity", "----> ViewModel enter fetch");

        ApiListResponseUiBundle<CategoryEntity> currentState = categoriesUiState.getValue();
        if (currentState != null) {
            categoriesUiState.setValue(currentState.withIsLoading(true));
        }

        LiveData<ApiResponse<List<CategoryEntity>>> result = ApplicationRestService
                .INSTANCE.getCategories();

        categoriesResponseMediator.setSource(result);

        Log.i("CategoryListActivity", "<---- ViewModel exit fetch");
    }

    public void clearNeedShowCategoryError() {
        ApiListResponseUiBundle<CategoryEntity> currentState = categoriesUiState.getValue();
        if (currentState != null && currentState.isErrorMessageShow) {
            categoriesUiState.setValue(currentState.withErrorShow(false));
        }
    }

    public MutableLiveData<CategoryBooksUiBundle> getCategoryBooksUiState() {
        return categoryBooksUiState;
    }

    public void fetchBooksForCategory(CategoryEntity category) {

        CategoryBooksUiBundle currentState = categoryBooksUiState.getValue();
        if (currentState != null) {
            categoryBooksUiState.setValue(currentState
                    .withCategoryId(category)
                    .withIsLoading(true)
            );
        }

        LiveData<ApiResponse<List<BookEntity>>> result = ApplicationRestService
                .INSTANCE.getBooksForCategory(category.getBooksLink());

        categoryBooksResponseMediator.setSource(result);

    }

    public void clearNeedShowCategoryBooksError() {
        CategoryBooksUiBundle currentState = categoryBooksUiState.getValue();
        if (currentState != null && currentState.isErrorMessageShow) {
            categoryBooksUiState.setValue(currentState.withErrorShow(false));
        }
    }

    private final Observer<ApiResponse<List<CategoryEntity>>> categoriesResponseMediatorObserver = value -> {
        String text = String.format(Locale.UK,
                "Original API response: isSuccess=%b, httpCode=%d, httpMessage=%s, nItems=%s, exception=%s",
                value.isSuccessful(),
                value.httpCode(),
                value.httpMessage(),
                (value.getResponseData() == null) ? "null (0)" : ""+value.getResponseData().size(),
                (value.getLocalError() == null) ? "null" : (value.getLocalError().getClass().getSimpleName() + " - " + value.getLocalError().getMessage()));
        Log.d(TAG, text);
    };

    private final Observer<ApiListResponseUiBundle<CategoryEntity>> categoriesObserver = value -> {
        if (value.isSuccess) {
            Log.i(TAG, "Successful response: nItems = "+value.data.size());
        } else {
            Log.e(TAG, "API error response: "+value.error);
        }
    };

    @Override
    protected void onCleared() {
        super.onCleared();
        categoriesResponseMediator.removeObserver(categoriesResponseMediatorObserver);
        categoriesUiState.removeObserver(categoriesObserver);
    }

    public void navigateToCategoryBooksScreen(@NonNull CategoryEntity category,
                                              @Nullable List<BookEntity> optBooks) {
        Intent launcher = new Intent(Navigation.ACTION_SCREEN_CATEGORY_BOOKS);
        launcher.putExtra(Navigation.EXTRA_BOOK_CATEGORY, category);
        if (optBooks != null) {
            if (optBooks instanceof ArrayList) {
                launcher.putParcelableArrayListExtra(Navigation.EXTRA_BOOKS,
                        (ArrayList<? extends Parcelable>) optBooks);
            } else {
                ArrayList<Parcelable> extras = new ArrayList<>(optBooks.size());
                extras.addAll(optBooks);
                launcher.putParcelableArrayListExtra(Navigation.EXTRA_BOOKS, extras);
            }
        }
        getApplication().startActivity(launcher);
    }
}
