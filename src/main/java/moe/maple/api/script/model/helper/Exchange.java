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

package moe.maple.api.script.model.helper;

import moe.maple.api.script.util.tuple.Tuple;

import java.util.*;

public class Exchange implements Iterable<Tuple<Integer, Integer>> {

    private final int money;

    private final List<Tuple<Integer, Integer>> items;

    public Exchange(int money, Collection<Tuple<Integer, Integer>> itemTemplateIdAndCount) {
        this.money = money;
        this.items = new ArrayList<>(itemTemplateIdAndCount);
    }

    public Exchange(int money, int... itemTemplateIdAndCount) {
        this(money, Arrays.stream(itemTemplateIdAndCount).boxed().toArray(Integer[]::new));
    }

    public Exchange(int money, Integer... itemTemplateIdAndCount) {
        this.money = money;
        this.items = Tuple.listOf(itemTemplateIdAndCount);
    }

    @SafeVarargs
    public Exchange(int money, Tuple<Integer, Integer>... itemTemplateIdAndCount) {
        this(money, List.of(itemTemplateIdAndCount));
    }

    public Exchange(int money, Tuple<Integer, Integer> item) {
        this.money = money;

        this.items = new ArrayList<>(1);
        this.items.add(item);
    }

    public Exchange(int money, int itemId, int itemCount) {
        this(money, Tuple.of(itemId, itemCount));
    }

    public Exchange(int money) {
        this.money = money;
        this.items = Collections.emptyList();
    }

    public int getMoney() { return money; }

    public List<Tuple<Integer, Integer>> getItems() { return Collections.unmodifiableList(items); }

    @Override
    public Iterator<Tuple<Integer, Integer>> iterator() {
        return items.iterator();
    }
}
