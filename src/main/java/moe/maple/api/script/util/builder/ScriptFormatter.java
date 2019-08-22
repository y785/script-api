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

import org.slf4j.helpers.MessageFormatter;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *  Generic class for MessageFormatter.
 *  Just because slf4j is a dependency and I trust nothing.
 */
public interface ScriptFormatter<Builder extends CharacterSequenceBuilder<Builder>> extends CharacterSequenceBuilder<Builder>  {

    default Builder appendFormat(String format, Object... objects) {
        append(ScriptFormatter.format(format, objects));
        return get();
    }

    default Builder appendFormat(String format, Object object) {
        append(ScriptFormatter.format(format, object));
        return get();
    }

    static String format(String format, Object... objects) {
        return MessageFormatter.arrayFormat(format, objects).getMessage();
    }

    static String format(String format, Object object) {
        return MessageFormatter.format(format, object).getMessage();
    }


    default Builder appendFormattedNumber(Number number) {
        append(formatWithLocale(number));
        return get();
    }

    NumberFormat commaFormatter = NumberFormat.getNumberInstance(Locale.getDefault());
    /**
     * Formats a number using the server's Locale
     * Ex. (Locale.US) 1000 => 1,000
     * @param hopefullyANumber HOPEFULLY
     * @return A formatted number as a {@link String}
     */
    static String formatWithLocale(Number hopefullyANumber) {
        return commaFormatter.format(hopefullyANumber);
    }

}