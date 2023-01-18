package cam.alperez.samples.onlineonlyapi.rest.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.net.URL;

class URLTypeAdapter extends TypeAdapter<URL> {

    @Override
    public void write(JsonWriter out, URL value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.toString());
        }
    }

    @Override
    public URL read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        } else {
            String value = in.nextString().trim();
            if (value.isEmpty()) {
                throw new IOException("Cannot deserialize URL: value is empty");
            }

            //This throws MalformedURLException in case of error, so we are good with this.
            return new URL(value);
        }
    }
}
