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

import moe.maple.api.script.util.Tuple;

import java.util.Collection;

/**
 * @author umbreon22
 * A builder for AskMenu.
 */

public class ScriptMenuBuilder extends StyleAndColorBuilder<ScriptMenuBuilder> implements CharacterSequenceBuilder<ScriptMenuBuilder>, AppendingBuilder<ScriptMenuBuilder> {

    private final StringBuilder textBuilder;
    private int runningMenuIndex;//Used with sequential array menus

    public ScriptMenuBuilder() {
        this.textBuilder = new StringBuilder();
        this.runningMenuIndex = 0;
        resetColorAndStyle();
    }

    public ScriptMenuBuilder get() {
        return this;
    }

    @Override
    public ScriptMenuBuilder append(String str) {
        textBuilder.append(str);
        return this;
    }

    @Override
    public ScriptMenuBuilder append(CharSequence text) {
        textBuilder.append(text);
        return this;
    }

    @Override
    public ScriptMenuBuilder append(Object object) {
        textBuilder.append(object);
        return this;
    }

    @Override
    public ScriptMenuBuilder append(StringBuffer sb) {
        textBuilder.append(sb);
        return this;
    }

    private static boolean isValidOption(String option) {
        return option != null && !option.isEmpty();
    }


    public ScriptMenuBuilder appendMenu(String... options) {
        for(int i = 0; i < options.length; i++) {
            appendMenuItem(runningMenuIndex++, options[i]);
        }
        return this;
    }

    public ScriptMenuBuilder appendMenu(Collection<Tuple<Integer, String>> options) {
        for (Tuple<Integer, String> option : options) {
            appendMenuItem(option.left, option.right);
        }
        return this;
    }

    private ScriptMenuBuilder appendMenuItem(int index, String option) {
        if(isValidOption(option)) {
            textBuilder.append("#L").append(index).append("#").append(option).append("#l\r\n");
        }
        return this;
    }

    public ScriptMenuBuilder appendMenuWith(FontStyle menuStyle, FontColor menuColor, String... options) {
        boolean appendStyle = menuStyle != FontStyle.NONE && menuStyle != this.currentStyle;
        boolean appendColor = menuColor != FontColor.NONE && menuColor != this.currentColor;
        if(appendStyle) {
            textBuilder.append(menuStyle.prefix);
        }
        if(appendColor) {
            textBuilder.append(menuColor.prefix);
        }
        appendMenu(options);
        if(appendStyle) {
            textBuilder.append(getStyleSafe(FontStyle.NORMAL).prefix);
        }
        if(appendColor) {
            textBuilder.append(getColorSafe(FontColor.BLACK).prefix);
        }
        return this;
    }

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
