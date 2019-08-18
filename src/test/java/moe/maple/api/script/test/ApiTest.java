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

package moe.maple.api.script.test;

import moe.maple.api.script.model.BaseScript;
import moe.maple.api.script.model.Script;
import moe.maple.api.script.model.ScriptAPI;
import moe.maple.api.script.model.type.SpeakerType;
import moe.maple.api.script.util.tuple.Tuple;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiTest {

    private static final Logger log = LoggerFactory.getLogger( ApiTest.class );

    @BeforeAll
    public static void beforeall() {

    }

    @BeforeEach
    public void beforeEach() {
        ScriptAPI.INSTANCE.setDefaultMessengers();
    }

    @Test
    public void apiSayLogic() {
        class ApiSayLogic1 extends BaseScript {
            @Override
            @Script(name = "ApiSayLogic1")
            public void work() {
                ScriptAPI.say(this, "0", "1", "2").andThen(() -> {
                    log.debug("Beginning sub test 2: Back <-> Forward");
                    ScriptAPI.say(this, Tuple.of(0, "3"), Tuple.of(0, "4"), Tuple.of(0, "5")).andThen(() -> {
                        ScriptAPI.say(this, "6");
                    });
                });
            }
        }

        var atomicIndex = new AtomicInteger();
        var back = new AtomicInteger();
        var forward = new AtomicInteger();

        forward.set(1);
        back.set(0);

        ScriptAPI.INSTANCE.setSayMessenger((script, message, speakerTemplateId, param, previous, next) -> {
            var idx = atomicIndex.getAndIncrement();
            log.debug("{}: idx {} / p {} / n {}", idx, message, previous, next);

            var ableToGoBack = back.get()  == 1;
            var ableToGoForward = forward.get() == 1;
            assertEquals(ableToGoBack, previous);
            assertEquals(ableToGoForward, next);
        });

        var test = new ApiSayLogic1();
        test.work();

        assertTrue(!test.isDone());
        assertTrue(test.isPaused());

        back.set(1);
        test.resume(SpeakerType.SAY, 1, null); forward.set(0); back.set(1);
        test.resume(SpeakerType.SAY, 1, null); forward.set(1); back.set(0);

        test.resume(SpeakerType.SAY, 1, null); forward.set(1); back.set(1);
        test.resume(SpeakerType.SAY, 1, null); forward.set(1); back.set(0);
        test.resume(SpeakerType.SAY, 0, null); forward.set(1); back.set(1);
        test.resume(SpeakerType.SAY, 1, null); forward.set(0); back.set(1);
        test.resume(SpeakerType.SAY, 1, null); forward.set(0); back.set(0);
        test.resume(SpeakerType.SAY, 1, null);
        test.resume(SpeakerType.SAY, 1, null);

        assertTrue(test.isDone());
        assertTrue(!test.isPaused());

        test.reset();

        assertTrue(!test.isPaused());
        assertTrue(!test.isDone());
    }

    @Test
    public void apiAskMenuLogic() {
        class ApiAskMenuLogic extends BaseScript {
            @Override
            @Script(name = "ApiAskMenu")
            public void work() {
                ScriptAPI.askMenu(this, "Prompt", "Option 1", "Option 2", "Option 3").andThen(sel -> {
                    assertEquals(sel, 1);
                    ScriptAPI.askMenu(this, "Prompt", Tuple.of("Option 4", null), Tuple.of("Option 5", () -> {

                    }));
                });
            }
        }

        var test = new ApiAskMenuLogic();
        test.work();

        assertTrue(!test.isDone());
        assertTrue(test.isPaused());

        test.resume(SpeakerType.ASKMENU, 1, 1);

        assertTrue(!test.isDone());
        assertTrue(test.isPaused());

        test.resume(SpeakerType.ASKMENU, 1, 0);

        assertTrue(test.isDone());
        assertTrue(!test.isPaused());

        test.reset();
        assertTrue(!test.isDone());
        assertTrue(!test.isPaused());

        test.resume(SpeakerType.ASKMENU, 1, 1);
        test.resume(SpeakerType.ASKMENU, 1, 1);

        assertTrue(test.isDone());
        assertTrue(!test.isPaused());
    }
}