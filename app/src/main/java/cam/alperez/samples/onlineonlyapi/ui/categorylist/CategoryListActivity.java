package cam.alperez.samples.onlineonlyapi.ui.categorylist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import cam.alperez.samples.onlineonlyapi.R;

public class CategoryListActivity extends AppCompatActivity {

    private CategoryListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_refresh).setOnClickListener(v -> viewModel.fetchCategories());

        viewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);

    }
}