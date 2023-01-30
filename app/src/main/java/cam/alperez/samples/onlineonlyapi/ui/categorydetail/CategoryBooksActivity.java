package cam.alperez.samples.onlineonlyapi.ui.categorydetail;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import cam.alperez.samples.onlineonlyapi.R;
import cam.alperez.samples.onlineonlyapi.entity.BookEntity;
import cam.alperez.samples.onlineonlyapi.entity.CategoryEntity;
import cam.alperez.samples.onlineonlyapi.ui.Navigation;

public class CategoryBooksActivity extends AppCompatActivity {

    private SwipeRefreshLayout vRefresher;
    private TextView tvError;

    private CategoryBooksViewModel viewModel;

    CategoryBooksAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_with_recycler);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.screen_title_books_for_category);

        vRefresher = findViewById(R.id.refresher);
        tvError = findViewById(R.id.text_error);
        tvError.setVisibility(View.GONE);

        if (savedInstanceState == null) {
            CategoryEntity category = getIntent().getParcelableExtra(Navigation.EXTRA_BOOK_CATEGORY);
            viewModel = new ViewModelProvider(this, new CategoryBooksViewModel.Factory(category))
                        .get(CategoryBooksViewModel.class);
        } else {
            viewModel = ViewModelProviders.of(this).get(CategoryBooksViewModel.class);
        }

        RecyclerView rv = findViewById(R.id.items_recycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new CategoryBooksAdapter(this, viewModel.getCategory());
        rv.setAdapter(listAdapter);

        observeUiState();

        // Initial fetching data
        if (savedInstanceState == null) {
            List<BookEntity> argBooks = getIntent().getParcelableArrayListExtra(Navigation.EXTRA_BOOKS);
            if ((argBooks != null) && !argBooks.isEmpty()) {
                viewModel.setBooks(argBooks);
            } else {
                viewModel.fetchBooks();
            }
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        vRefresher.setOnRefreshListener(viewModel::fetchBooks);
    }

    private void observeUiState() {
        viewModel.getBooksUiState().observe(this, uiState -> {

            vRefresher.setRefreshing(uiState.isLoading);

            if (uiState.isSuccess) {
                tvError.setVisibility(View.GONE);
                listAdapter.setData(uiState.data);
            } else if (uiState.error != null) {
                listAdapter.clear();
                tvError.setText(uiState.error.detailedDescription);
                tvError.setVisibility(View.VISIBLE);
            }

            if (uiState.isErrorMessageShow) {
                if (uiState.error != null) {
                    Toast.makeText(this, uiState.error.displayText, Toast.LENGTH_SHORT).show();
                }
                vRefresher.post(() -> viewModel.clearNeedShowBookError());
            }
        });
    }

}
