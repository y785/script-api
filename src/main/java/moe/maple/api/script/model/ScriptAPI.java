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
import moe.maple.api.script.util.tuple.ImmutableTuple;
import moe.maple.api.script.util.tuple.Tuple;
import moe.maple.api.script.util.With;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ScriptAPI {
    INSTANCE;
    private static final Logger log = LoggerFactory.getLogger( ScriptAPI.class );

    private int defaultSpeakerId;

    private SayMessenger messengerSay;
    private AskYesNoMessenger messengerAskYesNo;
    private AskMenuMessenger messengerAskMenu;
    private AskAvatarMessenger messengerAskAvatar;

    ScriptAPI() { }

    public void setDefaultSpeakerId(int speakerId) { this.defaultSpeakerId = speakerId; }

    private int getSpeakerIdFromScript(MoeScript script) {
        // todo check if script has a default speakerId
        return ScriptAPI.INSTANCE.defaultSpeakerId;
    }

    public void setDefaultMessengers() {
        messengerSay = (script, message, speakerTemplateId, param, previous, next) -> {
            log.debug("say-> speaker: {}, param: {}, prev: {}, next: {}, message: \"{}\"", speakerTemplateId, param, previous, next, message);
        };
        messengerAskYesNo = (script, message, speakerTemplateId, param) -> {
           log.debug("askYesNo-> speaker: {}, param: {}, message: \"{}\"", speakerTemplateId, param, message);
        };
        messengerAskMenu = (script, message, speakerTemplateId, param) -> {
            log.debug("askMenu-> speaker: {}, param: {}, message: {}", speakerTemplateId, param, message);
        };
    }

    public void setSayMessenger(SayMessenger msger) { this.messengerSay = msger; }
    public void setMessengerAskYesNo(AskYesNoMessenger msger) { this.messengerAskYesNo = msger; }
    public void setAskMenuMessenger(AskMenuMessenger msger) { this.messengerAskMenu = msger; }
    public void setAskAvatarMessenger(AskAvatarMessenger msger) { this.messengerAskAvatar = msger; }


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

    // =================================================================================================================

    private static ScriptResponse sayResponse(MoeScript script, List<ScriptResponse> chain, Integer speakerTemplateId, Integer param, String message, Integer idx, Integer ts) {
        return (t, a, o) -> {
            if (t != SpeakerType.SAY) { // Wrong type, b-baka.
                script.end();
            } else {
                switch (a.intValue()) {
                    case -1: // Escape
                        script.end();
                    case 0: // Back.
                        if (idx != 0) {
                            var back = idx-1 != 0;
                            var res = chain.get(idx - 1);
                            script.setScriptResponse(res);
                            ScriptAPI.INSTANCE.messengerSay.send(script, message, speakerTemplateId, param, back, true);
                        } else {
                            log.warn("Tried to go back while on the first message? No! :(");
                            script.end();
                        }
                        break;
                    case 1: // Next.
                        if (ts - 1 >= idx + 1) {
                            var forward = ts - 1 > idx + 1;
                            var res = chain.get(idx + 1);
                            script.setScriptResponse(res);
                            ScriptAPI.INSTANCE.messengerSay.send(script, message, speakerTemplateId, param, true, forward);
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
        };
    }

    public static BasicActionChain say(MoeScript script, Tuple<Integer, String>... paramAndMessages) {
        script.setScriptAction(null);

        var chain = new LinkedList<ScriptResponse>();

        With.indexAndCount(paramAndMessages, (tpl, idx, ts) -> {
            chain.add(sayResponse(script, chain, ScriptAPI.INSTANCE.getSpeakerIdFromScript(script), tpl.left(), tpl.right(), idx, ts));
        });
        script.setScriptResponse(chain.getFirst());

        var speaker = ScriptAPI.INSTANCE.getSpeakerIdFromScript(script);
        var param = paramAndMessages[0].left();
        var message = paramAndMessages[0].right();

        ScriptAPI.INSTANCE.messengerSay.send(script, message, speaker, param, false, paramAndMessages.length > 1);

        return script::setScriptAction;
    }

    public static BasicActionChain say(MoeScript script, Integer[] speakers, Integer[] params, String... messages) {
        script.setScriptAction(null);

        var chain = new LinkedList<ScriptResponse>();

        // To be honest, this isn't really needed. // todo validate
        With.index(speakers, (i, idx) -> {
            if (i == 0)
                speakers[idx] = ScriptAPI.INSTANCE.getSpeakerIdFromScript(script);
        });
        With.indexAndCount(messages, (msg, idx, ts) -> chain.add(sayResponse(script, chain, speakers[idx], params[idx], msg, idx, ts)));
        script.setScriptResponse(chain.getFirst());

        var speaker = speakers[0];
        var param = params[0];
        var message = messages[0];

        ScriptAPI.INSTANCE.messengerSay.send(script, message, speaker, param, false, messages.length > 1);

        return script::setScriptAction;
    }

    public static BasicActionChain say(MoeScript script, Integer[] speakers, int param, String... messages) {
        Integer[] params = new Integer[messages.length];
        Arrays.fill(params, param);
        return say(script, speakers, params, messages);
    }

    public static BasicActionChain say(MoeScript script, int speakerTemplateId, int param, String... messages) {
        Integer[] speakers = new Integer[messages.length];
        Integer[] params = new Integer[messages.length];
        Arrays.fill(speakers, speakerTemplateId);
        Arrays.fill(params, param);
        return say(script, speakers, params, messages);
    }

    public static BasicActionChain say(MoeScript script, int param, String... messages) {
        script.setScriptAction(null);
        var chain = new LinkedList<ScriptResponse>();
        With.indexAndCount(messages, (msg, idx, ts) -> {
            chain.add(sayResponse(script, chain, ScriptAPI.INSTANCE.getSpeakerIdFromScript(script), param, msg, idx, ts));
        });
        script.setScriptResponse(chain.getFirst());

        var speaker = ScriptAPI.INSTANCE.getSpeakerIdFromScript(script);
        var message = messages[0];

        ScriptAPI.INSTANCE.messengerSay.send(script, message, speaker, param, false, messages.length > 1);

        return script::setScriptAction;
    }

    public static BasicActionChain say(MoeScript script, String... messages) {
        return say(script, 0, messages);
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
        var speaker = ScriptAPI.INSTANCE.getSpeakerIdFromScript(script);

        ScriptAPI.INSTANCE.messengerAskYesNo.send(script, message, speaker, 0);
    }

    public static void askYesNo(MoeScript script, String message, BasicScriptAction onYes) {
        askYesNo(script, message, onYes, script::end);
    }

    // =================================================================================================================

    private static ScriptResponse askMenuResponse(MoeScript script, String... menuItems) {
        return (t, a, o) -> {
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
        };
    }

    public static NumberActionChain askMenu(MoeScript script, int speakerTemplateId, int param, String prompt, String... menuItems) {
        var builder = new ScriptMenuBuilder();
        builder.append(prompt).appendMenu(menuItems);

        script.setScriptResponse(askMenuResponse(script, menuItems));

        ScriptAPI.INSTANCE.messengerAskMenu.send(script, builder.toString(), speakerTemplateId, param);

        return script::setScriptAction;
    }

    public static NumberActionChain askMenu(MoeScript script, int speakerTemplateId, String prompt, String... menuItems) {
        return askMenu(script, speakerTemplateId, 0, prompt, menuItems);
    }

    public static NumberActionChain askMenu(MoeScript script, String prompt, String... menuItems) {
        return askMenu(script, ScriptAPI.INSTANCE.getSpeakerIdFromScript(script), prompt, menuItems);
    }

    @SafeVarargs
    public static BasicActionChain askMenu(MoeScript script, String prompt, Tuple<String, BasicScriptAction>... options) {
        var builder = new ScriptMenuBuilder();
        builder.append(prompt).appendMenu(Stream.of(options).map(Tuple::left).collect(Collectors.joining()));

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
                script.setScriptAction(options[sel].right());
                script.resume(t, a, o);
            }
        });

        var speaker = ScriptAPI.INSTANCE.getSpeakerIdFromScript(script);

        ScriptAPI.INSTANCE.messengerAskMenu.send(script, builder.toString(), speaker, 0);

        return script::setScriptAction;
    }

    // =================================================================================================================

    private static ScriptResponse askAvatarResponse(MoeScript script, int... options) {
        return (t, a, o) -> {
            var min = 0;
            var max = options.length - 1;
            var sel = ((Number)o).intValue();
            var bad = sel < min || sel > max;

            if (t != SpeakerType.ASKAVATAR || bad || a.intValue() != 1) {
                if (bad)
                    log.debug("Value mismatch: min {}, max {}, val {}", min, max, sel);
                script.end();
            } else {
                script.setScriptResponse(null);
                script.resume(t, a, o);
            }
        };
    }

    public static NumberActionChain askAvatar(MoeScript script, int speakerTemplateId, int param, String prompt, int... options) {

        script.setScriptResponse(askAvatarResponse(script, options));

        ScriptAPI.INSTANCE.messengerAskAvatar.send(script, prompt, speakerTemplateId, param, options);

        return script::setScriptAction;
    }

    public static NumberActionChain askAvatar(MoeScript script, int speakerTemplateId, String prompt, int... options) {
        return askAvatar(script, speakerTemplateId, 0, prompt, options);
    }

    public static NumberActionChain askAvatar(MoeScript script, String prompt, int... options) {
        return askAvatar(script, ScriptAPI.INSTANCE.getSpeakerIdFromScript(script), prompt, options);
    }

    // =================================================================================================================
}
