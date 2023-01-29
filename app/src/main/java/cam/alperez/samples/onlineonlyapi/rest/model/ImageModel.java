package cam.alperez.samples.onlineonlyapi.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.net.MalformedURLException;
import java.net.URL;

public class ImageModel implements Parcelable {
    private final URL url;

    @SerializedName("size")
    private final int sizeBytes;

    @SerializedName(value = "w", alternate = {"width"})
    private final int width;

    @SerializedName(value = "h", alternate = {"height"})
    private final int height;

    @SerializedName(value = "is_vector", alternate = {"isVector"})
    private final boolean isVector;

    public ImageModel(URL url, int sizeBytes, int width, int height, boolean isVector) {
        this.url = url;
        this.sizeBytes = sizeBytes;
        this.width = width;
        this.height = height;
        this.isVector = isVector;
    }

    public URL getUrl() {
        return url;
    }

    public int getSizeBytes() {
        return sizeBytes;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isVector() {
        return isVector;
    }

    /******************************  Parcelable implementation  ***********************************/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Object[] items = new Object[5];
        items[0] = (url == null) ? null : url.toString();
        items[1] = sizeBytes;
        items[2] = width;
        items[3] = height;
        items[4] = (byte) (isVector ? 1 : 0);
        dest.writeArray(items);
    }

    protected ImageModel(Parcel in) {
        Object[] items = in.readArray(ImageModel.class.getClassLoader());
        try {
            url = (items[0] == null) ? null : new URL((String) items[0]);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        sizeBytes = (Integer) items[1];
        width = (Integer) items[2];
        height = (Integer) items[3];
        isVector = (Byte)items[4] != 0;
    }

    public static final Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel in) {
            return new ImageModel(in);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };
}
