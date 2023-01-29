package cam.alperez.samples.onlineonlyapi.ui.categorylist;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import cam.alperez.samples.onlineonlyapi.R;

public class CategoryListActivity extends AppCompatActivity {

    public static final String STATE_PENDING_NAVIGATE_CATEGORY = "pendingNavigateToCategory";

    private SwipeRefreshLayout vRefresher;
    private TextView tvError;

    private CategoryListViewModel viewModel;

    private boolean pendingNavigateToCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.screen_title_category_list);

        vRefresher = findViewById(R.id.refresher);
        tvError = findViewById(R.id.text_error);
        tvError.setVisibility(View.GONE);

        RecyclerView rv = findViewById(R.id.items_recycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        CategoryListAdapter listAdapter = new CategoryListAdapter(this);
        listAdapter.setItemClickListener((position, category) -> {
            if (!pendingNavigateToCategory) {
                pendingNavigateToCategory = true;
                viewModel.fetchBooksForCategory(category);
            }
        });
        rv.setAdapter(listAdapter);

        viewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);

        observeCategories(listAdapter);
        observeCategoryBook(listAdapter);

        if (savedInstanceState == null) {
            viewModel.fetchCategories();
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingNavigateToCategory = savedInstanceState.getBoolean(STATE_PENDING_NAVIGATE_CATEGORY, false);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        vRefresher.setOnRefreshListener(viewModel::fetchCategories);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_PENDING_NAVIGATE_CATEGORY, pendingNavigateToCategory);
    }

    private void observeCategories(CategoryListAdapter adapter) {
        viewModel.getCategoriesUiState().observe(this, uiState -> {

            vRefresher.setRefreshing(uiState.isLoading);

            if (uiState.isSuccess) {
                tvError.setVisibility(View.GONE);
                adapter.setData(uiState.data);
            } else if (uiState.error != null) {
                adapter.clear();
                tvError.setText(uiState.error.detailedDescription);
                tvError.setVisibility(View.VISIBLE);
            }

            if (uiState.isErrorMessageShow) {
                if (uiState.error != null) {
                    Toast.makeText(this, uiState.error.displayText, Toast.LENGTH_SHORT).show();
                }
                vRefresher.post(() -> viewModel.clearNeedShowCategoryError());
            }
        });
    }

    private void observeCategoryBook(final CategoryListAdapter adapter) {
        viewModel.getCategoryBooksUiState().observe(this, uiState -> {

            if (uiState.categoryToDownload != null) {
                adapter.setItemLoadingProgress(uiState.categoryToDownload.getId(), uiState.isLoading);
            }

            if (!uiState.isLoading) {
                if (uiState.isSuccess && (uiState.data != null) && pendingNavigateToCategory) {
                    //TODO Open category details activity
                    Toast.makeText(this, "Open category for: "+uiState.categoryToDownload.getDisplayName(), Toast.LENGTH_SHORT).show();
                }
                pendingNavigateToCategory = false;
            }

            if (uiState.isErrorMessageShow) {
                if (uiState.error != null) {
                    Toast.makeText(this, uiState.error.displayText, Toast.LENGTH_SHORT).show();
                }
                vRefresher.post(() -> viewModel.clearNeedShowCategoryBooksError());
            }
        });
    }

}