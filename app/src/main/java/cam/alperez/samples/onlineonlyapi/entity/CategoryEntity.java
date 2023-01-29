package cam.alperez.samples.onlineonlyapi.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import cam.alperez.samples.onlineonlyapi.rest.model.ImageModel;
import cam.alperez.samples.onlineonlyapi.utils.IntId;

public class CategoryEntity implements Entity<CategoryEntity>, Parcelable {

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

    /******************************  Parcelable implementation  ***********************************/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id.idValue);
        parcel.writeString(displayName);
        parcel.writeInt(order);
        parcel.writeString(booksLink);
        parcel.writeParcelable(iconPng, i);
        parcel.writeParcelable(iconSvg, i);
    }

    protected CategoryEntity(Parcel in) {
        id = IntId.valueOf(in.readInt());
        displayName = in.readString();
        order = in.readInt();
        booksLink = in.readString();
        iconPng = in.readParcelable(ImageModel.class.getClassLoader());
        iconSvg = in.readParcelable(ImageModel.class.getClassLoader());
    }

    public static final Creator<CategoryEntity> CREATOR = new Creator<CategoryEntity>() {
        @Override
        public CategoryEntity createFromParcel(Parcel in) {
            return new CategoryEntity(in);
        }

        @Override
        public CategoryEntity[] newArray(int size) {
            return new CategoryEntity[size];
        }
    };
}
