package moe.maple.api.script.util;

import java.util.Collection;

public class With {

    public static <T> void continueException(Collection<T> collection, Consumers.ContinueException<T> consumer) {
        for (T object : collection) {
            try {
                consumer.accept(object);
            } catch (Exception e) { }
        }
    }

    public static <T> void continueException(T[] collection, Consumers.ContinueException<T> consumer) {
        for (T object : collection) {
            try {
                consumer.accept(object);
            } catch (Exception e) { }
        }
    }

    public static <T> void index(Collection<T> collection, Consumers.IndexedConsumer<T> consumer) {
        int index = 0;
        for (T object : collection) {
            consumer.accept(object, index++);
        }
    }

    public static <T> void index(T[] collection, Consumers.IndexedConsumer<T> consumer) {
        for (int i = 0; i < collection.length; i++) {
            consumer.accept(collection[i], i);
        }
    }

    public static <T> void indexAndCount(Collection<T> collection, Consumers.IndexedSizedConsumer<T> consumer) {
        var index = 0;
        var length = collection.size();
        for (T object : collection) {
            consumer.accept(object, index++, length);
        }
    }

    public static <T> void indexAndCount(T[] collection, Consumers.IndexedSizedConsumer<T> consumer) {
        var length = collection.length;
        for (int i = 0; i < collection.length; i++) {
            consumer.accept(collection[i], i, length);
        }
    }

    public static class Consumers {
        @FunctionalInterface
        public interface IndexedConsumer<T> {
            void accept(T t, int index);
        }

        @FunctionalInterface
        public interface IndexedSizedConsumer<T> {
            void accept(T t, int index, int length);
        }

        @FunctionalInterface
        public interface ContinueException<T> {
            void accept(T t) throws Exception;
        }
    }

}
