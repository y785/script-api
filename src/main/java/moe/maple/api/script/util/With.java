/*
 * Copyright (C) 2019, y785, http://github.com/y785
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
