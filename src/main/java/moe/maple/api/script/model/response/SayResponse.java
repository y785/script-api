package moe.maple.api.script.model.response;

import moe.maple.api.script.model.MoeScript;
import moe.maple.api.script.model.ScriptAPI;
import moe.maple.api.script.model.messenger.say.SayMessenger;
import moe.maple.api.script.model.object.user.UserObject;
import moe.maple.api.script.model.type.ScriptMessageType;
import moe.maple.api.script.util.CursorIterator;
import moe.maple.api.script.util.ListCursorIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 8/26/2019.
 */
public class SayResponse implements ScriptResponse {


    private static final int END_CHAT = -1, PREV = 0, NEXT = 1;

    private final SayMessenger messenger;
    private final MoeScript script;
    private static final Logger log = LoggerFactory.getLogger(SayResponse.class);

    private final int speakerTemplateId, replaceTemplate, parameters;
    private final String message;

    public SayResponse(SayMessenger messenger, MoeScript script, int speakerTemplateId, int parameters, String message) {
        this.messenger = messenger;
        this.script = script;
        this.speakerTemplateId = speakerTemplateId;
        this.replaceTemplate = speakerTemplateId;//Need to discuss
        //if ((param & NPC_REPLACED_BY_NPC) > 0)
        //    packet.encode4(replacedId);
        //^ Usage in Say
        this.parameters = parameters;
        this.message = message;
    }

    @Override
    public void response(ScriptMessageType type, Number action, Object response) {
        if(type != ScriptMessageType.SAY) {//Wrong type, b-baka.
                script.end();
        } else {
            var chain = script.getSayChain();
            switch (action.intValue()) {
                case END_CHAT:
                    script.end();
                    break;
                case PREV:
                    if (chain.hasPrev()) {
                        var prev = chain.prev();
                        script.setScriptResponse(prev);
                        script.getUserObject().ifPresentOrElse(prev::send, () -> log.debug("User object isn't set, workflow is messy."));
                    } else {
                        log.warn("Tried to go back while on the first message? No! :(");
                        script.end();
                    }
                    break;
                case NEXT:
                    if (chain.hasNext()) {
                        var next = chain.next();
                        script.setScriptResponse(next);
                        script.getUserObject().ifPresentOrElse(next::send, () -> log.debug("User object isn't set, workflow is messy."));
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
        }
    }

    public void send(UserObject userObject) {
        var chain = script.getSayChain();
        messenger.send(userObject,speakerTemplateId, replaceTemplate, parameters,  message, chain.hasPrev(), shouldSayNext(chain));
    }

    private boolean shouldSayNext(CursorIterator<SayResponse> chain) {
        if(ScriptAPI.INSTANCE.getPreferences().shouldForceOkOnSay()) {
            return chain.cursor() < chain.size() - 1;
        } else return chain.hasNext();
    }

    @Override
    public String toString() {
        return message;//eh
    }
}
