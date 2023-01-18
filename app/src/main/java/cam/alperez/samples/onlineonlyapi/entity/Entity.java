package cam.alperez.samples.onlineonlyapi.entity;

import cam.alperez.samples.onlineonlyapi.utils.IntId;

public interface Entity<T extends Entity<?>> {

    IntId<T> getId();

}
