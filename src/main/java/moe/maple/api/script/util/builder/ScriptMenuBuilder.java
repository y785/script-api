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

import moe.maple.api.script.util.tuple.Tuple;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author umbreon22
 * A builder for AskMenu.
 */

public class ScriptMenuBuilder<Builder extends ScriptMenuBuilder<Builder>> extends StyleAndColorBuilder<Builder> implements CharacterSequenceBuilder<Builder>, AppendingBuilder<Builder>, ScriptFormatter<Builder> {

    private final StringBuilder textBuilder;
    private int runningMenuIndex;//Used with sequential array menus

    public ScriptMenuBuilder(StringBuilder builder) {
        this.textBuilder = builder;
        this.runningMenuIndex = 0;
        resetColorAndStyle();
    }

    public ScriptMenuBuilder() {
        this(new StringBuilder());
    }

    public Builder get() {
        return (Builder) this;
    }

    @Override
    public Builder append(String str) {
        textBuilder.append(str);
        return get();
    }

    @Override
    public Builder append(CharSequence text) {
        textBuilder.append(text);
        return get();
    }

    @Override
    public Builder append(Object object) {
        textBuilder.append(object);
        return get();
    }

    @Override
    public Builder append(StringBuffer sb) {
        textBuilder.append(sb);
        return get();
    }

    private static boolean isValidOption(String option) {
        return option != null && !option.isEmpty();
    }


    public Builder appendMenu(String... options) {
        for(int i = 0; i < options.length; i++) {
            appendMenuItem(runningMenuIndex++, options[i]);
        }
        return get();
    }

    public Builder appendMenu(Iterable<String> options) {
        for(String option : options) {
            appendMenuItem(runningMenuIndex++, option);
        }
        return get();
    }

    public Builder appendMenu(Collection<Tuple<Integer, String>> options) {
        for (Tuple<Integer, String> option : options) {
            appendMenuItem(option.left(), option.right());
        }
        return get();
    }

    public <T> Builder appendMenu(Function<T, String> formatter, T... options) {
        for (T obj : options) {
            appendMenuItem(runningMenuIndex++, formatter.apply(obj));
        }
        return get();
    }

    public <T> Builder appendMenu(Function<T, String> formatter, Iterable<T> options) {
        for (T obj : options) {
            appendMenuItem(runningMenuIndex++, formatter.apply(obj));
        }
        return get();
    }

    private Builder appendMenuItem(int index, String option) {
        if(isValidOption(option)) {
            textBuilder.append("#L").append(index).append("#").append(option).append("#l\r\n");
        }
        return get();
    }

    public Builder appendMenuWith(FontStyle menuStyle, FontColor menuColor, String... options) {
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
        return get();
    }

    public String build() {
        if(textBuilder.length() == 0) {
            return "Invalid input.";
        }
        return textBuilder.toString();
    }

    public static int parseMenuIndex(String menuLine) throws IllegalArgumentException {
        if(menuLine == null || menuLine.isEmpty()) throw new IllegalArgumentException("Cannot parse empty strings");
        int before = menuLine.indexOf("#L");
        if(before < 0) throw new IllegalArgumentException("Missing starting #L");
        else before+=2;//Skipping #L in substring

        int mid = menuLine.indexOf("#", before);
        if(mid < before) throw new IllegalArgumentException("Missing middle #");

        int after = menuLine.indexOf("#l", mid);
        if(after < mid) throw new IllegalArgumentException("Missing closing #l");

        return Integer.parseInt(menuLine.substring(before, mid));
    }

    /**
     * Simple regex pattern to grab everything between #L and #l.
     * @param fullMenuString Ex. "#L100# test #l\r\n#L101# test2 #l"
     * @return The client's expected menu index texts as an array
     */
    private static Pattern menuIndexPattern = Pattern.compile("#L+\\d[^\"]+?#l", Pattern.MULTILINE);
    public static List<String> matchIndices(String fullMenuString) {
        var matcher = menuIndexPattern.matcher(fullMenuString);
        var list = new LinkedList<String>();
        while(matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }


    public static boolean containsMenuIndex(String menuLine) {
        int before = menuLine.indexOf("#L");
        if(before < 0) return false;
        int mid = menuLine.indexOf("#", before);
        if(mid < 0) return false;
        int after = menuLine.indexOf("#l", mid);
        if(after < 0) return false;
        return after - mid - before > 0;
    }

    @Override
    public String toString() {
        return build();
    }
}
