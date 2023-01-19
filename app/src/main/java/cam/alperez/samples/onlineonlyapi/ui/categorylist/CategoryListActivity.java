package cam.alperez.samples.onlineonlyapi.ui.categorylist;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import cam.alperez.samples.onlineonlyapi.R;

public class CategoryListActivity extends AppCompatActivity {

    private SwipeRefreshLayout vRefresher;

    private CategoryListViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.screen_title_category_list);

        vRefresher = findViewById(R.id.refresher);

        RecyclerView rv = findViewById(R.id.items_recycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        CategoryListAdapter listAdapter = new CategoryListAdapter(this);
        listAdapter.setOnDataUpdateListener(() -> vRefresher.setRefreshing(false));
        rv.setAdapter(listAdapter);

        viewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);

        viewModel.getCategories().observe(this, listAdapter::setData);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        vRefresher.setOnRefreshListener(viewModel::fetchCategories);
    }
}