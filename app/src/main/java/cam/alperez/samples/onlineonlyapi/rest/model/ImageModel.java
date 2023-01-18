package cam.alperez.samples.onlineonlyapi.rest.model;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

public class ImageModel {
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
}
