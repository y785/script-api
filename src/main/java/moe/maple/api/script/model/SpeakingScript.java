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
import moe.maple.api.script.logic.chain.BasicActionChain;
import moe.maple.api.script.logic.chain.IntegerActionChain;
import moe.maple.api.script.logic.chain.StringActionChain;
import moe.maple.api.script.model.helper.Exchange;
import moe.maple.api.script.model.messenger.say.SayMessage;
import moe.maple.api.script.util.Moematter;
import moe.maple.api.script.util.tuple.Tuple;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * This should mainly be implemented by NPCs
 */
public interface SpeakingScript extends MessagingScript {

    default void exchange(BasicScriptAction onTrue, BasicScriptAction onFalse, int money, int itemId, int itemCount) {
        getUserObject().ifPresentOrElse(u -> u.exchange(onTrue, onFalse, money, itemId, itemCount), onFalse::act);
    }

    default void exchange(BasicScriptAction onTrue, String onFalseMessage, int money, int itemId, int itemCount) {
        exchange(onTrue, () -> say(onFalseMessage), money, itemId, itemCount);
    }

    default void exchange(BasicScriptAction onFalse, int money, int itemId, int itemCount) {
        exchange(() -> {}, onFalse, money, itemId, itemCount);
    }

    default void exchange(String onTrueMessage, String onFalseMessage, int money, int itemId, int itemCount) {
        exchange(() -> say(onTrueMessage), () -> say(onFalseMessage), money, itemId, itemCount);
    }

    default void exchange(String onFalseMessage, int money, int itemId, int itemCount) {
        exchange(() -> {}, () -> say(onFalseMessage), money, itemId, itemCount);
    }

    default void exchange(BasicScriptAction onTrue, BasicScriptAction onFalse, Exchange exchange) {
        getUserObject().ifPresentOrElse(u -> u.exchange(onTrue, onFalse, exchange), onFalse::act);
    }

    default void exchange(BasicScriptAction onFalse, Exchange exchange) {
        exchange(() -> { }, onFalse,  exchange);
    }

    default void exchange(String onTrueMessage, String onFalseMessage, Exchange exchange) {
        exchange(() -> say(onTrueMessage), () -> say(onFalseMessage), exchange);
    }

    default void exchange(String onFalseMessage, Exchange exchange) {
        exchange(() -> say(onFalseMessage), exchange);
    }

    default void exchange(String onTrueMessage, BasicScriptAction onFalse, Exchange exchange) {
        exchange(() -> say(onTrueMessage), onFalse, exchange);
    }

    default void exchange(BasicScriptAction onTrue, String onFalseMessage, Exchange exchange) {
        exchange(onTrue, () -> say(onFalseMessage), exchange);
    }

    // =================================================================================================================

    /**
     * Beware, sugar below.
     */

    default BasicActionChain say(String... messages) {
        return ScriptAPI.say(this, 0, List.of(messages));
    }

    default BasicActionChain say(Collection<SayMessage> messages) {
        return ScriptAPI.say(this, messages);
    }

    default BasicActionChain say(Integer[] speakers, Tuple<Integer, String>... paramAndMessages) {
        return say(speakers, List.of(paramAndMessages));
    }

    default BasicActionChain say(Integer[] speakers, List<Tuple<Integer, String>> paramAndMessages) {
        return ScriptAPI.say(this, speakers, paramAndMessages);
    }

    default BasicActionChain say(Tuple<Integer, String>... paramAndMessages) {
        return say(List.of(paramAndMessages));
    }

    default BasicActionChain say(List<Tuple<Integer, String>> paramAndMessages) {
        return ScriptAPI.say(this, paramAndMessages);
    }

    default BasicActionChain say(Integer[] speakers, Integer[] params, String... messages) {
        return ScriptAPI.say(this, speakers, params, List.of(messages));
    }

    default BasicActionChain say(Integer[] speakers, int param, String... messages) {
        return ScriptAPI.say(this, speakers, param, List.of(messages));
    }

    default BasicActionChain say(int speakerTemplateId, int param, String... messages) {
        return ScriptAPI.say(this, speakerTemplateId, param, List.of(messages));
    }

    default BasicActionChain say(int param, String... messages) {
        return ScriptAPI.say(this, param, List.of(messages));
    }

    default BasicActionChain say(String message, Object... objects) {
        return ScriptAPI.say(this, message, objects);
    }

    default BasicActionChain sayf(String message, Object... objects) {
        return ScriptAPI.say(this, Moematter.format(message, objects));
    }

    // =================================================================================================================

    default void askYesNo(String message, BasicScriptAction onYes) {
        ScriptAPI.askYesNo(this, message, onYes);
    }

    default void askYesNo(String message, BasicScriptAction onYes, BasicScriptAction onNo) {
        ScriptAPI.askYesNo(this, message, onYes, onNo);
    }

    default void askYesNo(String message, BasicScriptAction onYes, String... noMsg) {
        ScriptAPI.askYesNo(this, message, onYes, ()->say(noMsg));
    }

