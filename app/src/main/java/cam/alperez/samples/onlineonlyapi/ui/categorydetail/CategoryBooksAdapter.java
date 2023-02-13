package cam.alperez.samples.onlineonlyapi.ui.categorydetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cam.alperez.samples.onlineonlyapi.R;
import cam.alperez.samples.onlineonlyapi.entity.BookEntity;
import cam.alperez.samples.onlineonlyapi.entity.CategoryEntity;
import cam.alperez.samples.onlineonlyapi.rest.model.ImageModel;
import cam.alperez.samples.onlineonlyapi.ui.utils.UiUtils;

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
                return new VHCategory(inflater.inflate(R.layout.category_header_list_item, parent, false));
            case ITEM_TYPE_BOOK:
                return new VHBook(inflater.inflate(R.layout.book_list_item, parent, false));
            default:
                throw new RuntimeException("View type not supported: " + viewType);
        }
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
        private final ImageView ivCategoryLogo;
        private final TextView tvCategoryTitle;

        private final Point logoScaledSize = new Point();

        public VHCategory(@NonNull View itemView) {
            super(itemView);
            ivCategoryLogo = itemView.findViewById(R.id.img_category_header);
            tvCategoryTitle= itemView.findViewById(R.id.category_title_header);
        }

        void bingData(CategoryEntity category) {
            final ImageModel img = category.getIconPng();
            UiUtils.calculateScaledImageSize(img.getWidth(), img.getHeight(),
                    ivCategoryLogo.getLayoutParams().width, ivCategoryLogo.getLayoutParams().height,
                    logoScaledSize);

            Picasso.get()
                    .load(img.getUrl().toString())
                    .resize(logoScaledSize.x, logoScaledSize.y)
                    .placeholder(R.drawable.image_placeholder)
                    .into(ivCategoryLogo);
            tvCategoryTitle.setText(category.getDisplayName());
        }
    }

    static class VHBook extends RecyclerView.ViewHolder {

        private final ImageView ivBookCover;
        private final TextView tvBookTitle;
        private final TextView tvAuthors;
        private final TextView tvYear;
        private final TextView tvPublisher;
        private final TextView tvIsbnTitle;
        private final TextView tvIsbnValue;

        private final Point coverScaledSize = new Point();

        public VHBook(@NonNull View itemView) {
            super(itemView);
            ivBookCover = itemView.findViewById(R.id.img_book_cover);
            tvBookTitle = itemView.findViewById(R.id.txt_book_title);
            tvAuthors = itemView.findViewById(R.id.txt_book_authors);
            tvYear = itemView.findViewById(R.id.txt_book_year);
            tvPublisher = itemView.findViewById(R.id.txt_book_publisher);
            tvIsbnTitle = itemView.findViewById(R.id.book_isbn_title);
            tvIsbnValue = itemView.findViewById(R.id.book_isbn_value);
        }

        @SuppressLint("SetTextI18n")
        void bingData(BookEntity book) {
            final ImageModel img = book.getCover();
                    UiUtils.calculateScaledImageSize(img.getWidth(), img.getHeight(),
                    ivBookCover.getLayoutParams().width, ivBookCover.getLayoutParams().height,
                    coverScaledSize);
            Picasso.get()
                    .load(book.getCover().getUrl().toString())
                    .resize(coverScaledSize.x, coverScaledSize.y)
                    .placeholder(R.drawable.image_placeholder)
                    .into(ivBookCover);
            tvBookTitle.setText(book.getTitle());
            tvAuthors.setText(buildDisplayedAuthors(book.getAuthors()));
            tvYear.setText(Integer.toString(book.getYear()));
            tvPublisher.setText(book.getPublisher());
            if (TextUtils.isEmpty(book.getIsbn())) {
                tvIsbnTitle.setVisibility(View.INVISIBLE);
                tvIsbnValue.setText("");
            } else {
                tvIsbnTitle.setVisibility(View.VISIBLE);
                tvIsbnValue.setText(book.getIsbn());
            }
        }

        private CharSequence buildDisplayedAuthors(List<String> authors) {
            if (authors.isEmpty()) {
                return "";
            } else if (authors.size() == 1) {
                return authors.get(0);
            } else {
                StringBuilder sb = new StringBuilder();
                authors.forEach(author -> {
                    if (sb.length() > 0) sb.append(", ");
                    sb.append(author);
                });
                return sb.toString();
            }
        }
    }

}
