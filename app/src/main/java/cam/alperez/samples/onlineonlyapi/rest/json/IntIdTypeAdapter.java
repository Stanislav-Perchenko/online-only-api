package cam.alperez.samples.onlineonlyapi.rest.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import cam.alperez.samples.onlineonlyapi.entity.Entity;
import cam.alperez.samples.onlineonlyapi.utils.IntId;

class IntIdTypeAdapter extends TypeAdapter<IntId<? extends Entity<?>>> {

    @Override
    public void write(JsonWriter out, IntId<? extends Entity<?>> value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.idValue);
        }
    }

    @Override
    public IntId<? extends Entity<?>> read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        } else {
            int id = in.nextInt();
            return new IntId<>(id);
        }
    }
}
