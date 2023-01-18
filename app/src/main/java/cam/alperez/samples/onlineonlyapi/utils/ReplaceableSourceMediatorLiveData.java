package cam.alperez.samples.onlineonlyapi.utils;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class ReplaceableSourceMediatorLiveData<T> extends MediatorLiveData<T> {

    private LiveData<?> source;

    @MainThread
    public void setSource(@NonNull LiveData<T> source) {
        if (this.source != null) {
            this.removeSource(this.source);
        }
        this.source = source;
        this.addSource(source, this::setValue);
    }

}
