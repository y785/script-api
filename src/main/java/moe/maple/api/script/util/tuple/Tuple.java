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

package moe.maple.api.script.util.tuple;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Tuple<L, R> {
    L left();
    R right();

    static <L, R> Tuple<L, R> of(L left, R right) {
        return new ImmutableTuple<>(left, right);
    }

    /**
     * Utility method to perform an action for each Tuple
     * @param tuples An iterable Tuple collection
     * @param action An action to perform for each Tuple
     * @param <L> Left type
     * @param <R> Right type
     */
    static <L, R> void forEach(Iterable<Tuple<L,R>> tuples, BiConsumer<? super L, ? super R> action) {
        for(var tuple : tuples) {
            action.accept(tuple.left(), tuple.right());
        }
    }

    static <T> void flatForEach(Iterable<Tuple<T, T>> tuples, Consumer<? super T> action) {
        for(var tuple : tuples) {
            action.accept(tuple.left());
            action.accept(tuple.right());
        }
    }

    @SafeVarargs
    static <T> List<T> flatten(Tuple<T, T>... tuples) {
        List<T> list = new ArrayList<>(tuples.length*2);
        for (int i = 0, tuplesLength = tuples.length; i < tuplesLength; i++) {
            Tuple<T, T> tuple = tuples[i];
            list.add(tuple.left());
            list.add(tuple.right());
        }
        return list;
    }

    static <T> List<T> flatten(Collection<Tuple<T, T>> tuples) {
        List<T> list = new ArrayList<>(tuples.size()*2);
        for (Tuple<T, T> tuple : tuples) {
            list.add(tuple.left());
            list.add(tuple.right());
        }
        return list;
    }


    //========================================== Caution: memes below =================================================

    /**
     * Returns a list containing N tuples.
     *
     * @param <L> the left type
     * @param <R> the right type
     * @param args varargs to fill the list
     * @return a {@code List} containing the specified elements
     * @throws NullPointerException if either value is {@code null}
     */
    private static <L, R> List<Tuple<L, R>> unsafeListOf(Object... args) {
        if ((args.length & 1) != 0) {
            throw new InternalError("length is odd");
        }
        List<Tuple<L,R>> list = new ArrayList<>(args.length / 2);
        for (int i = 0; i < args.length; i += 2) {
            @SuppressWarnings("unchecked")
            L left = Objects.requireNonNull((L)args[i]);
            @SuppressWarnings("unchecked")
            R right = Objects.requireNonNull((R)args[i+1]);
            list.add(new ImmutableTuple<>(left, right));
        }
        return list;
    }

    @SafeVarargs
    static <V> List<Tuple<V, V>> listOf(V... args) {
        return unsafeListOf((Object[]) args);
    }

    static <L, R> List<Tuple<L, R>> listOf(L l1, R r1, L l2, R r2) {
        return unsafeListOf(l1, r1, l2, r2);
    }

    static <L, R> List<Tuple<L, R>> listOf(L l1, R r1, L l2, R r2, L l3, R r3) {
        return unsafeListOf(l1, r1, l2, r2, l3, r3);
    }

    static <L, R> List<Tuple<L, R>> listOf(L l1, R r1, L l2, R r2, L l3, R r3, L l4, R r4) {
        return unsafeListOf(l1, r1, l2, r2, l3, r3, l4, r4);
    }

    static <L, R> List<Tuple<L, R>> listOf(L l1, R r1, L l2, R r2, L l3, R r3, L l4, R r4, L l5, R r5) {
        return unsafeListOf(l1, r1, l2, r2, l3, r3, l4, r4, l5, r5);
    }

    static <L, R> List<Tuple<L, R>> listOf(L l1, R r1, L l2, R r2, L l3, R r3, L l4, R r4, L l5, R r5, L l6, R r6) {
        return unsafeListOf(l1, r1, l2, r2, l3, r3, l4, r4, l5, r5, l6, r6);
    }

    static <L, R> List<Tuple<L, R>> listOf(L l1, R r1, L l2, R r2, L l3, R r3, L l4, R r4, L l5, R r5, L l6, R r6, L l7, R r7) {
        return unsafeListOf(l1, r1, l2, r2, l3, r3, l4, r4, l5, r5, l6, r6, l7, r7);
    }

    static <L, R> List<Tuple<L, R>> listOf(L l1, R r1, L l2, R r2, L l3, R r3, L l4, R r4, L l5, R r5, L l6, R r6, L l7, R r7, L l8, R r8) {
        return unsafeListOf(l1, r1, l2, r2, l3, r3, l4, r4, l5, r5, l6, r6, l7, r7, l8, r8);
    }

    static <L, R> List<Tuple<L, R>> listOf(L l1, R r1, L l2, R r2, L l3, R r3, L l4, R r4, L l5, R r5, L l6, R r6, L l7, R r7, L l8, R r8, L l9, R r9) {
        return unsafeListOf(l1, r1, l2, r2, l3, r3, l4, r4, l5, r5, l6, r6, l7, r7, l8, r8, l9, r9);
    }

    static <L, R> List<Tuple<L, R>> listOf(L l1, R r1, L l2, R r2, L l3, R r3, L l4, R r4, L l5, R r5, L l6, R r6, L l7, R r7, L l8, R r8, L l9, R r9, L l10, R r10) {
        return unsafeListOf(l1, r1, l2, r2, l3, r3, l4, r4, l5, r5, l6, r6, l7, r7, l8, r8, l9, r9, l10, r10);
    }


}
