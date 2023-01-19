package cam.alperez.samples.onlineonlyapi.ui.categorylist;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import cam.alperez.samples.onlineonlyapi.entity.CategoryEntity;
import cam.alperez.samples.onlineonlyapi.rest.ApplicationRestService;
import cam.alperez.samples.onlineonlyapi.rest.utils.ApiResponse;
import cam.alperez.samples.onlineonlyapi.utils.NonNullMediatorLiveData;
import cam.alperez.samples.onlineonlyapi.utils.ReplaceableSourceMediatorLiveData;

public class CategoryListViewModel extends AndroidViewModel {

    public static final String TAG = "CategoryListViewModel";

    private final ReplaceableSourceMediatorLiveData<ApiResponse<List<CategoryEntity>>> categoriesResponseMediator;

    private final LiveData<List<CategoryEntity>> categories;
    private final LiveData<ApiResponse<List<CategoryEntity>>> categoriesError;

    public CategoryListViewModel(Application app) {
        super(app);
        Log.i("CategoryListActivity", "----> ViewModel create!");
        categoriesResponseMediator = new ReplaceableSourceMediatorLiveData<>();

        categories = Transformations.map(
                categoriesResponseMediator,
                (ApiResponse<List<CategoryEntity>> input) ->
                        (input.isSuccessful() && input.getResponseData() != null)
                                ? input.getResponseData()
                                : Collections.emptyList()
        );

        categoriesError = new NonNullMediatorLiveData<>(Transformations.map(
                categoriesResponseMediator,
                (ApiResponse<List<CategoryEntity>> input) -> input.isSuccessful() ? null : input
        ));

        observeCategoriesLiveDataForLogs();

        fetchCategories();
    }

    private void observeCategoriesLiveDataForLogs() {
        categoriesResponseMediator.observeForever(categoriesResponseMediatorObserver);
        categories.observeForever(categoriesObserver);
        categoriesError.observeForever(categoriesErrorObserver);
    }

    public LiveData<List<CategoryEntity>> getCategories() {
        return categories;
    }

    public LiveData<ApiResponse<List<CategoryEntity>>> getCategoriesError() {
        return categoriesError;
    }

    public void fetchCategories() {
        Log.i("CategoryListActivity", "----> ViewModel enter fetch");
        LiveData<ApiResponse<List<CategoryEntity>>> result = ApplicationRestService
                .INSTANCE.getCategories();

        categoriesResponseMediator.setSource(result);
        Log.i("CategoryListActivity", "<---- ViewModel exit fetch");
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

    private final Observer<List<CategoryEntity>> categoriesObserver = value -> {
        Log.i(TAG, "Successful response: nItems = "+value.size());
    };

    private final Observer<ApiResponse<List<CategoryEntity>>> categoriesErrorObserver = value -> {
        String text = String.format(Locale.UK,
                "API error response: isSuccess=%b, exception=%s, httpCode=%d, httpMessage=%s, nItems=%s",
                value.isSuccessful(),
                (value.getLocalError() == null) ? "null" : (value.getLocalError().getClass().getSimpleName() + " - " + value.getLocalError().getMessage()),
                value.httpCode(),
                value.httpMessage(),
                (value.getResponseData() == null) ? "null (0)" : ""+value.getResponseData().size());
        Log.e(TAG, text);
    };


    @Override
    protected void onCleared() {
        super.onCleared();
        categoriesResponseMediator.removeObserver(categoriesResponseMediatorObserver);
        categories.removeObserver(categoriesObserver);
        categoriesError.removeObserver(categoriesErrorObserver);
    }
}
