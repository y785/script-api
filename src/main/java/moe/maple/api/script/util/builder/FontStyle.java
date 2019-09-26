package moe.maple.api.script.util.builder;
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
