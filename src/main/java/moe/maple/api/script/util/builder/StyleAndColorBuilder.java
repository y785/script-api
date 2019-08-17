package moe.maple.api.script.util.builder;

/**
 * @author umbreon22
 * @param <Builder> A Builder class
 */
public abstract class StyleAndColorBuilder<Builder extends StyleAndColorBuilder<Builder>> {

    protected FontStyle currentStyle;
    protected FontColor currentColor;

    /**
     * Sets the {@link FontColor} and {@link FontStyle} to their respective 'NONE'.
     * @return A Builder
     */
    Builder resetColorAndStyle() {
        this.currentColor = FontColor.NONE;
        this.currentStyle = FontStyle.NONE;
        return get();
    }

    /**
     * A safe way of retrieving a {@link FontColor}.
     * @param def A default value.
     * @return The current color, or def.
     */
    protected FontColor getColorSafe(FontColor def) {
        return currentColor == null || currentColor == FontColor.NONE ? def : this.currentColor;
    }

    /**
     * A safe way of retrieving a {@link FontStyle}.
     * @param def A default value.
     * @return The current style, or def.
     */
    protected FontStyle getStyleSafe(FontStyle def) {
        return currentStyle == null || currentStyle == FontStyle.NONE ? def : this.currentStyle;
    }

    /**
     * @see {@link #setStyle(FontStyle)}
     * Sets the {@link FontStyle} to {@link FontStyle#BOLD}
     */
    public Builder bold() {
        return setStyle(FontStyle.BOLD);
    }

    /**
     * @see {@link #setColor(FontColor)}
     * Sets the {@link FontStyle} to {@link FontStyle#NORMAL}
     */
    public Builder normal() {
        return setStyle(FontStyle.NORMAL);
    }

    /**
     * @see {@link #setColor(FontColor)}
     * Sets the {@link FontColor} to {@link FontColor#BLACK}
     */
    public Builder black() {
        return setColor(FontColor.BLACK);
    }

    /**
     * @see {@link #setColor(FontColor)}
     * Sets the {@link FontColor} to {@link FontColor#BLUE}
     */
    public Builder blue() {
        return setColor(FontColor.BLUE);
    }

    /**
     * @see {@link #setColor(FontColor)}
     * Sets the {@link FontColor} to {@link FontColor#PURPLE}
     */
    public Builder purple() {
        return setColor(FontColor.PURPLE);
    }

    /**
     * @see {@link #setColor(FontColor)}
     * Sets the {@link FontColor} to {@link FontColor#GREEN}
     */
    public Builder green() {
        return setColor(FontColor.GREEN);
    }

    /**
     * @see {@link #setColor(FontColor)}
     * Sets the {@link FontColor} to {@link FontColor#RED}
     */
    public Builder red() {
        return setColor(FontColor.RED);
    }

    /**
     * Sets the current {@link FontColor} and appends it's {@link FontColor#prefix}.
     * @param color A valid client color prefix.
     * @return A Builder
     */
    public Builder setColor(FontColor color) {
        this.currentColor = color;
        if(currentColor != FontColor.NONE) append(color.prefix);
        return get();
    }

    /**
     * Sets the current {@link FontStyle} and appends it's {@link FontStyle#prefix}.
     * @param style A valid client color prefix.
     * @return A Builder
     */
    public Builder setStyle(FontStyle style) {
        this.currentStyle = style;
        if(currentStyle != FontStyle.NONE) append(style.prefix);
        return get();
    }

    /**
     * Appends text, duh.
     * @param text A sequence of characters, probably.
     * @return A Builder
     */
    public abstract Builder append(CharSequence text);

    /**
     * 4000 IQ plays here.
     * @return this
     */
    public abstract Builder get();

    /**
     * @author umbreon22
     * String prefixes that adjust the style of client text.
     */
    public enum FontStyle {
        NONE(""),
        NORMAL("#n"),
        BOLD("#e");
        final String prefix;
        FontStyle(String style) {
            this.prefix = style;
        }

        /**
         * Converts a Client Prefix into a Style
         * @param p (Ex. #e)
         * @return Color (Ex. would return BOLD)
         */
        public static FontStyle fromPrefix(String p) {
            for(FontStyle style : values())
                if(style.prefix.equals(p)) return style;
            return FontStyle.NONE;
        }
    }

    /**
     * @author umbreon22
     * String prefixes that adjust the color of client text.
     */
    public enum FontColor {
        NONE(""),
        BLACK("#k"),
        BLUE("#b"),
        RED("#r"),
        PURPLE("#d"),
        GREEN("#g");
        final String prefix;
        FontColor(String color) {
            this.prefix = color;
        }

        /**
         * Converts a Client Prefix into a Color
         * @param p (Ex. #k)
         * @return Color (Ex. would return BLACK)
         */
        public static FontColor fromPrefix(String p) {
            for(FontColor color : values())
                if(color.prefix.equals(p)) return color;
            return FontColor.NONE;
        }

    }
}
