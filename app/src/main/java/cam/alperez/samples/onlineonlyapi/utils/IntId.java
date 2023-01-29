package cam.alperez.samples.onlineonlyapi.utils;

import cam.alperez.samples.onlineonlyapi.entity.Entity;

public class IntId<T extends Entity<?>> {
    public final int idValue;

    private IntId(int idValue) {
        this.idValue = idValue;
    }

    public static <E extends Entity<?>> IntId<E> valueOf(int idValue) {
        return new IntId<>(idValue);
    }
}
