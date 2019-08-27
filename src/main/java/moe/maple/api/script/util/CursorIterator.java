package moe.maple.api.script.util;

/**
 * Needs documentation :)
 * Created on 8/26/2019.
 */
public interface CursorIterator<E> {
    E next();
    boolean add(E element);
    void set(int index);
    E prev();
    boolean hasPrev();
    boolean hasNext();
    E get(int index);
    int size();
    int cursor();
    void clear();
    boolean isEmpty();
}
