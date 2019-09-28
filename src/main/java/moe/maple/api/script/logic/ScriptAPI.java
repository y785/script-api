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

package moe.maple.api.script.logic;

import moe.maple.api.script.logic.action.*;
import moe.maple.api.script.logic.chain.BasicActionChain;
import moe.maple.api.script.logic.chain.IntegerActionChain;
import moe.maple.api.script.logic.chain.StringActionChain;
import moe.maple.api.script.logic.response.SayResponse;
import moe.maple.api.script.model.ScriptPreferences;
import moe.maple.api.script.model.helper.MenuItem;
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
import moe.maple.api.script.model.messenger.say.SayMessage;
import moe.maple.api.script.model.messenger.say.SayMessenger;
import moe.maple.api.script.logic.response.ScriptResponse;
import moe.maple.api.script.model.object.user.UserObject;
import moe.maple.api.script.model.MoeScript;
import moe.maple.api.script.model.type.ScriptMessageType;
import moe.maple.api.script.util.Moematter;
import moe.maple.api.script.util.builder.ScriptMenuBuilder;
import moe.maple.api.script.util.builder.ScriptStringBuilder;
import moe.maple.api.script.util.tuple.Tuple;
import moe.maple.api.script.util.With;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    // =================================================================================================================

    private final ScriptPreferences preferences;

    private final Map<Integer, Integer> typeMap;

    ScriptAPI() {
        this.preferences = ScriptPreferences.DEFAULT;
        this.typeMap = new HashMap<>();
    }

    // =================================================================================================================

    /**
     * ScriptMessageType changes based on version.
     * This can cause a lot of issues, so you need to set your values,
     * based on what is provided.
     * @param scriptMessageType - See {@link ScriptMessageType}
     * @param value             - Your server's value.
     */
    public void setScriptMessageType(int scriptMessageType, int value) {
        typeMap.put(scriptMessageType, value);
    }

    public int getScriptMessageType(int scriptMessageType) {
        return typeMap.getOrDefault(scriptMessageType, scriptMessageType);
    }

    // =================================================================================================================

    /**
     * Sets default messengers to logging messengers.
     */
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
                -> log.debug("say-> speakerType: {}, speakerTemplate: {}, param: {}, message: \"{}\", prev: {}, next: {}", speakerType, speakerTemplateId, param, message, previous, next);

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

    private static void onScriptMessage(MoeScript script, Consumer<UserObject> sendMessage) {
        script.setScriptResponse(null);
        script.setScriptAction(null);
        script.getUserObject().ifPresentOrElse(sendMessage, () -> log.debug("User object isn't set, workflow is messy."));
    }

    /**
     * A message that is dropped into the chatbox. Or not. I don't know.
     * @param script
     * @param type
     * @param message
     */
    public static void message(MoeScript script, int type, String message) {
        onScriptMessage(script, user->ScriptAPI.INSTANCE.messengerMessage.send(user, type, message));
    }

    /**
     * A balloon that appears above a user.
     * @param script
     * @param width
     * @param timeoutInSeconds
     * @param message
     */
    public static void balloon(MoeScript script, int width, int timeoutInSeconds, String message) {
        onScriptMessage(script, user->ScriptAPI.INSTANCE.messengerBalloon.send(user, message, width, timeoutInSeconds));
    }

    /**
     * The yellow message that appears in the center of the screen.
     * @param script
     * @param message
     */
    public static void progress(MoeScript script, String message) {
        onScriptMessage(script, user->ScriptAPI.INSTANCE.messengerProgress.send(user, message));
    }

    public static void statChanged(MoeScript script, boolean exclRequest) {
        onScriptMessage(script, user->ScriptAPI.INSTANCE.messengerStatChanged.send(user, exclRequest));
    }

    // =================================================================================================================

    public static void fieldEffectScreen(MoeScript script, String path) {
        onScriptMessage(script, user->ScriptAPI.INSTANCE.messengerFieldScreen.send(user, path));
    }

    public static void fieldEffectSound(MoeScript script, String path) {
        onScriptMessage(script, user->ScriptAPI.INSTANCE.messengerFieldScreen.send(user, path));
    }

    public static void fieldEffectTremble(MoeScript script, int type, int delay) {
        onScriptMessage(script, user->ScriptAPI.INSTANCE.messengerFieldTremble.send(user, type, delay));
    }

    // =================================================================================================================

    public static void userAvatarOriented(MoeScript script, String path, int durationInSeconds) {
        onScriptMessage(script, user->ScriptAPI.INSTANCE.messengerAvatarOriented.send(user, path, durationInSeconds));
    }

    public static void userPlayPortalSE(MoeScript script) {
        onScriptMessage(script, user->ScriptAPI.INSTANCE.messengerPlayPortalSE.send(user));
    }

    public static void userReservedEffect(MoeScript script, String path) {
        onScriptMessage(script, user->ScriptAPI.INSTANCE.messengerReservedEffect.send(user, path));
    }

    public static BasicActionChain say(MoeScript script, Collection<SayMessage> saying) {
        script.setScriptAction(null);
        script.setScriptResponse(null);
        var chain = new SayResponse[saying.size()];
        With.index(saying, (msg, idx) -> chain[idx] = (new SayResponse(chain, ScriptAPI.INSTANCE.messengerSay, script, idx, msg)));
        chain[0].onResponse(chain[0]);
        return script::setScriptAction;
    }

    public static BasicActionChain say(MoeScript script, Integer[] speakers, List<Tuple<Integer, String>> paramAndMessages) {
        script.setScriptAction(null);
        script.setScriptResponse(null);
        var chain = new SayResponse[paramAndMessages.size()];
        With.indexAndCount(paramAndMessages, (msg, idx, ts) -> chain[idx] = (new SayResponse(chain, ScriptAPI.INSTANCE.messengerSay, script, idx, new SayMessage(0, speakers[idx], 0, paramAndMessages.get(idx).left(), paramAndMessages.get(idx).right()))));
        chain[0].onResponse(chain[0]);
        return script::setScriptAction;
    }

    public static BasicActionChain say(MoeScript script, List<Tuple<Integer, String>> paramAndMessages) {
        script.setScriptAction(null);
        Integer[] speakers = new Integer[paramAndMessages.size()];
        Arrays.fill(speakers, script.getSpeakerTemplateId());
        return say(script, speakers, paramAndMessages);
    }

    public static BasicActionChain say(MoeScript script, Integer[] speakers, Integer[] params, List<String> messages) {
        var tlps = new ArrayList<Tuple<Integer, String>>(messages.size());
        With.index(messages, (m, i) -> {
            var par = params.length - 1 >= i ? params[i] : 0;
            tlps.add(Tuple.of(par, m));
        });
        return say(script, speakers, tlps);
    }

    public static BasicActionChain say(MoeScript script, Integer[] speakers, int param, List<String> messages) {
        Integer[] params = new Integer[messages.size()];
        Arrays.fill(params, param);
        return say(script, speakers, params, messages);
    }

    public static BasicActionChain say(MoeScript script, int speakerTemplateId, int param, List<String> messages) {
        Integer[] speakers = new Integer[messages.size()];
        Integer[] params = new Integer[speakers.length];
        Arrays.fill(speakers, speakerTemplateId);
        Arrays.fill(params, param);
        return say(script, speakers, params, messages);
    }

    public static BasicActionChain say(MoeScript script, int param, List<String> messages) {
        return say(script, script.getSpeakerTemplateId(), param, messages);
    }

    public static BasicActionChain say(MoeScript script, String message, Object... objects) {
        return say(script, 0, List.of(Moematter.format(message, objects)));
    }

    // =================================================================================================================

    public static void askYesNo(MoeScript script, String message, BasicScriptAction onYes, BasicScriptAction onNo) {
        script.setScriptAction(null);
        script.setScriptResponse((t, a, o) -> {
            var real = ScriptAPI.INSTANCE.getScriptMessageType(ScriptMessageType.ASKYESNO);
            if (t.intValue() != real) {
                log.warn("ScriptMessageType mismatch: {} vs {}", t.intValue(), real);
                script.end();
            } else {
                var an = a.intValue();
                if (an == 1 || an == 0) {
                    var act = an == 0 ? onNo : onYes;
                    script.setScriptResponse(null);
                    script.setScriptAction(act);
                    script.resume(t, a, o);
                } else if (an == -1) {
                    script.escape();
                } else {
                    log.debug("Answer was invalid, ending: {}", an);
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
            var real = ScriptAPI.INSTANCE.getScriptMessageType(ScriptMessageType.ASKACCEPT);
            if (t.intValue() != real) {
                log.warn("ScriptMessageType mismatch: {} vs {}", t.intValue(), real);
                script.end();
            } else {
                var an = a.intValue();
                if (an == 1 || an == 0) {
                    var act = an == 0 ? onNo : onYes;
                    script.setScriptResponse(null);
                    script.setScriptAction(act);
                    script.resume(t, a, o);
                } else if (an == -1) {
                    script.escape();
                } else {
                    log.debug("Answer was invalid, ending: {}", an);
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

    private static ScriptResponse askMenuResponse(MoeScript script, Set<Integer> options) {
        return (t, a, o) -> {
            var real = ScriptAPI.INSTANCE.getScriptMessageType(ScriptMessageType.ASKMENU);
            if (t.intValue() != real || a.intValue() != 1) {
                if (t.intValue() != real)
                    log.warn("ScriptMessageType mismatch: {} vs {}", t.intValue(), real);
                else if (a.intValue() == 0)
                    log.debug("Answer escaped, escaping: {}", a.intValue());
                else
                    log.warn("Answer wasn't valid, ending: {}", a.intValue());
                script.escape(); // todo, ask menu is always an escape?
            } else {
                var sel = ((Integer)o);
                var bad = sel == null || !options.contains(sel);
                if (bad) {
                    log.debug("Value mismatch: val {} options {}", sel, options);
                    script.end();
                } else {
                    script.setScriptResponse(null);
                    script.resume(t, a, o);
                }
            }
        };
    }

    public static IntegerActionChain askMenu(MoeScript script, int speakerTemplateId, int param, String prompt, Collection<String> menuItems) {
        var builder = new ScriptMenuBuilder<>();
        builder.append(prompt).newLine().blue().appendMenu(menuItems);
        var built = builder.toString();

        script.setScriptAction(null);

        var options = ScriptMenuBuilder.matchIndices(built).stream().map(ScriptMenuBuilder::parseMenuIndex).collect(Collectors.toSet());
        script.setScriptResponse(askMenuResponse(script, options));

        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAskMenu.send(obj, speakerTemplateId, param, built),
                () -> log.debug("User object isn't set, workflow is messy."));

        return script::setScriptAction;
    }

    public static IntegerActionChain askMenu(MoeScript script, int speakerTemplateId, String prompt, Collection<String> menuItems) {
        return askMenu(script, speakerTemplateId, 0, prompt, menuItems);
    }

    public static IntegerActionChain askMenu(MoeScript script, String prompt, Collection<String> menuItems) {
        return askMenu(script, script.getSpeakerTemplateId(), prompt, menuItems);
    }

    public static IntegerActionChain askMenu(MoeScript script, String prompt) {
        script.setScriptAction(null);
        var options = ScriptMenuBuilder.matchIndices(prompt).stream().map(ScriptMenuBuilder::parseMenuIndex).collect(Collectors.toSet());
        script.setScriptResponse(askMenuResponse(script, options));//prompt.length() - prompt.replace("#L", "").length() - 1
        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAskMenu.send(obj, script.getSpeakerTemplateId(), 0, prompt),
                () -> log.debug("User object isn't set, workflow is messy."));
        return script::setScriptAction;
    }

    public static void askMenu(MoeScript script, String prompt, MenuItem... items) {
        script.setScriptAction(null);

        var ssb = new ScriptStringBuilder().append(prompt).blue().newLine();
        var actionMap = new HashMap<Integer, BasicScriptAction>();
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            actionMap.put(i, item.action());
            ssb.appendMenuItemLine(i, item.message());
        }
        var options = IntStream.range(0, items.length).boxed().collect(Collectors.toUnmodifiableSet());
        script.setScriptResponse((t, a, o) -> {
            var sel = ((Integer)o);
            var bad = sel == null || !options.contains(sel);
            var real = ScriptAPI.INSTANCE.getScriptMessageType(ScriptMessageType.ASKMENU);
            if (t.intValue() != real || bad || a.intValue() != 1) {
                if (bad)
                    log.debug("Value mismatch: val {} keys {}", sel, options);
                else if (t.intValue() != real)
                    log.warn("ScriptMessageType mismatch: {} vs {}", t.intValue(), real);
                else
                    log.debug("Answer is invalid: {}", a);
                if (a.intValue() == -1)
                    script.escape();
                else
                    script.end();
            } else {
                script.setScriptResponse(null);
                script.setScriptAction(actionMap.get(sel));
                script.resume(t, a, o);
            }
        });

        var speaker = script.getSpeakerTemplateId();
        script.getUserObject().ifPresentOrElse(obj -> ScriptAPI.INSTANCE.messengerAskMenu.send(obj, speaker, 0, ssb.toString()),
                () -> log.debug("User object isn't set, workflow is messy."));
    }

    // =================================================================================================================

    private static ScriptResponse askAvatarResponse(MoeScript script, Collection<Integer> options) {
        return (t, a, o) -> {
            var sel = ((Integer)o);
            var bad = sel == null || !options.contains(sel);
            var real = ScriptAPI.INSTANCE.getScriptMessageType(ScriptMessageType.ASKAVATAR);

            if (t.intValue() != real || bad || a.intValue() != 1) {
                if (bad)
                    log.debug("Value mismatch: val {}, options {}", sel, options);
                else if (t.intValue() != real)
                    log.warn("ScriptMessageType mismatch: {} vs {}", t.intValue(), real);
                else
                    log.debug("Answer is invalid: {}", a);
                if (a.intValue() == -1)
                    script.escape();
                else
                    script.end();
            } else {
                script.setScriptResponse(null);
                script.resume(t, a, o);
            }
        };
    }

    public static IntegerActionChain askAvatar(MoeScript script, int speakerTemplateId, int param, String prompt, Collection<Integer> options) {
        script.setScriptAction(null);
        script.setScriptResponse(askAvatarResponse(script, options));
        int[] optionArray = options.stream().mapToInt(Integer::intValue).toArray();//:|
        script.getUserObject().ifPresentOrElse(obj ->ScriptAPI.INSTANCE.messengerAskAvatar.send(obj, speakerTemplateId, param, prompt, optionArray),
                () -> log.debug("User object isn't set, workflow is messy."));

        return script::setScriptAction;
    }

    public static IntegerActionChain askAvatar(MoeScript script, int speakerTemplateId, String prompt, Collection<Integer> options) {
        return askAvatar(script, speakerTemplateId, 0, prompt, options);
    }

    public static IntegerActionChain askAvatar(MoeScript script, String prompt, Collection<Integer> options) {
        return askAvatar(script, script.getSpeakerTemplateId(), prompt, options);
    }

    // =================================================================================================================

    private static ScriptResponse askTextResponse(MoeScript script, int min, int max) {
        return (t, a, o) -> {
            var sel = ((String)o);
            var bad = sel == null || sel.length() < min || sel.length() > max;
            var real = ScriptAPI.INSTANCE.getScriptMessageType(ScriptMessageType.ASKTEXT);

            if (t.intValue() != real || bad || a.intValue() != 1) {
                if (bad)
                    log.debug("Value mismatch: min {}, max {}, val {}", min, max, sel);
                else if (t.intValue() != real)
                    log.warn("ScriptMessageType mismatch: {} vs {}", t.intValue(), real);
                else
                    log.debug("Answer is invalid: {}", a);
                if (a.intValue() == -1)
                    script.escape();
                else
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
            var real = ScriptAPI.INSTANCE.getScriptMessageType(ScriptMessageType.ASKTEXT);

            if (t.intValue() != ScriptMessageType.ASKNUMBER || bad || a.intValue() != 1) {
                if (bad)
                    log.debug("Value mismatch: min {}, max {}, val {}", min, max, sel);
                else if (t.intValue() != real)
                    log.warn("ScriptMessageType mismatch: {} vs {}", t.intValue(), real);
                else
                    log.debug("Answer is invalid: {}", a.intValue());
                if (a.intValue() == -1)
                    script.escape();
                else
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

    public ScriptPreferences getPreferences() {
        return preferences;
    }

}
