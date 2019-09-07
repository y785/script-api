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

package moe.maple.api.script.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.function.Consumer;
import java.util.function.Function;

@java.lang.annotation.Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Script {
    /**
     * The name(s) of the script. These should match
     * the .wz property of script names, like:
     * Map.wz/Map/Map0/000010000.img/info/onUserEnter
     */
    String[] name() default "";

    /**
     *  Your name. B-baka.
     */
    String author() default "";

    /**
     *  A brief description of the script, what it does, where it's located, etc.
     */
    String description() default "";

    /**
     * The Map IDs of where this script is located.
     * Some scripts can be located in multiple maps/fields.
     */
    int[] field() default 0;
}
