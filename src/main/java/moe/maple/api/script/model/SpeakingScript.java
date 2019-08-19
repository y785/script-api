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

import moe.maple.api.script.model.action.BasicScriptAction;
import moe.maple.api.script.model.chain.BasicActionChain;
import moe.maple.api.script.model.chain.IntegerActionChain;
import moe.maple.api.script.util.tuple.Tuple;

/**
 * This should mainly be implemented by NPCs
 */
public interface SpeakingScript extends MessagingScript {

    /**
     * Beware, sugar below.
     */

    default BasicActionChain say(String... messages) {
        return ScriptAPI.say(this, messages);
    }

    default BasicActionChain say(String message, Object... objects) {
        return ScriptAPI.say(this, message, objects);
    }

    default BasicActionChain say(int param, String... messages) {
        return ScriptAPI.say(this, param, messages);
    }

    default BasicActionChain say(int speakerTemplateId, int param, String... messages) {
        return ScriptAPI.say(this, speakerTemplateId, param, messages);
    }

    default BasicActionChain say(Tuple<Integer, String>... paramAndMessages) {
        return ScriptAPI.say(this, paramAndMessages);
    }

    default BasicActionChain say(Integer[] speakers, int param, String... messages) {
        return ScriptAPI.say(this, speakers, param, messages);
    }

    default BasicActionChain say(Integer[] speakers, Integer[] params, String... messages) {
        return ScriptAPI.say(this, speakers, params, messages);
    }

    // =================================================================================================================

    default void askYesNo(String message, BasicScriptAction onYes) {
        ScriptAPI.askYesNo(this, message, onYes);
    }

    default void askYesNo(String message, BasicScriptAction onYes, BasicScriptAction onNo) {
        ScriptAPI.askYesNo(this, message, onYes, onNo);
    }

    // =================================================================================================================

    default IntegerActionChain askMenu(int speakerTemplateId, int param, String prompt, String... menuItems) {
        return ScriptAPI.askMenu(this, speakerTemplateId, param, prompt, menuItems);
    }

    default IntegerActionChain askMenu(int speakerTemplateId, String prompt, String... menuItems) {
        return ScriptAPI.askMenu(this, speakerTemplateId, prompt, menuItems);
    }

    default IntegerActionChain askMenu(String prompt, String... menuItems) {
        return ScriptAPI.askMenu(this, prompt, menuItems);
    }

    default IntegerActionChain askMenu(String prompt) {
        return ScriptAPI.askMenu(this, prompt);
    }

    default void askMenu(String prompt, Tuple<String, BasicScriptAction>... options) {
        ScriptAPI.askMenu(this, prompt, options);
    }

    // =================================================================================================================

    default IntegerActionChain askAvatar(int speakerTemplateId, int param, String prompt, int... options) {
        return ScriptAPI.askAvatar(this, speakerTemplateId, param, prompt, options);
    }

    default IntegerActionChain askAvatar(int speakerTemplateId, String prompt, int... options) {
        return ScriptAPI.askAvatar(this, speakerTemplateId, prompt, options);
    }

    default IntegerActionChain askAvatar(String prompt, int... options) {
        return ScriptAPI.askAvatar(this, prompt, options);
    }
}
