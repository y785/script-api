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

}
