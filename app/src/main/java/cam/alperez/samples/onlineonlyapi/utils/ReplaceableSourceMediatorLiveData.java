package cam.alperez.samples.onlineonlyapi.utils;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public class ReplaceableSourceMediatorLiveData<T> extends MediatorLiveData<T> {

    private LiveData<?> source;

    @MainThread
    public void setSource(@NonNull LiveData<T> source) {
        if (this.source != null) {
            super.removeSource(this.source);
        }
        this.source = source;
        super.addSource(source, this::setValue);
    }

    @Override
    public <S> void addSource(@NonNull LiveData<S> source, @NonNull Observer<? super S> onChanged) {
        throw new RuntimeException("Call not supported");
    }

    @Override
    public <S> void removeSource(@NonNull LiveData<S> toRemote) {
        throw new RuntimeException("Call not supported");
    }

}
