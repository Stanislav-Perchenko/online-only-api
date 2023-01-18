package cam.alperez.samples.onlineonlyapi.utils;

import cam.alperez.samples.onlineonlyapi.entity.Entity;

public class IntId<T extends Entity> {
    public final int idValue;

    public IntId(int idValue) {
        this.idValue = idValue;
    }
}
