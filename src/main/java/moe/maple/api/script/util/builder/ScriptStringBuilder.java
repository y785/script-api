package moe.maple.api.script.util.builder;

public class ScriptStringBuilder extends StyleAndColorBuilder<ScriptStringBuilder> implements CharacterSequenceBuilder<ScriptStringBuilder>, AppendingBuilder<ScriptStringBuilder> {

    private final StringBuilder textBuilder;

    public ScriptStringBuilder() {
        this.textBuilder = new StringBuilder();
        resetColorAndStyle();
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
}