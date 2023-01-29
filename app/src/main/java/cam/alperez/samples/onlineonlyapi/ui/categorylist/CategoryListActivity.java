package cam.alperez.samples.onlineonlyapi.ui.categorylist;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import cam.alperez.samples.onlineonlyapi.R;

public class CategoryListActivity extends AppCompatActivity {

    private SwipeRefreshLayout vRefresher;
    private TextView tvError;

    private CategoryListViewModel viewModel;


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
            viewModel.fetchBooksForCategory(category);
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
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        vRefresher.setOnRefreshListener(viewModel::fetchCategories);
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

    private void observeCategoryBook(CategoryListAdapter adapter) {
        viewModel.getCategoryBooksUiState().observe(this, uiState -> {
            //TODO Populate UI with current state
        });
    }



}