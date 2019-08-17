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

package moe.maple.api.script;

import moe.maple.api.script.example.ExampleMenuScript;
import moe.maple.api.script.example.ExampleSayAndAsk;
import moe.maple.api.script.model.ScriptAPI;
import moe.maple.api.script.model.type.SpeakerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger( Main.class );
    public static void main(String... args) {
        // Goals:
        // Non-blocking / Non-locking.
        // Initialize as needed.
        // Moe.

        ScriptAPI.INSTANCE.setDefaultMessengers();

        var script = new ExampleMenuScript();
        script.addStartEvent(a -> log.debug("Starting script! :D"));
        script.addEndEvent(e -> log.debug("Ending script! :("));
        script.start();

        script.resume(SpeakerType.ASKMENU, 1, 2); // Forward!
//        script.resume(SpeakerType.SAY, 0, null); // Back
//        script.resume(SpeakerType.SAY, 1, null); // Forward!
//        script.resume(SpeakerType.SAY, 1, null); // Forward!
//        script.resume(SpeakerType.SAY, 1, null); // Forward!

        script.resume(SpeakerType.ASKYESNO, 1, null);


    }
}
