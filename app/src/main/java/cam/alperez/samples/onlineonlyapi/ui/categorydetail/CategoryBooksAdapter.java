package cam.alperez.samples.onlineonlyapi.ui.categorydetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cam.alperez.samples.onlineonlyapi.entity.BookEntity;
import cam.alperez.samples.onlineonlyapi.entity.CategoryEntity;

public class CategoryBooksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_CATEGORY = 1;
    public static final int ITEM_TYPE_BOOK = 2;

    private final LayoutInflater inflater;

    private final CategoryEntity category;
    private final List<BookEntity> books = new ArrayList<>();

    public CategoryBooksAdapter(Context ctx, CategoryEntity category) {
        inflater = LayoutInflater.from(ctx);
        this.category = category;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(Collection<BookEntity> books) {
        if (!this.books.isEmpty()) {
            this.books.clear();
        }
        this.books.addAll(books);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clear() {
        if (!books.isEmpty()) {
            books.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return 1 + books.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? ITEM_TYPE_CATEGORY : ITEM_TYPE_BOOK;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_CATEGORY:
                return null;
            case ITEM_TYPE_BOOK:
                return null;
        }
        //TODO Implement this
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int itemType = getItemViewType(position);
        switch (itemType) {
            case ITEM_TYPE_CATEGORY:
                ((VHCategory) holder).bingData(category);
                break;
            case ITEM_TYPE_BOOK:
                ((VHBook) holder).bingData(getBookByAdapterPosition(position));
                break;
        }
    }

    private BookEntity getBookByAdapterPosition(int position) {
        return books.get(position-1);
    }

    static class VHCategory extends RecyclerView.ViewHolder {

        public VHCategory(@NonNull View itemView) {
            super(itemView);
        }

        void bingData(CategoryEntity category) {
            //TODO Implement this
        }
    }

    static class VHBook extends RecyclerView.ViewHolder {

        public VHBook(@NonNull View itemView) {
            super(itemView);
        }

        void bingData(BookEntity book) {
            //TODO Implement this
        }
    }

}
