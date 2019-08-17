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

package moe.maple.api.script.example;

import moe.maple.api.script.model.FunScript;
import moe.maple.api.script.model.ScriptAPI;
import moe.maple.api.script.util.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMenuTupleScript extends FunScript {

    private static final Logger log = LoggerFactory.getLogger( ExampleMenuTupleScript.class );

    @Override
    public void work() {
        ScriptAPI.askMenu(this, "Example Menu Script",
        Tuple.of("Option 1", () -> {
            log.debug("Option 1 met.");
        }), Tuple.of("Option 2", () -> {
            log.debug("Option 2 met.");
        }), Tuple.of("Option 3", () -> {
            log.debug("Option 3 met.");
        }), Tuple.of("Option 4", () -> {
            log.debug("Option 4 met.");
        }), Tuple.of("Option 5", () -> {
            log.debug("Option 5 met.");
        }));
    }
}
