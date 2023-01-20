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
        rv.setAdapter(listAdapter);

        viewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);

        observeCategories(listAdapter);
    }

    private void observeCategories(CategoryListAdapter adapter) {
        viewModel.getCategories().observe(this, bundle -> {
            vRefresher.setRefreshing(false);

            if (bundle.isSuccess) {
                tvError.setVisibility(View.GONE);
                adapter.setData(bundle.data);
            } else {
                adapter.clear();
                tvError.setText(bundle.error.detailedDescription);
                tvError.setVisibility(View.VISIBLE);
                Toast.makeText(this, bundle.error.displayText, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        vRefresher.setOnRefreshListener(viewModel::fetchCategories);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.fetchCategories();
    }
}