package cam.alperez.samples.onlineonlyapi.entity;

import cam.alperez.samples.onlineonlyapi.utils.IntId;

public interface Entity<T> {

    IntId<T> getId();

}
