package cam.alperez.samples.onlineonlyapi.rest.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cam.alperez.samples.onlineonlyapi.rest.model.FileModel;
import cam.alperez.samples.onlineonlyapi.rest.model.ImageModel;
import cam.alperez.samples.onlineonlyapi.utils.IntId;

public class BookEntity implements Entity<BookEntity> {

    private final IntId<BookEntity> id;

    @SerializedName("category_id")
    private final IntId<CategoryEntity> categoryId;

    private final int order;

    private final String title;

    private final List<String> authors;

    private final String publisher;

    private final int year;

    private final String isbn;

    private final ImageModel cover;

    private final FileModel file;

    public BookEntity(IntId<BookEntity> id,
                      IntId<CategoryEntity> categoryId,
                      int order,
                      String title,
                      List<String> authors,
                      String publisher,
                      int year,
                      String isbn,
                      ImageModel cover,
                      FileModel file)
    {
        this.id = id;
        this.categoryId = categoryId;
        this.order = order;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.year = year;
        this.isbn = isbn;
        this.cover = cover;
        this.file = file;
    }

    @Override
    public IntId<BookEntity> getId() {
        return id;
    }

    public IntId<CategoryEntity> getCategoryId() {
        return categoryId;
    }

    public int getOrder() {
        return order;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getYear() {
        return year;
    }

    public String getIsbn() {
        return isbn;
    }

    public ImageModel getCover() {
        return cover;
    }

    public FileModel getFile() {
        return file;
    }
}
