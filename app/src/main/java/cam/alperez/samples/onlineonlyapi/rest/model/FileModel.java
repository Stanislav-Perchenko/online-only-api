package cam.alperez.samples.onlineonlyapi.rest.model;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

import cam.alperez.samples.onlineonlyapi.utils.FileType;

public class FileModel {
    private final URL url;

    @SerializedName("type")
    private final FileType fileType;

    @SerializedName("size")
    private final int sizeBytes;

    public FileModel(URL url, FileType fileType, int sizeBytes) {
        this.url = url;
        this.fileType = fileType;
        this.sizeBytes = sizeBytes;
    }

    public URL getUrl() {
        return url;
    }

    public FileType getFileType() {
        return fileType;
    }

    public int getSizeBytes() {
        return sizeBytes;
    }
}
