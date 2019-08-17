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

import moe.maple.api.script.model.action.*;
import moe.maple.api.script.model.messenger.*;
import moe.maple.api.script.model.response.ScriptResponse;
import moe.maple.api.script.model.type.SpeakerType;
import moe.maple.api.script.util.builder.ScriptMenuBuilder;
import moe.maple.api.script.util.Tuple;
import moe.maple.api.script.util.With;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ScriptAPI {
    INSTANCE;
    private static final Logger log = LoggerFactory.getLogger( ScriptAPI.class );

    private int defaultSpeakerId;

    private SayMessenger messengerSay;
    private AskYesNoMessenger messengerAskYesNo;
    private AskMenuMessenger messengerAskMenu;

    ScriptAPI() { }

    public void setDefaultSpeakerId(int speakerId) { this.defaultSpeakerId = speakerId; }

    public void setDefaultMessengers() {
        messengerSay = (script, speakerTemplateid, param, message, previous, next) -> {
            log.debug("say-> speaker: {}, param: {}, prev: {}, next: {}, message: \"{}\"", speakerTemplateid, param, previous, next, message);
        };
        messengerAskYesNo = (script, speakerTemplateId, param, message) -> {
           log.debug("askYesNo-> speaker: {}, param: {}, message: \"{}\"", speakerTemplateId, param, message);
        };
        messengerAskMenu = (script, speakerTemplateId, param, message) -> {
            log.debug("askMenu-> speaker: {}, param: {}, message: {}", speakerTemplateId, param, message);
        };
    }

    public void setSayMessenger(SayMessenger msger) { this.messengerSay = msger; }
    public void setMessengerAskYesNo(AskYesNoMessenger msger) { this.messengerAskYesNo = msger; }
    public void setAskMenuMessenger(AskMenuMessenger msger) { this.messengerAskMenu = msger; }


    // =================================================================================================================

    @FunctionalInterface
    public interface BasicActionChain {
        void andThen(BasicScriptAction next);
    }

    @FunctionalInterface
    public interface NumberActionChain {
        void andThen(NumberScriptAction next);
    }

    @FunctionalInterface
    public interface StringActionChain {
        void andThen(StringScriptAction next);
    }

    // Will be called from future object that doesn't need to pass Script
    public static BasicActionChain say(MoeScript script, String... messages) {
        var chain = new LinkedList<ScriptResponse>();
        With.indexAndCount(messages, (msg, idx, ts) -> chain.add((t, a, o) -> {
            var back = idx != 0;
            var forward = ts - 1 >= idx + 1;
            if (t != SpeakerType.SAY) { // Wrong type, b-baka.
                script.end();
            } else {
                var speaker = ScriptAPI.INSTANCE.defaultSpeakerId;
                switch (a.intValue()) {
                    case -1: // Escape
                        script.end();
                    case 0: // Back.
                        if (idx != 0) {
                            var res = chain.get(idx - 1);
                            script.setScriptResponse(res);
                            ScriptAPI.INSTANCE.messengerSay.send(script, speaker, 0, messages[idx - 1], back, forward);
                        } else {
                            log.warn("Tried to go back while on the first message? No! :(");
                            script.end();
                        }
                        break;
                    case 1: // Next.
                        if (ts - 1 >= idx + 1) {
                            var res = chain.get(idx + 1);
                            script.setScriptResponse(res);
                            ScriptAPI.INSTANCE.messengerSay.send(script, speaker, 0, messages[idx - 1], back, forward);
                        } else {
                            script.setScriptResponse(null);
                            script.resume(t, a, o);
                        }
                        break;
                    default:
                        log.warn("Unhandled action({}) for {}", t, a);
                        script.end();
                }
            }
        }));
        script.setScriptResponse(chain.getFirst());

        var speaker = ScriptAPI.INSTANCE.defaultSpeakerId;

        ScriptAPI.INSTANCE.messengerSay.send(script, speaker, 0, messages[0], false, messages.length > 1);

        return script::setScriptAction;
    }

    // =================================================================================================================

    public static void askYesNo(MoeScript script, String message, BasicScriptAction onYes, BasicScriptAction onNo) {
        script.setScriptResponse((t, a, o) -> {
            if (t != SpeakerType.ASKYESNO) {
                script.end();
            } else {
                var an = a.intValue();
                if (an == 1 || an == 0) {
                    var act = an == 0 ? onNo : onYes;
                    script.setScriptResponse(null);
                    script.setScriptAction(act);
                    script.resume(t, a, o);
                } else {
                    script.end();
                }
            }
        });
        var speaker = ScriptAPI.INSTANCE.defaultSpeakerId;

        ScriptAPI.INSTANCE.messengerAskYesNo.send(script, speaker, 0, message);
    }

    public static void askYesNo(MoeScript script, String message, BasicScriptAction onYes) {
        askYesNo(script, message, onYes, script::end);
    }

    // =================================================================================================================

    public static NumberActionChain askMenu(MoeScript script, String prompt, String... menuItems) {
        var builder = new ScriptMenuBuilder();
        builder.append(prompt).appendMenu(menuItems);

        script.setScriptResponse((t, a, o) -> {
            var min = 0;
            var max = menuItems.length - 1;
            var sel = ((Number)o).intValue();
            var bad = sel < min || sel > max;

            if (t != SpeakerType.ASKMENU || bad || a.intValue() != 1) {
                if (bad)
                    log.debug("Value mismatch: min {}, max {}, val {}", min, max, sel);
                script.end();
            } else {
                script.setScriptResponse(null);
                script.resume(t, a, o);
            }
        });

        var speaker = ScriptAPI.INSTANCE.defaultSpeakerId;

        ScriptAPI.INSTANCE.messengerAskMenu.send(script, speaker, 0, builder.toString());

        return script::setScriptAction;
    }

    @SafeVarargs
    public static BasicActionChain askMenu(MoeScript script, String prompt, Tuple<String, BasicScriptAction>... options) {
        var builder = new ScriptMenuBuilder();
        builder.append(prompt).appendMenu(Stream.of(options).map(t -> t.left).collect(Collectors.joining()));

        script.setScriptResponse((t, a, o) -> {
            var min = 0;
            var max = options.length - 1;
            var sel = ((Number)o).intValue();
            var bad = sel < min || sel > max;

            if (t != SpeakerType.ASKMENU || bad || a.intValue() != 1) {
                if (bad)
                    log.debug("Value mismatch: min {}, max {}, val {}", min, max, sel);
                script.end();
            } else {
                script.setScriptResponse(null);
                script.setScriptAction(options[sel].right);
                script.resume(t, a, o);
            }
        });

        var speaker = ScriptAPI.INSTANCE.defaultSpeakerId;

        ScriptAPI.INSTANCE.messengerAskMenu.send(script, speaker, 0, builder.toString());

        return script::setScriptAction;
    }

    // =================================================================================================================
}
