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

import moe.maple.api.script.util.builder.ScriptMenuBuilder;
import org.slf4j.helpers.MessageFormatter;

public class ScriptStringBuilder {

    private final StringBuilder sb;
    // Latest color, used to revert back when writing colored text.
    private ScriptColor color;

    public ScriptStringBuilder() {
        this.sb = new StringBuilder();
        this.color = ScriptColor.BLACK;
    }

    public ScriptStringBuilder append(String str) {
        sb.append(str);
        return this;
    }

    public ScriptStringBuilder append(Object object) {
        sb.append(String.valueOf(object));
        return this;
    }

    public ScriptStringBuilder append(StringBuffer sb) {
        sb.append(sb);
        return this;
    }

    public ScriptStringBuilder append(CharSequence s) {
        sb.append(s);
        return this;
    }

    public ScriptStringBuilder append(String format, Object... objects) {
        sb.append(MessageFormatter.arrayFormat(format, objects).getMessage());
        return this;
    }

    // =================================================================================================================

    public ScriptStringBuilder black() {
        color = ScriptColor.BLACK;
        sb.append(color.prefix);
        return this;
    }

    public ScriptStringBuilder black(String str) {
        sb.append(ScriptColor.BLACK).append(str).append(color.prefix);
        return this;
    }

    public ScriptStringBuilder blue() {
        color = ScriptColor.BLUE;
        sb.append(color.prefix);
        return this;
    }

    public ScriptStringBuilder blue(String str) {
        sb.append(ScriptColor.BLUE).append(str).append(color.prefix);
        return this;
    }

    public ScriptStringBuilder red() {
        color = ScriptColor.RED;
        sb.append(color.prefix);
        return this;
    }

    public ScriptStringBuilder red(String str) {
        sb.append(ScriptColor.BLUE).append(str).append(color.prefix);
        return this;
    }

    public ScriptStringBuilder green() {
        color = ScriptColor.GREEN;
        sb.append(color.prefix);
        return this;
    }

    public ScriptStringBuilder green(String str) {
        sb.append(ScriptColor.BLUE).append(str).append(color.prefix);
        return this;
    }

    public ScriptStringBuilder purple() {
        color = ScriptColor.PURPLE;
        sb.append(color.prefix);
        return this;
    }

    public ScriptStringBuilder purple(String str) {
        sb.append(ScriptColor.BLUE).append(str).append(color.prefix);
        return this;
    }

    // =================================================================================================================

    public ScriptStringBuilder appendMenuItem(int menuIndex, String format, Object... objects) {
        append("#L").append(menuIndex).append("# ").append(format, objects).append("#l\r\n");
        return this;
    }

    public ScriptStringBuilder appendMenuItem(int menuIndex, String line) {
        append("#L").append(menuIndex).append("# ").append(line).append("#l\r\n");
        return this;
    }

    public ScriptStringBuilder appendMenu(ScriptColor color, String... options) {
        if (color != this.color)
            sb.append(color.prefix);
        With.index(options, (s, i) -> {
            appendMenuItem(i, s);
        });
        if (color != this.color)
            sb.append(this.color.prefix);
        return this;
    }

    public ScriptStringBuilder appendMenu(String... options) {
        return appendMenu(ScriptColor.BLUE, options);
    }

    @Override
    public String toString() {
        return sb.toString();
    }

    public enum ScriptColor {
        BLACK("#k"),
        BLUE("#b"),
        RED("#r"),
        GREEN("#g"),
        PURPLE("#d");
        public final String prefix;

        ScriptColor(String prefix) { this.prefix = prefix; }
    }
}
