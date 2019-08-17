package moe.maple.api.script.util;

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

    public ScriptStringBuilder appendMenu(ScriptColor color, String... options) {
        sb.append(color.prefix);
        With.index(options, (s, i) -> {
            sb.append("#L").append(i).append("# ").append(s).append("#l");
        });
        sb.append(this.color.prefix);
        return this;
    }

    public ScriptStringBuilder appendMenu(String... options) {
        return appendMenu(ScriptColor.BLUE, options);
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
