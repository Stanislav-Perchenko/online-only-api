package cam.alperez.samples.onlineonlyapi.ui.categorydetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;

import cam.alperez.samples.onlineonlyapi.entity.BookEntity;
import cam.alperez.samples.onlineonlyapi.entity.CategoryEntity;

public class CategoryBooksAdapter extends RecyclerView.Adapter<CategoryBooksAdapter.ViewHolder> {

    private final LayoutInflater inflater;

    private final CategoryEntity category;

    public CategoryBooksAdapter(Context ctx, CategoryEntity category) {
        inflater = LayoutInflater.from(ctx);
        this.category = category;
    }

    public void setData(Collection<BookEntity> books) {
        //TODO Implement this
        // Check each item for Category ID. If not related - ignore one.
    }

    @Override
    public int getItemCount() {
        //TODO Implement this
        return 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //TODO Implement this
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //TODO Implement this
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //TODO Implement this
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
