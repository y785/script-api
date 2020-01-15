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
     * @see #setStyle(FontStyle)
     * Sets the {@link FontStyle} to {@link FontStyle#BOLD}
     * @return A builder
     */
    public Builder bold() {
        return setStyle(FontStyle.BOLD);
    }

    /**
     * @see #setColor(FontColor)
     * Sets the {@link FontStyle} to {@link FontStyle#NORMAL}
     * @return A builder
     */
    public Builder normal() {
        return setStyle(FontStyle.NORMAL);
    }

    /**
     * @see #setColor(FontColor)
     * Sets the {@link FontColor} to {@link FontColor#BLACK}
     * @return A builder
     */
    public Builder black() {
        return setColor(FontColor.BLACK);
    }

    /**
     * @see #setColor(FontColor)
     * Sets the {@link FontColor} to {@link FontColor#BLUE}
     * @return A builder
     */
    public Builder blue() {
        return setColor(FontColor.BLUE);
    }

    /**
     * @see #setColor(FontColor)
     * Sets the {@link FontColor} to {@link FontColor#PURPLE}
     * @return A builder
     */
    public Builder purple() {
        return setColor(FontColor.PURPLE);
    }

    /**
     * @see #setColor(FontColor)
     * Sets the {@link FontColor} to {@link FontColor#GREEN}
     * @return A builder
     */
    public Builder green() {
        return setColor(FontColor.GREEN);
    }

    /**
     * @see #setColor(FontColor)
     * Sets the {@link FontColor} to {@link FontColor#RED}
     * @return A builder
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
}
