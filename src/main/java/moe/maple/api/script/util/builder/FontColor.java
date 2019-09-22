package moe.maple.api.script.util.builder;

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