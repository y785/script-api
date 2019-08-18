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

import moe.maple.api.script.model.action.ScriptAction;
import moe.maple.api.script.model.response.ScriptResponse;
import moe.maple.api.script.model.event.ScriptEvent;
import moe.maple.api.script.model.type.SpeakerType;

public interface MoeScript {

    boolean isDone();
    boolean isPaused();

    void work();

    void start();
    void end();

    /**
     * Resets the script back to its default state.
     */
    void reset();
    default void resetAndStart() { reset(); start(); }

    void resume(SpeakerType type, Number action, Object response);

    // =================================================================================================================

    void setScriptAction(ScriptAction action);
    void setScriptResponse(ScriptResponse response);

    void addStartEvent(ScriptEvent event);
    void addEndEvent(ScriptEvent event);

    // =================================================================================================================

}
