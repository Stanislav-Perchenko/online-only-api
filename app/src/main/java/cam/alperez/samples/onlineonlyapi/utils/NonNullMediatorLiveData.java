package cam.alperez.samples.onlineonlyapi.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public class NonNullMediatorLiveData<T> extends MediatorLiveData<T> {

    public NonNullMediatorLiveData(LiveData<T> source) {
        super.addSource(source, value -> {
            if (value != null) setValue(value);
        });
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
