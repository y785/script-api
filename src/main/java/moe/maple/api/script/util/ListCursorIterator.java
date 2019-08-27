package moe.maple.api.script.util;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author umbreon22
 * A simple collection with access managed solely by a cursor.
 * Created on 10/27/2018.
 */
public class ListCursorIterator<E> implements CursorIterator<E> {
    private final List<E> list;
    private int cursor = -1;
    public ListCursorIterator(List<E> l){
        this.list = l;
    }

    public E next() {
        return list.get(++cursor);
    }

    public boolean add(E element) {
        return list.add(element);
    }

    public void set(int index) {
        if(size() >= index && index >= 0) {
            cursor = index;
        } else throw new IllegalArgumentException("Invalid set index in cursor. ("+index+"/"+size());
    }

    public E prev() {
        return list.get(--cursor);
    }

    public boolean hasPrev() {
        return cursor - 1 >= 0;
    }

    public boolean hasNext() {
        return cursor + 1 < list.size();
    }

    public E get(int index) {//Can probably be improved.
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    public int cursor() {
        return cursor;
    }

    public void clear() {
        list.clear();
        cursor = -1;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return list.toString() + "; Cursor: " + cursor + "; Size: " + list.size();
    }
}

