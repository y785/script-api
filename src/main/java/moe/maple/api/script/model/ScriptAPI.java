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
import moe.maple.api.script.model.messenger.*;
import moe.maple.api.script.model.messenger.ask.*;
import moe.maple.api.script.model.messenger.effect.field.FieldScreenMessenger;
import moe.maple.api.script.model.messenger.effect.field.FieldSoundMessenger;
import moe.maple.api.script.model.messenger.effect.uel.AvatarOrientedMessenger;
import moe.maple.api.script.model.messenger.effect.uel.PlayPortalSEMessenger;
import moe.maple.api.script.model.messenger.effect.uel.ReservedEffectMessenger;
import moe.maple.api.script.model.messenger.say.SayImageMessenger;
import moe.maple.api.script.model.messenger.say.SayMessenger;
import moe.maple.api.script.model.response.ScriptResponse;
import moe.maple.api.script.model.type.SpeakerType;
import moe.maple.api.script.util.builder.ScriptMenuBuilder;
import moe.maple.api.script.util.tuple.Tuple;
import moe.maple.api.script.util.With;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    // =================================================================================================================

    private FieldScreenMessenger messengerFieldScreen;
    private FieldSoundMessenger messengerFieldSound;

    // =================================================================================================================

    private AvatarOrientedMessenger messengerAvatarOriented;
    private PlayPortalSEMessenger messengerPlayPortalSE;
    private ReservedEffectMessenger messengerReservedEffect;

    ScriptAPI() { }

    public void setDefaultMessengers() {
        messengerAskAccept = (userObject, speakerTemplateId, param, message) -> log.debug("askAccept-> speaker: {}, param: {}, message: \"{}\"", speakerTemplateId, param, message);
        messengerAskAvatar = (userObject, message, speakerTemplateId, param,  options) -> log.debug("askAvatar-> speaker: {}, param: {}, options: {}, message: \"{}\"", speakerTemplateId, param, options, message);
        messengerAskBoxText = (userObject, speakerTemplateId, param, message, defaultText, column, row) -> log.debug("askBoxText-> speaker: {}, param: {}, column: {}, row: {}, default: \"{}\", message: \"{}\"", speakerTemplateId, param, column, row, defaultText, message);
        messengerAskMemberShopAvatar = (userObject, speakerTemplateId, param, message, options) -> log.debug("askMemberShopAvatar-> speaker: {}, param: {}, options: {}, message: \"{}\"", speakerTemplateId, param, options, message);
        messengerAskMenu = (userObject, message, speakerTemplateId, param) -> log.debug("askMenu-> speaker: {}, param: {}, message: \"{}\"", speakerTemplateId, param, message);
        messengerAskNumber = (userObject, speakerTemplateId, param, message, defaultNumber, min, max) -> log.debug("askNumber-> speaker: {}, param: {}, min: {}, max: {}, default: {}, message: \"{}\"", speakerTemplateId, param, min, max, defaultNumber, message);
        messengerAskQuiz = (userObject, speakerTemplateId, param, title, problemText, hintText, min, max, remainInitialQuiz) -> log.debug("askQuiz-> speaker: {}, param: {}, min: {}, max: {}, remain: {}, title: \"{}\", problem: \"{}\", hint: \"{}\"", speakerTemplateId, param, min, max, remainInitialQuiz, title, problemText, hintText);
        messengerAskSlideMenu = (userObject, speakerTemplateId, slideDlgEX, index, message) -> log.debug("askSlideMenu-> speaker: {}, slideDlgEX: {}, index: {}, message: \"{}\"", speakerTemplateId, slideDlgEX, index, message);
        messengerAskSpeedQuiz = (userObject, speakerTemplateId, param, type, answer, correct, remaining, remainInitialQuiz, title, problemText, hintText, min, max, remainInitialQuiz1) -> log.debug("askSpeedQuiz-> speaker: {}, param: {}, type: {}, answer: {}, correct: {}, remaining: {}, remain: {}, title: \"{}\", problem: \"{}\", hint: \"{}\", min: {}, max: {}, remain1: {}", speakerTemplateId, param, type, answer, correct, remaining, remainInitialQuiz, title, problemText, hintText, min, max, remainInitialQuiz1);
        messengerAskText = (userObject, speakerTemplateId, param, message, defaultText, min, max) -> log.debug("askText-> speaker: {}, param: {}, min: {}, max: {}, default: \"{}\", message: \"{}\"", speakerTemplateId, param, min, max, defaultText, message);
        messengerAskYesNo = (userObject, message, speakerTemplateId, param) -> log.debug("askYesNo-> speaker: {}, param: {}, message: \"{}\"", speakerTemplateId, param, message);
        messengerSayImage = (userObject, speakerTemplateId, param, imagePath) -> log.debug("sayImage-> speaker: {}, param: {}, path: {}", speakerTemplateId, param, imagePath);
        messengerSay = (userObject, message, speakerTemplateId, param, previous, next) -> log.debug("say-> speaker: {}, param: {}, prev: {}, next: {}, message: \"{}\"", speakerTemplateId, param, previous, next, message);

        messengerMessage = ((userObject, type, message) -> log.debug("message-> type: {}, message: \"{}\"", type, message));
        messengerBalloon = ((userObject, message, width, timeoutInSeconds) -> log.debug("balloon-> width: {}, timeout: {}, message: \"{}\"", width, timeoutInSeconds, message));
        messengerProgress= ((userObject, message) -> log.debug("progress-> message:  \"{}\"", message));

        messengerFieldScreen = (userObject, path) -> log.debug("fieldScreen-> path: {}", path);
        messengerFieldSound = (userObject, path) -> log.debug("fieldSound-> path: {}", path);

        messengerAvatarOriented = (userObject, path, durationInSeconds) -> log.debug("avatarOriented-> path: {}, duration: {}");
        messengerPlayPortalSE = userObject -> log.debug("playPortalSE->");
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

    // =================================================================================================================

    public void setMessengerFieldScreen(FieldScreenMessenger messengerFieldScreen) {
        this.messengerFieldScreen = messengerFieldScreen;
    }

    public void setMessengerFieldSound(FieldSoundMessenger messengerFieldSound) {
        this.messengerFieldSound = messengerFieldSound;
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

                            script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerSay.send(obj, message, speakerTemplateId, param, back, true),
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
                            script.setScriptResponse(res);
                            script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerSay.send(obj, message, speakerTemplateId, param, true, forward),
                                    () -> log.debug("User object isn't set, workflow is messy."));
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

    @SafeVarargs
    public static BasicActionChain say(MoeScript script, Tuple<Integer, String>... paramAndMessages) {
        script.setScriptAction(null);

        var chain = new LinkedList<ScriptResponse>();

        With.indexAndCount(paramAndMessages, (tpl, idx, ts) ->
                chain.add(sayResponse(script, chain, script.getSpeakerTemplateId(), tpl.left(), tpl.right(), idx, ts)));
        script.setScriptResponse(chain.getFirst());

        var speaker = script.getSpeakerTemplateId();
        var param = paramAndMessages[0].left();
        var message = paramAndMessages[0].right();

        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerSay.send(obj, message, speaker, param, false, paramAndMessages.length > 1),
                () -> log.debug("User object isn't set, workflow is messy."));

        return script::setScriptAction;
    }

    public static BasicActionChain say(MoeScript script, Integer[] speakers, Integer[] params, String... messages) {
        script.setScriptAction(null);

        var chain = new LinkedList<ScriptResponse>();

        // To be honest, this isn't really needed. // todo validate
        With.index(speakers, (i, idx) -> {
            if (i == 0)
                speakers[idx] = script.getSpeakerTemplateId();
        });
        With.indexAndCount(messages, (msg, idx, ts) -> chain.add(sayResponse(script, chain, speakers[idx], params[idx], msg, idx, ts)));
        script.setScriptResponse(chain.getFirst());

        var speaker = speakers[0];
        var param = params[0];
        var message = messages[0];

        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerSay.send(obj, message, speaker, param, false, messages.length > 1),
                () -> log.debug("User object isn't set, workflow is messy."));

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
        With.indexAndCount(messages, (msg, idx, ts) -> chain.add(sayResponse(script, chain, script.getSpeakerTemplateId(), param, msg, idx, ts)));
        script.setScriptResponse(chain.getFirst());

        var speaker = script.getSpeakerTemplateId();
        var message = messages[0];

        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerSay.send(obj, message, speaker, param, false, messages.length > 1),
                () -> log.debug("User object isn't set, workflow is messy."));

        return script::setScriptAction;
    }

    public static BasicActionChain say(MoeScript script, String... messages) {
        return say(script, 0, messages);
    }

    public static BasicActionChain say(MoeScript script, String message, Object... objects) {
        return say(script, MessageFormatter.arrayFormat(message, objects).getMessage());
    }

    // =================================================================================================================

    public static void askYesNo(MoeScript script, String message, BasicScriptAction onYes, BasicScriptAction onNo) {
        script.setScriptAction(null);
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
        var speaker = script.getSpeakerTemplateId();

        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAskYesNo.send(obj, message, speaker, 0),
                () -> log.debug("User object isn't set, workflow is messy."));
    }

    public static void askYesNo(MoeScript script, String message, BasicScriptAction onYes) {
        askYesNo(script, message, onYes, script::end);
    }

    // =================================================================================================================

    public static void askAccept(MoeScript script, String message, BasicScriptAction onYes, BasicScriptAction onNo) {
        script.setScriptAction(null);
        script.setScriptResponse((t, a, o) -> {
            if (t != SpeakerType.ASKACCEPT) {
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

            if (t != SpeakerType.ASKMENU || a.intValue() != 1) {
                script.end();
            } else {
                var sel = ((Number)o).intValue();
                var bad = sel < min || sel > max;
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
        builder.append(prompt).appendMenu(menuItems);

        script.setScriptAction(null);
        script.setScriptResponse(askMenuResponse(script, menuItems.length - 1));

        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAskMenu.send(obj, builder.toString(), speakerTemplateId, param),
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
        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAskMenu.send(obj, prompt, script.getSpeakerTemplateId(), 0),
                () -> log.debug("User object isn't set, workflow is messy."));
        return script::setScriptAction;
    }

    @SafeVarargs
    public static void askMenu(MoeScript script, String prompt, Tuple<String, BasicScriptAction>... options) {
        var builder = new ScriptMenuBuilder();
        builder.append(prompt).appendMenu(Stream.of(options).map(Tuple::left).collect(Collectors.joining()));

        script.setScriptAction(null);
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

        var speaker = script.getSpeakerTemplateId();

        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAskMenu.send(obj, builder.toString(), speaker, 0),
                () -> log.debug("User object isn't set, workflow is messy."));
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

    public static IntegerActionChain askAvatar(MoeScript script, int speakerTemplateId, int param, String prompt, int... options) {
        script.setScriptAction(null);
        script.setScriptResponse(askAvatarResponse(script, options));

        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAskAvatar.send(obj, prompt, speakerTemplateId, param, options),
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
}
