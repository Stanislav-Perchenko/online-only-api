package cam.alperez.samples.onlineonlyapi.ui.categorylist;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.Collections;
import java.util.List;

import cam.alperez.samples.onlineonlyapi.entity.CategoryEntity;
import cam.alperez.samples.onlineonlyapi.rest.ApplicationRestService;
import cam.alperez.samples.onlineonlyapi.rest.utils.ApiResponse;
import cam.alperez.samples.onlineonlyapi.utils.NonNullMediatorLiveData;
import cam.alperez.samples.onlineonlyapi.utils.ReplaceableSourceMediatorLiveData;

public class CategoryListViewModel extends AndroidViewModel {

    private final ReplaceableSourceMediatorLiveData<ApiResponse<List<CategoryEntity>>> categoriesResponseMediator;

    private final LiveData<List<CategoryEntity>> categories;
    private final LiveData<ApiResponse<List<CategoryEntity>>> categoriesError;

    public CategoryListViewModel(Application app) {
        super(app);
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

        fetchCategories();
    }

    public LiveData<List<CategoryEntity>> getCategories() {
        return categories;
    }

    public LiveData<ApiResponse<List<CategoryEntity>>> getCategoriesError() {
        return categoriesError;
    }

    public LiveData<ApiResponse<List<CategoryEntity>>> getCategoriesApiResponse() {
        return categoriesResponseMediator;
    }

    public void fetchCategories() {
        LiveData<ApiResponse<List<CategoryEntity>>> result = ApplicationRestService
                .INSTANCE.getCategories();

        categoriesResponseMediator.setSource(result);
    }

}
