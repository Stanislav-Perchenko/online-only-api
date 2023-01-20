package cam.alperez.samples.onlineonlyapi.ui.categorylist;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.Locale;

import cam.alperez.samples.onlineonlyapi.entity.CategoryEntity;
import cam.alperez.samples.onlineonlyapi.rest.ApplicationRestService;
import cam.alperez.samples.onlineonlyapi.rest.utils.ApiResponse;
import cam.alperez.samples.onlineonlyapi.ui.common.ApiListResponseUiBundle;
import cam.alperez.samples.onlineonlyapi.ui.common.ErrorUiMessageMapper;
import cam.alperez.samples.onlineonlyapi.utils.ReplaceableSourceMediatorLiveData;

public class CategoryListViewModel extends AndroidViewModel {

    public static final String TAG = "CategoryListViewModel";

    private final ReplaceableSourceMediatorLiveData<ApiResponse<List<CategoryEntity>>> categoriesResponseMediator;

    private final LiveData<ApiListResponseUiBundle<CategoryEntity>> categories;

    private final ErrorUiMessageMapper errorMsgMapper;

    public CategoryListViewModel(Application app) {
        super(app);
        Log.i("CategoryListActivity", "----> ViewModel create!");
        errorMsgMapper = new ErrorUiMessageMapper(app);
        categoriesResponseMediator = new ReplaceableSourceMediatorLiveData<>();

        categories = Transformations.map(
                categoriesResponseMediator,
                (ApiResponse<List<CategoryEntity>> input) ->
                        (input.isSuccessful() && input.getResponseData() != null)
                                ? ApiListResponseUiBundle.createSuccess(input.getResponseData())
                                : ApiListResponseUiBundle.createError(errorMsgMapper.apply(input))
        );

        observeCategoriesLiveDataForLogs();
    }

    private void observeCategoriesLiveDataForLogs() {
        categoriesResponseMediator.observeForever(categoriesResponseMediatorObserver);
        categories.observeForever(categoriesObserver);
    }

    public LiveData<ApiListResponseUiBundle<CategoryEntity>> getCategories() {
        return categories;
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
        categories.removeObserver(categoriesObserver);
    }
}
