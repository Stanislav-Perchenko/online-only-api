package cam.alperez.samples.onlineonlyapi.rest.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Locale;

import cam.alperez.samples.onlineonlyapi.utils.FileType;

class FileTypeTypeAdapter extends TypeAdapter<FileType> {

    @Override
    public void write(JsonWriter out, FileType value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.name().toLowerCase(Locale.UK));
        }
    }

    @Override
    public FileType read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        } else {
            String value = in.nextString().toUpperCase(Locale.UK);
            try {
                return FileType.valueOf(value);
            } catch (Exception e) {
                throw new IOException(String.format("Cannot deserialize FileType: no such value (%s)", value));
            }
        }
    }
}
