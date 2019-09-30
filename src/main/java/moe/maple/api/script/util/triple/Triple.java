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

package moe.maple.api.script.util.triple;

import moe.maple.api.script.util.ListOf;
import moe.maple.api.script.util.tuple.Tuple;

import java.util.List;

public interface Triple<L, C, R> {

    L left();
    C center();
    R right();

    static <L, C, R> Triple<L, C, R> of(L left, C center, R right) {
        return new ImmutableTriple<>(left, center, right);
    }

    private static <L, C, R> List<Triple<L, C, R>> unsafeListOf(Object... args) {
        return ListOf.triples(ImmutableTriple<L, C, R>::new, args);
    }

    @SafeVarargs
    static <V> List<Triple<V, V, V>> listOf(V... args) {
        return unsafeListOf((Object[]) args);
    }

    static <L, C, R> List<Triple<L, C, R>> listOf(L l1, C c1, R r1, L l2, C c2, R r2) {
        return unsafeListOf(l1, c1, r1, l2, c2, r2);
    }

    static <L, C, R> List<Triple<L, C, R>> listOf(L l1, C c1, R r1, L l2, C c2, R r2, L l3, C c3, R r3) {
        return unsafeListOf(l1, c1, r1, l2, c2, r2, l3, c3, r3);
    }

    static <L, C, R> List<Triple<L, C, R>> listOf(L l1, C c1, R r1, L l2, C c2, R r2, L l3, C c3, R r3, L l4, C c4, R r4) {
        return unsafeListOf(l1, c1, r1, l2, c2, r2, l3, c3, r3, l4, c4, r4);
    }

    static <L, C, R> List<Triple<L, C, R>> listOf(L l1, C c1, R r1, L l2, C c2, R r2, L l3, C c3, R r3, L l4, C c4, R r4, L l5, C c5, R r5) {
        return unsafeListOf(l1, c1, r1, l2, c2, r2, l3, c3, r3, l4, c4, r4, l5, c5, r5);
    }

    static <L, C, R> List<Triple<L, C, R>> listOf(L l1, C c1, R r1, L l2, C c2, R r2, L l3, C c3, R r3, L l4, C c4, R r4, L l5, C c5, R r5, L l6, C c6, R r6) {
        return unsafeListOf(l1, c1, r1, l2, c2, r2, l3, c3, r3, l4, c4, r4, l5, c5, r5, l6, c6, r6);
    }

    static <L, C, R> List<Triple<L, C, R>> listOf(L l1, C c1, R r1, L l2, C c2, R r2, L l3, C c3, R r3, L l4, C c4, R r4, L l5, C c5, R r5, L l6, C c6, R r6, L l7, C c7, R r7) {
        return unsafeListOf(l1, c1, r1, l2, c2, r2, l3, c3, r3, l4, c4, r4, l5, c5, r5, l6, c6, r6, l7, c7, r7);
    }

    static <L, C, R> List<Triple<L, C, R>> listOf(L l1, C c1, R r1, L l2, C c2, R r2, L l3, C c3, R r3, L l4, C c4, R r4, L l5, C c5, R r5, L l6, C c6, R r6, L l7, C c7, R r7, L l8, C c8, R r8) {
        return unsafeListOf(l1, c1, r1, l2, c2, r2, l3, c3, r3, l4, c4, r4, l5, c5, r5, l6, c6, r6, l7, c7, r7, l8, c8, r8);
    }

    static <L, C, R> List<Triple<L, C, R>> listOf(L l1, C c1, R r1, L l2, C c2, R r2, L l3, C c3, R r3, L l4, C c4, R r4, L l5, C c5, R r5, L l6, C c6, R r6, L l7, C c7, R r7, L l8, C c8, R r8, L l9, C c9, R r9) {
        return unsafeListOf(l1, c1, r1, l2, c2, r2, l3, c3, r3, l4, c4, r4, l5, c5, r5, l6, c6, r6, l7, c7, r7, l8, c8, r8, l9, c9, r9);
    }

    static <L, C, R> List<Triple<L, C, R>> listOf(L l1, C c1, R r1, L l2, C c2, R r2, L l3, C c3, R r3, L l4, C c4, R r4, L l5, C c5, R r5, L l6, C c6, R r6, L l7, C c7, R r7, L l8, C c8, R r8, L l9, C c9, R r9, L l10, R r10) {
        return unsafeListOf(l1, c1, r1, l2, c2, r2, l3, c3, r3, l4, c4, r4, l5, c5, r5, l6, c6, r6, l7, c7, r7, l8, c8, r8, l9, c9, r9, l10, r10);
    }
}
