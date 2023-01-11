package cam.alperez.samples.onlineonlyapi.rest.entity;

import cam.alperez.samples.onlineonlyapi.utils.IntId;

public interface Entity<T> {

    IntId<T> getId();

}
