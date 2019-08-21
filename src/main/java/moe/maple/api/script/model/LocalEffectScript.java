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

import moe.maple.api.script.util.builder.ScriptFormatter;
import org.slf4j.helpers.MessageFormatter;

/**
 * Local User Effects, Field Effects, etc.
 */
public interface LocalEffectScript extends MoeScript {

    default void avatarOriented(String path, int durationInSeconds) {
        ScriptAPI.userAvatarOriented(this, path, durationInSeconds);
    }

    default void playPortalSE() {
        ScriptAPI.userPlayPortalSE(this);
    }

    default void reservedEffect(String path) {
        ScriptAPI.userReservedEffect(this, path);
    }

    // =================================================================================================================

    default void fieldObject(String path) {
        ScriptAPI.fieldEffectScreen(this, path);
    }

    default void fieldScreen(String path) {
        ScriptAPI.fieldEffectScreen(this, path);
    }

    default void fieldScreen(String format, Object... objects) {
        ScriptAPI.fieldEffectScreen(this, ScriptFormatter.format(format, objects));
    }

    default void fieldSound(String path) {
        ScriptAPI.fieldEffectSound(this, path);
    }

    default void fieldSound(String format, Object... objects) {
        ScriptAPI.fieldEffectSound(this, ScriptFormatter.format(format, objects));
    }

    default void fieldTremble(int type, int delay) {
        ScriptAPI.fieldEffectTremble(this, type, delay);
    }
}
