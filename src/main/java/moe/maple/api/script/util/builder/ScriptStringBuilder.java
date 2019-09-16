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

package moe.maple.api.script.util.builder;

public class ScriptStringBuilder extends ScriptMenuBuilder<ScriptStringBuilder> {

    private final StringBuilder textBuilder;

    public ScriptStringBuilder(StringBuilder sb) {
        super(sb);
        this.textBuilder = sb;
        resetColorAndStyle();
    }

    public ScriptStringBuilder() {
        this(new StringBuilder());
    }

    public ScriptStringBuilder get() {
        return this;
    }

    @Override
    public ScriptStringBuilder append(String str) {
        textBuilder.append(str);
        return this;
    }

    @Override
    public ScriptStringBuilder append(Object object) {
        textBuilder.append(object);
        return this;
    }

    @Override
    public ScriptStringBuilder append(StringBuffer sb) {
        textBuilder.append(sb);
        return this;
    }

    public ScriptStringBuilder append(CharSequence text) {
        textBuilder.append(text);
        return this;
    }

    public ScriptStringBuilder appendWithColor(FontColor color, String tempColoredText) {
        if(currentColor == color) {
            append(tempColoredText);
        } else {
            append(color.prefix);
            append(tempColoredText);
            append(currentColor.prefix);
        }
        return this;
    }

    public ScriptStringBuilder appendWithStyle(FontStyle style, String tempStyledText) {
        if(currentStyle == style) {
            append(tempStyledText);
        } else {
            append(style.prefix);
            append(tempStyledText);
            append(currentStyle.prefix);
        }
        return this;
    }

    public ScriptStringBuilder appendWithStyleAndColor(FontStyle style, FontColor color, String text) {
        if(style != FontStyle.NONE) textBuilder.append(style.prefix);
        if(color != FontColor.NONE) textBuilder.append(color.prefix);
        textBuilder.append(text);
        if(this.currentStyle != style && this.currentStyle != FontStyle.NONE) {
            textBuilder.append(this.currentStyle.prefix);
        }
        return this;
    }

    /**
     * @return Your desired Script String.
     */
    public String build() {
        if(textBuilder.length() == 0) {
            return "Invalid input.";
        }
        return textBuilder.toString();
    }

    @Override
    public String toString() {
        return build();
    }
}