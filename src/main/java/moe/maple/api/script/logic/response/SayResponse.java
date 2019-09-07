package moe.maple.api.script.logic.response;
import moe.maple.api.script.logic.action.BasicScriptAction;
import moe.maple.api.script.logic.action.ObjectScriptAction;
import moe.maple.api.script.logic.chain.IntegerActionChain;
import moe.maple.api.script.logic.event.PolledScriptEvent;
import moe.maple.api.script.logic.ScriptAPI;
import moe.maple.api.script.model.messenger.say.SayMessenger;
import moe.maple.api.script.model.object.user.UserObject;
import moe.maple.api.script.model.MoeScript;
import moe.maple.api.script.model.type.ScriptMessageType;
import moe.maple.api.script.model.messenger.say.SayMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created on 8/26/2019.
 */
public class SayResponse implements ScriptResponse {


    public static final int END_CHAT = -1, PREV = 0, NEXT = 1;
    private static final Logger log = LoggerFactory.getLogger(SayResponse.class);

    private final SayMessenger messenger;
    private final MoeScript script;
    private final int index;
    private final SayMessage msg;
    private final SayResponse[] chain;

    public SayResponse(SayResponse[] chain, SayMessenger messenger, MoeScript script, int index, SayMessage ctx) {
        this.chain = chain;
        this.messenger = messenger;
        this.script = script;
        this.index = index;
        this.msg = ctx;
    }

    @Override
    public void response(Number type, Number action, Object response) {
        if (type.intValue() == ScriptMessageType.SAY) {
            switch (action.intValue()) {
                case END_CHAT:
                    script.end();
                    break;
                case PREV:
                    if (hasPrev()) {
                        onResponse(chain[index-1]);
                    } else {
                        log.warn("Tried to go back while on the first message? No! :(");
                        script.end();
                    }
                    break;
                case NEXT:
                    if (hasNext()) {
                        onResponse(chain[index+1]);
                    } else {
                        script.setScriptResponse(null);
                        script.resume(type, action, response);
                    }
                    break;
                default:
                    log.warn("Unhandled action({}) for {}", type, action);
                    script.end();
                    break;
            }
        } else {//Wrong type, b-baka.
            log.error("Invalid ScriptMessageType for SayResponse: {}", type);
            script.end();
        }
    }

    public void onResponse(SayResponse nextOrPrev) {
        script.setScriptResponse(nextOrPrev);
        script.addAfterRunEvent(
            (PolledScriptEvent)moe -> script.getUserObject().ifPresentOrElse(nextOrPrev::sendTo,
            () -> log.debug("User object isn't set, workflow is messy."))
        );
    }

    public void sendTo(UserObject userObject) {
        msg.onMessage(messenger, userObject, hasPrev(), hasNext() || scriptContinues());
    }

    private boolean scriptContinues() {
        return !ScriptAPI.INSTANCE.getPreferences().shouldForceOkOnSay() && script.isNextActionSet();
    }

    private boolean hasPrev() {
        return index > 0;//Could be safer here I guess.
    }

    private boolean hasNext() {
        return index + 1 < size();
    }

    private int size() {
        return chain.length;
    }

    @Override
    public String toString() {
        return msg.getMessage();//eh
    }

}