    default void askYesNo(String message, BasicScriptAction onYes, Collection<SayMessage> noMsg) {
        ScriptAPI.askYesNo(this, message, onYes, ()->say(noMsg));
    }

    // =================================================================================================================

    default void askAccept(String message, BasicScriptAction onYes) {
        ScriptAPI.askAccept(this, message, onYes);
    }

    default void askAccept(String message, BasicScriptAction onYes, BasicScriptAction onNo) {
        ScriptAPI.askAccept(this, message, onYes, onNo);
    }

    // =================================================================================================================

    default IntegerActionChain askMenu(int speakerTemplateId, int param, String prompt, String... menuItems) {
        return askMenu( speakerTemplateId, param, prompt, List.of(menuItems));
    }

    default IntegerActionChain askMenu(int speakerTemplateId, int param, String prompt, Collection<String> menuItems) {
        return ScriptAPI.askMenu(this, speakerTemplateId, param, prompt, menuItems);
    }

    default IntegerActionChain askMenu(int speakerTemplateId, String prompt, String... menuItems) {
        return askMenu(speakerTemplateId, prompt, List.of(menuItems));
    }

    default IntegerActionChain askMenu(int speakerTemplateId, String prompt, Collection<String> menuItems) {
        return ScriptAPI.askMenu(this, speakerTemplateId, prompt, menuItems);
    }

    default IntegerActionChain askMenu(String prompt, String... menuItems) {
        return askMenu(prompt, List.of(menuItems));
    }

    default IntegerActionChain askMenu(String prompt, Collection<String> menuItems) {
        return ScriptAPI.askMenu(this, prompt, menuItems);
    }

    default IntegerActionChain askMenu(String prompt) {
        return ScriptAPI.askMenu(this, prompt);
    }

    default void askMenu(String prompt, Tuple<String, BasicScriptAction>... options) {
        askMenu(prompt, List.of(options));
    }

    default void askMenu(String prompt, List<Tuple<String, BasicScriptAction>> options) {
        ScriptAPI.askMenu(this, prompt, options);
    }

    // =================================================================================================================

    default IntegerActionChain askAvatar(int speakerTemplateId, int param, String prompt, Integer... options) {
        return askAvatar(speakerTemplateId, param, prompt, List.of(options));
    }

    default IntegerActionChain askAvatar(int speakerTemplateId, int param, String prompt, Collection<Integer> options) {
        return ScriptAPI.askAvatar(this, speakerTemplateId, param, prompt, options);
    }

    default IntegerActionChain askAvatar(int speakerTemplateId, String prompt, Integer... options) {
        return askAvatar(speakerTemplateId, prompt, List.of(options));
    }

    default IntegerActionChain askAvatar(int speakerTemplateId, String prompt, Collection<Integer> options) {
        return ScriptAPI.askAvatar(this, speakerTemplateId, prompt, options);
    }

    default IntegerActionChain askAvatar(String prompt, Integer... options) {
        return askAvatar(prompt, List.of(options));
    }

    default IntegerActionChain askAvatar(String prompt, Collection<Integer> options) {
        return ScriptAPI.askAvatar(this, prompt, options);
    }

    // =================================================================================================================

    default StringActionChain askText(int speakerTemplateId, int param, String message, String defaultText, int min, int max) {
        return ScriptAPI.askText(this, speakerTemplateId, param, message, defaultText, min, max);
    }

    default StringActionChain askText(int param, String message, String defaultText, int min, int max) {
        return ScriptAPI.askText(this, param, message, defaultText, min, max);
    }

    default StringActionChain askText(String message, String defaultText, int min, int max) {
        return ScriptAPI.askText(this, message, defaultText, min, max);
    }

    default StringActionChain askText(String message, String defaultText, int min) {
        return ScriptAPI.askText(this, message, defaultText, min);
    }

    default StringActionChain askText(String message, String defaultText) {
        return ScriptAPI.askText(this, message, defaultText);
    }

    default StringActionChain askText(String message) {
        return ScriptAPI.askText(this, message);
    }

    // =================================================================================================================

    default IntegerActionChain askNumber(int speakerTemplateId, int param, String message, int defaultNumber, int min, int max) {
        return ScriptAPI.askNumber(this, speakerTemplateId, param, message, defaultNumber, min, max);
    }

    default IntegerActionChain askNumber(int param, String message, int defaultNumber, int min, int max) {
        return ScriptAPI.askNumber(this, param, message, defaultNumber, min, max);
    }

    default IntegerActionChain askNumber(String message, int defaultNumber, int min, int max) {
        return ScriptAPI.askNumber(this, message, defaultNumber, min, max);
    }

    default IntegerActionChain askNumber(String message, int defaultNumber, int min) {
        return ScriptAPI.askNumber(this, message, defaultNumber, min);
    }

    default IntegerActionChain askNumber(String message, int defaultNumber) {
        return ScriptAPI.askNumber(this, message, defaultNumber);
    }

    default IntegerActionChain askNumber(String message) {
        return ScriptAPI.askNumber(this, message);
    }
}
