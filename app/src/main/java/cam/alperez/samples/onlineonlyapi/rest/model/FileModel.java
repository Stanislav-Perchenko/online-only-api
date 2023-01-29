package cam.alperez.samples.onlineonlyapi.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.net.MalformedURLException;
import java.net.URL;

import cam.alperez.samples.onlineonlyapi.utils.FileType;

public class FileModel implements Parcelable {
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

    /******************************  Parcelable implementation  ***********************************/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Object[] items = new Object[3];
        items[0] = (url == null) ? null : url.toString();
        items[1] = fileType.name();
        items[2] = sizeBytes;
        parcel.writeArray(items);
    }

    protected FileModel(Parcel in) {
        Object[] items = in.readArray(FileModel.class.getClassLoader());
        try {
            url = (items[0] == null) ? null : new URL((String) items[0]);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        fileType = FileType.valueOf((String) items[1]);
        sizeBytes = (Integer) items[2];
    }

    public static final Creator<FileModel> CREATOR = new Creator<FileModel>() {
        @Override
        public FileModel createFromParcel(Parcel in) {
            return new FileModel(in);
        }

        @Override
        public FileModel[] newArray(int size) {
            return new FileModel[size];
        }
    };
}
