package cam.alperez.samples.onlineonlyapi.entity;

import com.google.gson.annotations.SerializedName;

import cam.alperez.samples.onlineonlyapi.rest.model.ImageModel;
import cam.alperez.samples.onlineonlyapi.utils.IntId;

public class CategoryEntity implements Entity<CategoryEntity> {

    private final IntId<CategoryEntity> id;

    @SerializedName("display_name")
    private final String displayName;

    private final int order;

    @SerializedName("books_link")
    private final String booksLink;

    @SerializedName("icon_png")
    private final ImageModel iconPng;

    @SerializedName("icon_svg")
    private final ImageModel iconSvg;

    public CategoryEntity(IntId<CategoryEntity> id,
                          String displayName,
                          int order,
                          String booksLink,
                          ImageModel iconPng,
                          ImageModel iconSvg)
    {
        this.id = id;
        this.displayName = displayName;
        this.order = order;
        this.booksLink = booksLink;
        this.iconPng = iconPng;
        this.iconSvg = iconSvg;
    }

    @Override
    public IntId<CategoryEntity> getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getOrder() {
        return order;
    }

    public String getBooksLink() {
        return booksLink;
    }

    public ImageModel getIconPng() {
        return iconPng;
    }

    public ImageModel getIconSvg() {
        return iconSvg;
    }
}
