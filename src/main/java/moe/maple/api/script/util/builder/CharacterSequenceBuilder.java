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
interface CharacterSequenceBuilder<Builder extends CharacterSequenceBuilder<Builder>> {

    Builder append(CharSequence text);

    /**
     * Disregard. 4000 IQ plays.
     * @return A Builder.
     */
    Builder get();

    /**
     * @see CharacterSequenceBuilder#tab()
     * @param howMany Appends 'howMany' tabs to the builder.
     * @return A Builder.
     */
    default Builder tab(int howMany) {
        for(int i = 0; i < howMany; i++)
            tab();
        return get();
    }

    /**
     * Appends \t to the builder.
     * @return A Builder.
     */
    default Builder tab() {
        append("\t");
        return get();
    }

    /**
     * @see CharacterSequenceBuilder#newLine()
     * @param howMany Appends 'howMany' newLines to the builder.
     * @return A Builder.
     */
    default Builder newLine(int howMany) {
        for(int i = 0; i < howMany; i++)
            newLine();
        return get();
    }

    /**
     * Appends a '\r\n' to the builder.
     * @return A Builder.
     */
    default Builder newLine() {
        append("\r\n");
        return get();
    }

    /**
     * Appends the System's line separator to the builder.
     * This is '\r\n' on Windows systems.
     * @return A Builder.
     */
    default Builder lineSeparator() {
        append(System.lineSeparator());
        return get();
    }

    /**
     * Appends a '\r' to the builder.
     * @return A Builder.
     */
    default Builder carriageReturn() {
        append("\r");
        return get();
    }

    /**
     * Appends a '\n' to the builder.
     * @return A Builder.
     */
    default Builder lineFeed() {
        append("\n");
        return get();
    }

}
