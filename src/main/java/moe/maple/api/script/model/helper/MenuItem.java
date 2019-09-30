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

import moe.maple.api.script.logic.action.BasicScriptAction;
import moe.maple.api.script.util.ListOf;

import java.util.List;

/**
 * A lame data class.
 */
public class MenuItem {

    private final String message;
    private final BasicScriptAction action;
    public MenuItem(String message, BasicScriptAction action) {
        this.message = message;
        this.action = action;
    }

    public String message() {
        return message;
    }

    public BasicScriptAction action() {
        return action;
    }

    public static MenuItem of(String message, BasicScriptAction action) {
        return new MenuItem( message, action);
    }

    public static MenuItem of(String message) {
        return new MenuItem(message, () -> {});
    }


    public static <L extends String, R extends BasicScriptAction> List<MenuItem> listOf(L l1, R r1, L l2, R r2) {
        return ListOf.tuples(MenuItem::new, l1, r1, l2, r2);
    }

    public static <L extends String, R extends BasicScriptAction> List<MenuItem> listOf(L l1, R r1, L l2, R r2, L l3, R r3) {
        return ListOf.tuples(MenuItem::new, l1, r1, l2, r2, l3, r3);
    }

    public static <L extends String, R extends BasicScriptAction> List<MenuItem> listOf(L l1, R r1, L l2, R r2, L l3, R r3, L l4, R r4) {
        return ListOf.tuples(MenuItem::new, l1, r1, l2, r2, l3, r3, l4, r4);
    }

    public static <L extends String, R extends BasicScriptAction> List<MenuItem> listOf(L l1, R r1, L l2, R r2, L l3, R r3, L l4, R r4, L l5, R r5) {
        return ListOf.tuples(MenuItem::new, l1, r1, l2, r2, l3, r3, l4, r4, l5, r5);
    }

    public static <L extends String, R extends BasicScriptAction> List<MenuItem> listOf(L l1, R r1, L l2, R r2, L l3, R r3, L l4, R r4, L l5, R r5, L l6, R r6) {
        return ListOf.tuples(MenuItem::new, l1, r1, l2, r2, l3, r3, l4, r4, l5, r5, l6, r6);
    }

    public static <L extends String, R extends BasicScriptAction> List<MenuItem> listOf(L l1, R r1, L l2, R r2, L l3, R r3, L l4, R r4, L l5, R r5, L l6, R r6, L l7, R r7) {
        return ListOf.tuples(MenuItem::new, l1, r1, l2, r2, l3, r3, l4, r4, l5, r5, l6, r6, l7, r7);
    }

    public static <L extends String, R extends BasicScriptAction> List<MenuItem> listOf(L l1, R r1, L l2, R r2, L l3, R r3, L l4, R r4, L l5, R r5, L l6, R r6, L l7, R r7, L l8, R r8) {
        return ListOf.tuples(MenuItem::new, l1, r1, l2, r2, l3, r3, l4, r4, l5, r5, l6, r6, l7, r7, l8, r8);
    }

    public static <L extends String, R extends BasicScriptAction> List<MenuItem> listOf(L l1, R r1, L l2, R r2, L l3, R r3, L l4, R r4, L l5, R r5, L l6, R r6, L l7, R r7, L l8, R r8, L l9, R r9) {
        return ListOf.tuples(MenuItem::new, l1, r1, l2, r2, l3, r3, l4, r4, l5, r5, l6, r6, l7, r7, l8, r8, l9, r9);
    }

    public static <L extends String, R extends BasicScriptAction> List<MenuItem> listOf(L l1, R r1, L l2, R r2, L l3, R r3, L l4, R r4, L l5, R r5, L l6, R r6, L l7, R r7, L l8, R r8, L l9, R r9, L l10, R r10) {
        return ListOf.tuples(MenuItem::new, l1, r1, l2, r2, l3, r3, l4, r4, l5, r5, l6, r6, l7, r7, l8, r8, l9, r9, l10, r10);
    }

}
