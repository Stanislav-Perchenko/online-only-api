package cam.alperez.samples.onlineonlyapi.rest.json;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.net.URL;

import cam.alperez.samples.onlineonlyapi.utils.FileType;
import cam.alperez.samples.onlineonlyapi.utils.IntId;

public class ApiGsonTypeAdapterFactory implements TypeAdapterFactory {

    public ApiGsonTypeAdapterFactory() {}

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> cls = type.getRawType();
        if (cls == IntId.class) {
            return (TypeAdapter<T>) new IntIdTypeAdapter();
        } else if (cls == FileType.class) {
            return (TypeAdapter<T>) new FileTypeTypeAdapter();
        } else if (cls == URL.class) {
            return (TypeAdapter<T>) new URLTypeAdapter();
        } else {
            return null;
        }
    }
}
