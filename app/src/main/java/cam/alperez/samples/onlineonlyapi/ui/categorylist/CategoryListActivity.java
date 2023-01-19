package cam.alperez.samples.onlineonlyapi.ui.categorylist;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;
import java.util.Locale;

import cam.alperez.samples.onlineonlyapi.R;
import cam.alperez.samples.onlineonlyapi.entity.CategoryEntity;
import cam.alperez.samples.onlineonlyapi.rest.utils.ApiResponse;

public class CategoryListActivity extends AppCompatActivity {

    public static final String TAG = "CategoryListActivity";

    private CategoryListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_refresh).setOnClickListener(v -> viewModel.fetchCategories());

        viewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);

        viewModel.getCategoriesApiResponse().observe(this, (ApiResponse<List<CategoryEntity>> value) -> {
            String text = String.format(Locale.UK,
                    "Original API response: isSuccess=%b, httpCode=%d, httpMessage=%s, nItems=%s",
                    value.isSuccessful(),
                    value.httpCode(),
                    value.httpMessage(),
                    (value.getResponseData() == null) ? "null (0)" : ""+value.getResponseData().size());
            Log.d(TAG, text);
        });

        viewModel.getCategories().observe(this, (List<CategoryEntity> value) -> {
            Log.i(TAG, "Successful response: nItems = "+value.size());
        });

        viewModel.getCategoriesError().observe(this, (ApiResponse<List<CategoryEntity>> value) -> {
            String text = String.format(Locale.UK,
                    "API error response: isSuccess=%b, exception=%s, httpCode=%d, httpMessage=%s, nItems=%s",
                    value.isSuccessful(),
                    (value.getLocalError() == null) ? "null" : (value.getLocalError().getClass().getSimpleName() + " - " + value.getLocalError().getMessage()),
                    value.httpCode(),
                    value.httpMessage(),
                    (value.getResponseData() == null) ? "null (0)" : ""+value.getResponseData().size());
            Log.e(TAG, text);
        });
    }
}