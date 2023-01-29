package cam.alperez.samples.onlineonlyapi.utils;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public final class MapMutableLiveData<T> extends MediatorLiveData<T> {

    private MapMutableLiveData() {
    }

    public static <X, Y> MapMutableLiveData<Y> create(@NonNull LiveData<X> source, @NonNull final Function<X, Y> mapFunction) {
        final MapMutableLiveData<Y> instance = new MapMutableLiveData<>();

        instance.addSource(source, x -> instance.setValue(mapFunction.apply(x)));

        return instance;
    }


}
