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

import moe.maple.api.script.util.triple.TriFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Created on 9/30/2019.
 */
public class ListOf {

    public static <L, R, T> List<T> tuples(BiFunction<L, R, T> constructor, Object... args) {
        if ((args.length & 1) != 0) {
            throw new InternalError("length is odd");
        }
        List<T> list = new ArrayList<>(args.length / 2);
        for (int i = 0; i < args.length; i += 2) {
            @SuppressWarnings("unchecked")
            L left = Objects.requireNonNull((L)args[i]);
            @SuppressWarnings("unchecked")
            R right = Objects.requireNonNull((R)args[i+1]);
            list.add(constructor.apply(left, right));
        }
        return list;
    }

    public static <L, C, R, T> List<T> triples(TriFunction<L, C, R, T> constructor, Object... args) {
        if ((args.length % 3) != 0) {
            throw new InternalError("length is odd");
        }
        List<T> list = new ArrayList<>(args.length / 3);
        for (int i = 0; i < args.length; i += 3) {
            @SuppressWarnings("unchecked")
            L left = Objects.requireNonNull((L)args[i]);
            @SuppressWarnings("unchecked")
            C center = Objects.requireNonNull((C)args[i+1]);
            @SuppressWarnings("unchecked")
            R right = Objects.requireNonNull((R)args[i+2]);
            list.add(constructor.apply(left, center, right));
        }
        return list;
    }
}
