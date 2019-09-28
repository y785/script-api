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

package moe.maple.api.script.model;

import moe.maple.api.script.logic.ScriptAPI;
import moe.maple.api.script.logic.action.BasicScriptAction;
import moe.maple.api.script.model.helper.Exchange;
import moe.maple.api.script.util.Moematter;
import moe.maple.api.script.util.tuple.Tuple;

public interface MessagingScript extends LocalEffectScript {

    /*
     * Think balloons and messages
     */

    default void message(int type, String message) {
        ScriptAPI.message(this, type, message);
    }

    default void message(String message) {
        message(Constants.MESSAGE_DEFAULT_TYPE, message);
    }

    default void message(String message, Object... objects) {
        message(Constants.MESSAGE_DEFAULT_TYPE, message, objects);
    }
    default void message(int type, String message, Object... objects) {
        message(type, Moematter.format(message, objects));
    }

    default void balloon(int width, int timeoutInSeconds, String message) {
        ScriptAPI.balloon(this, width, timeoutInSeconds, message);
    }

    default void balloon(int width, int timeoutInSeconds, String message, Object... objects) {
        balloon(width, timeoutInSeconds, Moematter.format(message, objects));
    }

    default void balloon(int width, String message) {
        balloon(width, Constants.BALLOON_DEFAULT_TIMEOUT, message);
    }

    default void balloon(int width, String message, Object... objects) {
        balloon(width, Moematter.format(message, objects));
    }

    default void balloon(String message, Object... objects) {
        balloon(Constants.BALLOON_DEFAULT_WIDTH, Moematter.format(message, objects));
    }

    default void balloon(String message) {
        balloon(Constants.BALLOON_DEFAULT_WIDTH, message);
    }

    default void statChanged(boolean exclRequest) {
        ScriptAPI.statChanged(this, exclRequest);
    }

    class Constants {
        private final static int MESSAGE_DEFAULT_TYPE = 0x5;

        private final static int BALLOON_DEFAULT_WIDTH = 200;
        private final static int BALLOON_DEFAULT_TIMEOUT = 5;
    }
}
