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

import moe.maple.api.script.logic.response.SayResponse;
import moe.maple.api.script.model.BaseScript;
import moe.maple.api.script.model.NpcScript;
import moe.maple.api.script.model.Script;
import moe.maple.api.script.logic.ScriptAPI;
import moe.maple.api.script.model.type.ScriptMessageType;
import moe.maple.api.script.util.tuple.Tuple;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
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
        final String SCRIPT_NAME = "ApiSayLogic1";

        @Script(name = SCRIPT_NAME)
        class NpcScriptTest extends NpcScript {
            @Override
            protected void work() {
                say("0", "1", "2").andThen(() -> {
                    log.debug("Beginning sub test 2: Back <-> Forward");
                    say(Tuple.of(0, "3"), Tuple.of(0, "4"), Tuple.of(0, "5")).andThen(() -> {
                        say( "6", "7");
                    });
                });
            }
        }

        var atomicIndex = new AtomicInteger();
        var back = new AtomicInteger();
        var forward = new AtomicInteger();

        forward.set(1);
        back.set(0);
        ScriptAPI.INSTANCE.setMessengerSay((userObject, speakerType, speakerTemplateId, replaceTemplateId, param, message, previous, next) -> {
            var idx = atomicIndex.getAndIncrement();
            log.debug("{}: m {} / p {} / n {}", idx, message, previous, next);

            var ableToGoBack = back.get()  == 1;
            var ableToGoForward = forward.get() == 1;
            assertEquals(ableToGoBack, previous);
            assertEquals(ableToGoForward, next);
        });

        var test = new NpcScriptTest();
        test.setUserObject(new TestUserObject());
        assertEquals(test.name(), SCRIPT_NAME);
        test.start();

        assertTrue(!test.isDone());
        assertTrue(test.isPaused());
        back.set(1);

        test.resume(ScriptMessageType.SAY, SayResponse.NEXT, null);
        forward.set(1); back.set(1);

        test.resume(ScriptMessageType.SAY, SayResponse.NEXT, null);
        forward.set(1); back.set(0);

        test.resume(ScriptMessageType.SAY, SayResponse.NEXT, null);
        forward.set(1); back.set(1);

        test.resume(ScriptMessageType.SAY, SayResponse.NEXT, null);
        forward.set(1); back.set(0);

        test.resume(ScriptMessageType.SAY, SayResponse.PREV, null);
        forward.set(1); back.set(1);
        test.resume(ScriptMessageType.SAY, SayResponse.NEXT, null);

        forward.set(1); back.set(1);
        test.resume(ScriptMessageType.SAY, SayResponse.NEXT, null);

        forward.set(1); back.set(0);
        test.resume(ScriptMessageType.SAY, SayResponse.NEXT, null);
        forward.set(0); back.set(1);
        test.resume(ScriptMessageType.SAY, SayResponse.NEXT, null);
        test.resume(ScriptMessageType.SAY, SayResponse.NEXT, null);

        assertTrue(test.isDone());
        assertTrue(!test.isPaused());

        test.reset();

        assertTrue(!test.isPaused());
        assertTrue(!test.isDone());
    }

    @Test
    public void apiAskMenuLogic() {
        @Script(name = "ApiAskMenu")
        class ApiAskMenuLogic extends BaseScript {
            @Override
            public void work() {
                ScriptAPI.askMenu(this, "Prompt", List.of("Option 1", "Option 2", "Option 3")).andThen(sel -> {
                    assertEquals(sel, 1);
                    ScriptAPI.askMenu(this, "Prompt", List.of(Tuple.of("Option 4", null), Tuple.of("Option 5", () -> {

                    })));
                });
            }
        }

        var test = new ApiAskMenuLogic();
        test.work();

        assertTrue(!test.isDone());
        assertTrue(test.isPaused());

        test.resume(ScriptMessageType.ASKMENU, 1, 1);

        assertTrue(!test.isDone());
        assertTrue(test.isPaused());

        test.resume(ScriptMessageType.ASKMENU, 1, 0);

        assertTrue(test.isDone());
        assertTrue(!test.isPaused());

        test.reset();
        assertTrue(!test.isDone());
        assertTrue(!test.isPaused());

        test.resume(ScriptMessageType.ASKMENU, 1, 1);
        test.resume(ScriptMessageType.ASKMENU, 1, 1);

        assertTrue(test.isDone());
        assertTrue(!test.isPaused());
    }
}
