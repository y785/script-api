package moe.maple.api.script.model;

import moe.maple.api.script.model.action.BasicScriptAction;
import moe.maple.api.script.model.action.NumberScriptAction;
import moe.maple.api.script.model.action.StringScriptAction;
import moe.maple.api.script.model.response.ScriptResponse;
import moe.maple.api.script.model.type.SpeakerType;
import moe.maple.api.script.util.ScriptStringBuilder;
import moe.maple.api.script.util.Tuple;
import moe.maple.api.script.util.With;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScriptAPI {
    private static final Logger log = LoggerFactory.getLogger( ScriptAPI.class );

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
        With.indexAndCount(messages, (msg, idx, ts) -> {
            chain.add((t, a, o) -> {
                if (t != SpeakerType.SAY) { // Wrong type, b-baka.
                    script.end();
                } else {
                    switch (a.intValue()) {
                        case -1: // Escape
                            script.end();
                        case 0: // Back.
                            if (idx != 0) {
                                var res = chain.get(idx - 1);
                                script.setScriptResponse(res);
                                log.debug("Now I send packet for: {}", messages[idx - 1]);
                            } else {
                                log.warn("Tried to go back while on the first message? No! :(");
                                script.end();
                            }
                            break;
                        case 1: // Next.
                            if (ts - 1 >= idx + 1) {
                                var res = chain.get(idx + 1);
                                script.setScriptResponse(res);
                                log.debug("Now I send packet for: {}", messages[idx + 1]);
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
            });
        });

        log.debug("say: {}", messages[0]);
        // script.setScriptAction(next);
        script.setScriptResponse(chain.getFirst());
        // send packet.
        // handle response.

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
        // todo send packet.
        log.debug("askYesNo: {}", message);
    }

    public static void askYesNo(MoeScript script, String message, BasicScriptAction onYes) {
        askYesNo(script, message, onYes, script::end);
    }

    // =================================================================================================================

    public static NumberActionChain askMenu(MoeScript script, String prompt, String... menuItems) {
        var builder = new ScriptStringBuilder();
        builder.append(prompt).appendMenu(menuItems);

        script.setScriptResponse((t, a, o) -> {
            var min = 0;
            var max = menuItems.length - 1;
            var sel = ((Number)o).intValue();
            if (t != SpeakerType.ASKMENU || sel < min || sel > max) {
                script.end();
            } else {
                script.setScriptResponse(null);
                script.resume(t, a, o);
            }
        });

        log.debug("askMeu: {}", builder);
        return script::setScriptAction;
    }

    public static BasicActionChain askMenu(MoeScript script, String prompt, Tuple<String, BasicScriptAction>... options) {
        var builder = new ScriptStringBuilder();
        builder.append(prompt).appendMenu(Stream.of(options).map(t -> t.left).collect(Collectors.joining()));

        script.setScriptResponse((t, a, o) -> {
            var min = 0;
            var max = options.length - 1;
            var sel = ((Number)o).intValue();
            if (t != SpeakerType.ASKMENU || sel < min || sel > max) {
                script.end();
            } else {
                script.setScriptResponse(null);
                script.setScriptAction(options[sel].right);
                script.resume(t, a, o);
            }
        });

        log.debug("askMeu: {}", builder);

        return script::setScriptAction;
    }

    // =================================================================================================================
}
