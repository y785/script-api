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
import moe.maple.api.script.model.chain.BasicActionChain;
import moe.maple.api.script.model.chain.IntegerActionChain;
import moe.maple.api.script.model.chain.StringActionChain;
import moe.maple.api.script.model.messenger.*;
import moe.maple.api.script.model.messenger.ask.*;
import moe.maple.api.script.model.messenger.effect.field.FieldObjectMessenger;
import moe.maple.api.script.model.messenger.effect.field.FieldScreenMessenger;
import moe.maple.api.script.model.messenger.effect.field.FieldSoundMessenger;
import moe.maple.api.script.model.messenger.effect.field.FieldTrembleMessenger;
import moe.maple.api.script.model.messenger.effect.uel.AvatarOrientedMessenger;
import moe.maple.api.script.model.messenger.effect.uel.PlayPortalSEMessenger;
import moe.maple.api.script.model.messenger.effect.uel.ReservedEffectMessenger;
import moe.maple.api.script.model.messenger.misc.StatChangedMessenger;
import moe.maple.api.script.model.messenger.say.SayImageMessenger;
import moe.maple.api.script.model.messenger.say.SayMessenger;
import moe.maple.api.script.model.response.ScriptResponse;
import moe.maple.api.script.model.type.ScriptMessageType;
import moe.maple.api.script.util.builder.ScriptFormatter;
import moe.maple.api.script.util.builder.ScriptMenuBuilder;
import moe.maple.api.script.util.tuple.Tuple;
import moe.maple.api.script.util.With;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum ScriptAPI {
    INSTANCE;
    private static final Logger log = LoggerFactory.getLogger( ScriptAPI.class );

    private AskAcceptMessenger messengerAskAccept;
    private AskAvatarMessenger messengerAskAvatar;
    private AskBoxTextMessenger messengerAskBoxText;
    private AskMemberShopAvatar messengerAskMemberShopAvatar;
    private AskMenuMessenger messengerAskMenu;
    private AskNumberMessenger messengerAskNumber;
    private AskQuizMessenger messengerAskQuiz;
    private AskSlideMenuMessenger messengerAskSlideMenu;
    private AskSpeedQuizMessenger messengerAskSpeedQuiz;
    private AskTextMessenger messengerAskText;
    private AskYesNoMessenger messengerAskYesNo;
    private SayImageMessenger messengerSayImage;
    private SayMessenger messengerSay;

    // =================================================================================================================

    private MessageMessenger messengerMessage;
    private BalloonMessenger messengerBalloon;
    private ProgressMessenger messengerProgress;
    private StatChangedMessenger messengerStatChanged;

    // =================================================================================================================

    /*
     * Field Effects
     */

    private FieldObjectMessenger messengerFieldObject;
    private FieldScreenMessenger messengerFieldScreen;
    private FieldSoundMessenger messengerFieldSound;
    private FieldTrembleMessenger messengerFieldTremble;

    // =================================================================================================================

    /*
     * User Effect Locals
     */

    private AvatarOrientedMessenger messengerAvatarOriented;
    private PlayPortalSEMessenger messengerPlayPortalSE;
    private ReservedEffectMessenger messengerReservedEffect;


    ScriptAPI() { }

    public void setDefaultMessengers() {
        // Basic script messages
        messengerAskAccept = (userObject, speakerType, speakerTemplateId, param, message)
                -> log.debug("askAccept-> speakerType: {}, speakerTemplate: {}, param: {}, message: \"{}\"", speakerType, speakerTemplateId, param, message);
        messengerAskAvatar = (userObject, speakerType, speakerTemplateId, param, message, options)
                -> log.debug("askAvatar-> speakerType: {}, speakerTemplate: {}, param: {}, message: \"{}\", options: {}", speakerType, speakerTemplateId, param, message, options);
        messengerAskBoxText = (userObject, speakerType, speakerTemplateId, param, message, defaultText, column, row)
                -> log.debug("askBoxText-> speakerType: {}, speakerTemplate: {}, param: {}, column: {}, row: {}, default: \"{}\", message: \"{}\"", speakerType, speakerTemplateId, param, column, row, defaultText, message);
        messengerAskMemberShopAvatar = (userObject, speakerType, speakerTemplateId, param, message, options)
                -> log.debug("askMemberShopAvatar-> speakerType: {}, speakerTemplate: {}, param: {}, message: \"{}\", options: {}", speakerType, speakerTemplateId, param, message, options);
        messengerAskMenu = (userObject, speakerType, speakerTemplateId, param, message)
                -> log.debug("askMenu-> speakerType: {}, speakerTemplate: {}, param: {}, message: \"{}\"", speakerType, speakerTemplateId, param, message);
        messengerAskNumber = (userObject, speakerType, speakerTemplateId, param, message, defaultNumber, min, max)
                -> log.debug("askNumber-> speakerType: {}, speakerTemplate: {}, param: {}, min: {}, max: {}, default: {}, message: \"{}\"", speakerType, speakerTemplateId, param, min, max, defaultNumber, message);
        messengerAskQuiz = (userObject, speakerType, speakerTemplateId, param, title, problemText, hintText, min, max, remainInitialQuiz)
                -> log.debug("askQuiz-> speakerType: {}, speakerTemplate: {}, param: {}, min: {}, max: {}, remain: {}, title: \"{}\", problem: \"{}\", hint: \"{}\"", speakerType, speakerTemplateId, param, min, max, remainInitialQuiz, title, problemText, hintText);
        messengerAskSlideMenu = (userObject, speakerType, speakerTemplateId, slideDlgEX, index, message)
                -> log.debug("askSlideMenu-> speakerType: {}, speakerTemplate: {}, slideDlgEX: {}, index: {}, message: \"{}\"", speakerType, speakerTemplateId, slideDlgEX, index, message);
        messengerAskSpeedQuiz = (userObject, speakerType, speakerTemplateId, param, type, answer, correct, remaining, remainInitialQuiz, title, problemText, hintText, min, max)
                -> log.debug("askSpeedQuiz-> speakerType: {}, speakerTemplate: {}, param: {}, type: {}, answer: {}, correct: {}, remaining: {}, remain: {}, title: \"{}\", problem: \"{}\", hint: \"{}\", min: {}, max: {}",  speakerType, speakerTemplateId, param, type, answer, correct, remaining, remainInitialQuiz, title, problemText, hintText, min, max);
        messengerAskText = (userObject, speakerType, speakerTemplateId, param, message, defaultText, min, max)
                -> log.debug("askText-> speakerType: {}, speakerTemplate: {}, param: {}, min: {}, max: {}, default: \"{}\", message: \"{}\"", speakerType, speakerTemplateId, param, min, max, defaultText, message);
        messengerAskYesNo = (userObject, speakerType, speakerTemplateId, param, message)
                -> log.debug("askYesNo-> speakerType: {}, speakerTemplate: {}, param: {}, message: \"{}\"", speakerType, speakerTemplateId, param, message);
        messengerSayImage = (userObject, speakerType, speakerTemplateId, param, imagePath)
                -> log.debug("sayImage-> speakerType: {}, speakerTemplate: {}, param: {}, path: {}", speakerType, speakerTemplateId, param, imagePath);
        messengerSay = (userObject, speakerType, speakerTemplateId, replaceTemplateId,  param, message, previous, next)
                -> log.debug("say-> speakerType: {}, speakerTemplate: {}, param: {}, prev: {}, next: {}, message: \"{}\"", speakerType, speakerTemplateId, param, message, previous, next);

        // Misc Packets
        messengerMessage = ((userObject, type, message) -> log.debug("message-> type: {}, message: \"{}\"", type, message));
        messengerBalloon = ((userObject, message, width, timeoutInSeconds) -> log.debug("balloon-> width: {}, timeout: {}, message: \"{}\"", width, timeoutInSeconds, message));
        messengerProgress= ((userObject, message) -> log.debug("progress-> message:  \"{}\"", message));
        messengerStatChanged = (userObject, exclRequest) -> log.debug("statChanged-> excl: {}", exclRequest);

        // Field Effects
        messengerFieldObject = (userObject, path) -> log.debug("fieldObject-> path: {}", path);
        messengerFieldScreen = (userObject, path) -> log.debug("fieldScreen-> path: {}", path);
        messengerFieldSound = (userObject, path) -> log.debug("fieldSound-> path: {}", path);
        messengerFieldTremble = (userObject, type, delay) -> log.debug("fieldTremble-> type: {}, delay: {}", type, delay);

        // User Local Effects
        messengerAvatarOriented = (userObject, path, durationInSeconds) -> log.debug("avatarOriented-> path: {}, duration: {}", path, durationInSeconds);
        messengerPlayPortalSE = userObject -> log.debug("playPortalSE->()");
        messengerReservedEffect = (userObject, path) -> log.debug("reservedEffect-> path: {}", path);
    }

    // =================================================================================================================

    public void setMessengerAskAccept(AskAcceptMessenger messengerAskAccept) {
        this.messengerAskAccept = messengerAskAccept;
    }

    public void setMessengerAskAvatar(AskAvatarMessenger messengerAskAvatar) {
        this.messengerAskAvatar = messengerAskAvatar;
    }

    public void setMessengerAskBoxText(AskBoxTextMessenger messengerAskBoxText) {
        this.messengerAskBoxText = messengerAskBoxText;
    }

    public void setMessengerAskMemberShopAvatar(AskMemberShopAvatar messengerAskMemberShopAvatar) {
        this.messengerAskMemberShopAvatar = messengerAskMemberShopAvatar;
    }

    public void setMessengerAskMenu(AskMenuMessenger messengerAskMenu) {
        this.messengerAskMenu = messengerAskMenu;
    }

    public void setMessengerAskNumber(AskNumberMessenger messengerAskNumber) {
        this.messengerAskNumber = messengerAskNumber;
    }

    public void setMessengerAskQuiz(AskQuizMessenger messengerAskQuiz) {
        this.messengerAskQuiz = messengerAskQuiz;
    }

    public void setMessengerAskSlideMenu(AskSlideMenuMessenger messengerAskSlideMenu) {
        this.messengerAskSlideMenu = messengerAskSlideMenu;
    }

    public void setMessengerAskSpeedQuiz(AskSpeedQuizMessenger messengerAskSpeedQuiz) {
        this.messengerAskSpeedQuiz = messengerAskSpeedQuiz;
    }

    public void setMessengerAskText(AskTextMessenger messengerAskText) {
        this.messengerAskText = messengerAskText;
    }

    public void setMessengerAskYesNo(AskYesNoMessenger messengerAskYesNo) {
        this.messengerAskYesNo = messengerAskYesNo;
    }

    public void setMessengerSayImage(SayImageMessenger messengerSayImage) {
        this.messengerSayImage = messengerSayImage;
    }

    public void setMessengerSay(SayMessenger messengerSay) {
        this.messengerSay = messengerSay;
    }

    // =================================================================================================================

    public void setMessengerMessage(MessageMessenger messengerMessage) {
        this.messengerMessage = messengerMessage;
    }

    public void setMessengerBalloon(BalloonMessenger messengerBalloon) {
        this.messengerBalloon = messengerBalloon;
    }

    public void setMessengerProgress(ProgressMessenger messengerProgress) {
        this.messengerProgress = messengerProgress;
    }

    public void setMessengerStatChanged(StatChangedMessenger messengerStatChanged) {
        this.messengerStatChanged = messengerStatChanged;
    }

    // =================================================================================================================


    public void setMessengerFieldObject(FieldObjectMessenger messengerFieldObject) {
        this.messengerFieldObject = messengerFieldObject;
    }

    public void setMessengerFieldScreen(FieldScreenMessenger messengerFieldScreen) {
        this.messengerFieldScreen = messengerFieldScreen;
    }

    public void setMessengerFieldSound(FieldSoundMessenger messengerFieldSound) {
        this.messengerFieldSound = messengerFieldSound;
    }

    public void setMessengerFieldTremble(FieldTrembleMessenger messengerFieldTremble) {
        this.messengerFieldTremble = messengerFieldTremble;
    }

    // =================================================================================================================

    public void setMessengerAvatarOriented(AvatarOrientedMessenger messengerAvatarOriented) {
        this.messengerAvatarOriented = messengerAvatarOriented;
    }

    public void setMessengerPlayPortalSE(PlayPortalSEMessenger messengerPlayPortalSE) {
        this.messengerPlayPortalSE = messengerPlayPortalSE;
    }

    public void setMessengerReservedEffect(ReservedEffectMessenger messengerReservedEffect) {
        this.messengerReservedEffect = messengerReservedEffect;
    }

    // =================================================================================================================

    /**
     * A message that is dropped into the chatbox. Or not. I don't know.
     * @param script
     * @param type
     * @param message
     */
    public static void message(MoeScript script, int type, String message) {
        script.setScriptResponse(null);
        script.setScriptAction(null);
        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerMessage.send(obj, type, message),
                () -> log.debug("User object isn't set, workflow is messy."));
    }

    /**
     * A balloon that appears above a user.
     * @param script
     * @param width
     * @param timeoutInSeconds
     * @param message
     */
    public static void balloon(MoeScript script, int width, int timeoutInSeconds, String message) {
        script.setScriptResponse(null);
        script.setScriptAction(null);
        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerBalloon.send(obj, message, width, timeoutInSeconds),
                () -> log.debug("User object isn't set, workflow is messy."));
    }

    /**
     * The yellow message that appears in the center of the screen.
     * @param script
     * @param message
     */
    public static void progress(MoeScript script, String message) {
        script.setScriptResponse(null);
        script.setScriptAction(null);
        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerProgress.send(obj, message),
                () -> log.debug("User object isn't set, workflow is messy."));
    }

    public static void statChanged(MoeScript script, boolean exclRequest) {
        script.setScriptResponse(null);
        script.setScriptAction(null);
        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerStatChanged.send(obj, exclRequest),
                () -> log.debug("User object isn't set, workflow is messy."));
    }

    // =================================================================================================================

    public static void fieldEffectScreen(MoeScript script, String path) {
        script.setScriptResponse(null);
        script.setScriptAction(null);
        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerFieldScreen.send(obj, path),
                () -> log.debug("User object isn't set, workflow is messy."));
    }

    public static void fieldEffectSound(MoeScript script, String path) {
        script.setScriptResponse(null);
        script.setScriptAction(null);
        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerFieldSound.send(obj, path),
                () -> log.debug("User object isn't set, workflow is messy."));
    }

    public static void fieldEffectTremble(MoeScript script, int type, int delay) {
        script.setScriptResponse(null);
        script.setScriptAction(null);
        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerFieldTremble.send(obj, type, delay),
                () -> log.debug("User object isn't set, workflow is messy."));
    }

    // =================================================================================================================

    public static void userAvatarOriented(MoeScript script, String path, int durationInSeconds) {
        script.setScriptResponse(null);
        script.setScriptAction(null);
        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAvatarOriented.send(obj, path, durationInSeconds),
                () -> log.debug("User object isn't set, workflow is messy."));
    }

    public static void userPlayPortalSE(MoeScript script) {
        script.setScriptResponse(null);
        script.setScriptAction(null);
        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerPlayPortalSE.send(obj),
                () -> log.debug("User object isn't set, workflow is messy."));
    }

    public static void userReservedEffect(MoeScript script, String path) {
        script.setScriptResponse(null);
        script.setScriptAction(null);
        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerReservedEffect.send(obj, path),
                () -> log.debug("User object isn't set, workflow is messy."));
    }

    // =================================================================================================================

    private static ScriptResponse sayResponse(MoeScript script, List<ScriptResponse> chain, Integer[] speakers, Tuple<Integer, String>[] paramAndMessage, Integer idx, Integer ts) {
        return (t, a, o) -> {
            if (t != ScriptMessageType.SAY) { // Wrong type, b-baka.
                script.end();
            } else {
                switch (a.intValue()) {
                    case -1: // Escape
                        script.end();
                        break;
                    case 0: // Back.
                        if (idx != 0) {
                            var back = idx-1 != 0;
                            var res = chain.get(idx - 1);
                            var message = paramAndMessage[idx-1].right();
                            var param = paramAndMessage[idx-1].left();
                            var speakerTemplateId = speakers[idx-1];
                            script.setScriptResponse(res);

                            int replaceTemplateId = speakerTemplateId;//Need to discuss
                            //if ((param & NPC_REPLACED_BY_NPC) > 0)
                            //    packet.encode4(replacedId);
                            // ^ Usage in Say

                            script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerSay.send(obj, speakerTemplateId, replaceTemplateId, param, message, back, true),
                                    () -> log.debug("User object isn't set, workflow is messy."));
                        } else {
                            log.warn("Tried to go back while on the first message? No! :(");
                            script.end();
                        }
                        break;
                    case 1: // Next.
                        if (ts - 1 >= idx + 1) {
                            var forward = ts - 1 > idx + 1;
                            var res = chain.get(idx + 1);
                            var message = paramAndMessage[idx+1].right();
                            var param = paramAndMessage[idx+1].left();
                            var speakerTemplateId = speakers[idx+1];

                            int replaceTemplateId = speakerTemplateId;//Need to discuss

                            script.setScriptResponse(res);
                            script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerSay.send(obj,speakerTemplateId, replaceTemplateId, param,  message, true, forward),
                                    () -> log.debug("User object isn't set, workflow is messy."));
                        } else {
                            script.setScriptResponse(null);
                            script.resume(t, a, o);
                        }
                        break;
                    default:
                        log.warn("Unhandled action({}) for {}", t, a);
                        script.end();
                        break;
                }
            }
        };
    }

    @SafeVarargs
    public static BasicActionChain say(MoeScript script, Integer[] speakers, Tuple<Integer, String>... paramAndMessages) {
        script.setScriptAction(null);
        script.setScriptResponse(null);

        With.index(speakers, (i, idx) -> {
            if (i == 0)
                speakers[idx] = script.getSpeakerTemplateId();
        });

        var chain = new LinkedList<ScriptResponse>();
        With.indexAndCount(paramAndMessages, (msg, idx, ts) -> chain.add(sayResponse(script, chain, speakers, paramAndMessages, idx, ts)));
        script.setScriptResponse(chain.getFirst());

        var speaker = speakers[0];
        var param = paramAndMessages[0].left();
        var message = paramAndMessages[0].right();

        int replaceTemplateId = speaker;//Need to discuss
        //if ((param & NPC_REPLACED_BY_NPC) > 0)
        //    packet.encode4(replacedId);
        // ^ Usage in Say

        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerSay.send(obj, speaker, replaceTemplateId, param, message,false, paramAndMessages.length > 1),
                () -> log.debug("User object isn't set, workflow is messy."));

        return script::setScriptAction;
    }

    @SafeVarargs
    public static BasicActionChain say(MoeScript script, Tuple<Integer, String>... paramAndMessages) {
        script.setScriptAction(null);

        Integer[] speakers = new Integer[paramAndMessages.length];
        Arrays.fill(speakers, script.getSpeakerTemplateId());
        return say(script, speakers, paramAndMessages);
    }

    public static BasicActionChain say(MoeScript script, Integer[] speakers, Integer[] params, String... messages) {
        Tuple<Integer, String>[] tlps = new Tuple[messages.length];
        With.index(messages, (m, i) -> {
            var par = params.length - 1 >= i ? params[i] : 0;
            tlps[i] = Tuple.of(par, m);
        });

        return say(script, speakers, tlps);
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
        return say(script, script.getSpeakerTemplateId(), param, messages);
    }

    public static BasicActionChain say(MoeScript script, String... messages) {
        return say(script, 0, messages);
    }

    public static BasicActionChain say(MoeScript script, String message, Object... objects) {
        return say(script, ScriptFormatter.format(message, objects));
    }

    // =================================================================================================================

    public static void askYesNo(MoeScript script, String message, BasicScriptAction onYes, BasicScriptAction onNo) {
        script.setScriptAction(null);
        script.setScriptResponse((t, a, o) -> {
            if (t != ScriptMessageType.ASKYESNO) {
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
        var speaker = script.getSpeakerTemplateId();

        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAskYesNo.send(obj, speaker, 0,  message),
                () -> log.debug("User object isn't set, workflow is messy."));
    }

    public static void askYesNo(MoeScript script, String message, BasicScriptAction onYes) {
        askYesNo(script, message, onYes, script::end);
    }

    // =================================================================================================================

    public static void askAccept(MoeScript script, String message, BasicScriptAction onYes, BasicScriptAction onNo) {
        script.setScriptAction(null);
        script.setScriptResponse((t, a, o) -> {
            if (t != ScriptMessageType.ASKACCEPT) {
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
        var speaker = script.getSpeakerTemplateId();

        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAskAccept.send(obj, speaker, 0, message),
                () -> log.debug("User object isn't set, workflow is messy."));
    }

    public static void askAccept(MoeScript script, String message, BasicScriptAction onYes) {
        askAccept(script, message, onYes, script::end);
    }

    // =================================================================================================================

    private static ScriptResponse askMenuResponse(MoeScript script, int max) {
        return (t, a, o) -> {
            var min = 0;

            if (t != ScriptMessageType.ASKMENU || a.intValue() != 1) {
                script.end();
            } else {
                var sel = ((Integer)o);
                var bad = sel == null || sel < min || sel > max;
                if (bad) {
                    log.debug("Value mismatch: min {}, max {}, val {}", min, max, sel);
                    script.end();
                } else {
                    script.setScriptResponse(null);
                    script.resume(t, a, o);
                }
            }
        };
    }

    public static IntegerActionChain askMenu(MoeScript script, int speakerTemplateId, int param, String prompt, String... menuItems) {
        var builder = new ScriptMenuBuilder();
        builder.append(prompt).newLine().blue().appendMenu(menuItems);

        script.setScriptAction(null);
        script.setScriptResponse(askMenuResponse(script, menuItems.length - 1));

        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAskMenu.send(obj, speakerTemplateId, param, builder.toString()),
                () -> log.debug("User object isn't set, workflow is messy."));

        return script::setScriptAction;
    }

    public static IntegerActionChain askMenu(MoeScript script, int speakerTemplateId, String prompt, String... menuItems) {
        return askMenu(script, speakerTemplateId, 0, prompt, menuItems);
    }

    public static IntegerActionChain askMenu(MoeScript script, String prompt, String... menuItems) {
        return askMenu(script, script.getSpeakerTemplateId(), prompt, menuItems);
    }

    public static IntegerActionChain askMenu(MoeScript script, String prompt) {
        script.setScriptAction(null);
        script.setScriptResponse(askMenuResponse(script, prompt.length() - prompt.replace("#L", "").length() - 1));
        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAskMenu.send(obj, script.getSpeakerTemplateId(), 0, prompt),
                () -> log.debug("User object isn't set, workflow is messy."));
        return script::setScriptAction;
    }

    @SafeVarargs
    public static void askMenu(MoeScript script, String prompt, Tuple<String, BasicScriptAction>... options) {
        var builder = new ScriptMenuBuilder();
        builder.append(prompt).newLine().blue().appendMenu(Tuple::left, options);

        script.setScriptAction(null);
        script.setScriptResponse((t, a, o) -> {
            var min = 0;
            var max = options.length - 1;
            var sel = ((Integer)o);
            var bad = sel == null || sel < min || sel > max;

            if (t != ScriptMessageType.ASKMENU || bad || a.intValue() != 1) {
                if (bad)
                    log.debug("Value mismatch: min {}, max {}, val {}", min, max, sel);
                script.end();
            } else {
                script.setScriptResponse(null);
                script.setScriptAction(options[sel].right());
                script.resume(t, a, o);
            }
        });

        var speaker = script.getSpeakerTemplateId();

        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAskMenu.send(obj, speaker, 0, builder.toString()),
                () -> log.debug("User object isn't set, workflow is messy."));
    }

    // =================================================================================================================

    private static ScriptResponse askAvatarResponse(MoeScript script, int... options) {
        return (t, a, o) -> {
            var min = 0;
            var max = options.length - 1;
            var sel = ((Integer)o);
            var bad = sel == null || sel < min || sel > max;

            if (t != ScriptMessageType.ASKAVATAR || bad || a.intValue() != 1) {
                if (bad)
                    log.debug("Value mismatch: min {}, max {}, val {}", min, max, sel);
                script.end();
            } else {
                script.setScriptResponse(null);
                script.resume(t, a, o);
            }
        };
    }

    public static IntegerActionChain askAvatar(MoeScript script, int speakerTemplateId, int param, String prompt, int... options) {
        script.setScriptAction(null);
        script.setScriptResponse(askAvatarResponse(script, options));

        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAskAvatar.send(obj, speakerTemplateId, param, prompt,  options),
                () -> log.debug("User object isn't set, workflow is messy."));

        return script::setScriptAction;
    }

    public static IntegerActionChain askAvatar(MoeScript script, int speakerTemplateId, String prompt, int... options) {
        return askAvatar(script, speakerTemplateId, 0, prompt, options);
    }

    public static IntegerActionChain askAvatar(MoeScript script, String prompt, int... options) {
        return askAvatar(script, script.getSpeakerTemplateId(), prompt, options);
    }

    // =================================================================================================================

    private static ScriptResponse askTextResponse(MoeScript script, int min, int max) {
        return (t, a, o) -> {
            var sel = ((String)o);
            var bad = sel == null || sel.length() < min || sel.length() > max;

            if (t != ScriptMessageType.ASKTEXT || bad || a.intValue() != 1) {
                if (bad)
                    log.debug("Value mismatch: min {}, max {}, val {}", min, max, sel);
                script.end();
            } else {
                script.setScriptResponse(null);
                script.resume(t, a, o);
            }
        };
    }

    public static StringActionChain askText(MoeScript script, int speakerTemplateId, int param, String message, String defaultText, int min, int max) {
        script.setScriptAction(null);
        script.setScriptResponse(askTextResponse(script, min, max));

        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAskText.send(obj, speakerTemplateId, param, message, defaultText, min, max),
                () -> log.debug("User object isn't set, workflow is messy."));
        return script::setScriptAction;
    }

    public static StringActionChain askText(MoeScript script, int param, String message, String defaultText, int min, int max) {
        return askText(script, script.getSpeakerTemplateId(), param, message, defaultText, min, max);
    }

    public static StringActionChain askText(MoeScript script, String message, String defaultText, int min, int max) {
        return askText(script, script.getSpeakerTemplateId(), 0, message, defaultText, min, max);
    }

    public static StringActionChain askText(MoeScript script, String message, String defaultText, int min) {
        return askText(script, script.getSpeakerTemplateId(), 0, message, defaultText, min, 32);
    }

    public static StringActionChain askText(MoeScript script, String message, String defaultText) {
        return askText(script, script.getSpeakerTemplateId(), 0, message, defaultText, 0, 32);
    }

    public static StringActionChain askText(MoeScript script, String message) {
        return askText(script, script.getSpeakerTemplateId(), 0, message, "", 0, 32);
    }

    // =================================================================================================================

    private static ScriptResponse askNumberResponse(MoeScript script, int min, int max) {
        return (t, a, o) -> {
            var sel = ((Integer)o);
            var bad = sel == null || sel < min || sel > max;

            if (t != ScriptMessageType.ASKNUMBER || bad || a.intValue() != 1) {
                if (bad)
                    log.debug("Value mismatch: min {}, max {}, val {}", min, max, sel);
                script.end();
            } else {
                script.setScriptResponse(null);
                script.resume(t, a, o);
            }
        };
    }

    public static IntegerActionChain askNumber(MoeScript script, int speakerTemplateId, int param, String message, int defaultNumber, int min, int max) {
        script.setScriptAction(null);
        script.setScriptResponse(askNumberResponse(script, min, max));

        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAskNumber.send(obj, speakerTemplateId, param, message, defaultNumber, min, max),
                () -> log.debug("User object isn't set, workflow is messy."));

        return script::setScriptAction;
    }

    public static IntegerActionChain askNumber(MoeScript script, int param, String message, int defaultNumber, int min, int max) {
        return askNumber(script, script.getSpeakerTemplateId(), param, message, defaultNumber, min, max);
    }

    public static IntegerActionChain askNumber(MoeScript script, String message, int defaultNumber, int min, int max) {
        return askNumber(script, script.getSpeakerTemplateId(), 0, message, defaultNumber, min, max);
    }

    public static IntegerActionChain askNumber(MoeScript script, String message, int defaultNumber, int min) {
        return askNumber(script, script.getSpeakerTemplateId(), 0, message, defaultNumber, min, Integer.MAX_VALUE);
    }

    public static IntegerActionChain askNumber(MoeScript script, String message, int defaultNumber) {
        return askNumber(script, script.getSpeakerTemplateId(), 0, message, defaultNumber, 0, Integer.MAX_VALUE);
    }

    public static IntegerActionChain askNumber(MoeScript script, String message) {
        return askNumber(script, script.getSpeakerTemplateId(), 0, message, 0, 0, Integer.MAX_VALUE);
    }

}
