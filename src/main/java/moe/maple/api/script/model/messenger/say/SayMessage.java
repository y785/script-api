package moe.maple.api.script.model.messenger.say;

import moe.maple.api.script.model.object.user.UserObject;
import moe.maple.api.script.model.type.ScriptMessageParameters;

/**
 * Created on 9/3/2019.
 */
public class SayMessage {
    private final int speakerType;
    protected final int speakerTemplate;
    protected final int replaceTemplate;
    protected final int parameters;
    protected final String message;

    public SayMessage(int speakerType, int speakerTemplate, int replaceTemplate, int parameters, String message) {
        this.speakerType = speakerType;
        this.speakerTemplate = speakerTemplate;
        this.replaceTemplate = replaceTemplate;
        this.parameters = parameters;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getSpeakerTemplate() {
        return speakerTemplate;
    }

    public int getReplaceTemplate() {
        return replaceTemplate;
    }

    public int getParameters() {
        return parameters;
    }

    public int getSpeakerType() {
        return speakerType;
    }

    @Override
    public String toString() {
        return message;
    }
    
    public boolean allowsEsc() {
        return (this.parameters & ScriptMessageParameters.NO_ESC) == 0;
    }

    public boolean isFlipped() {
        return (this.parameters & ScriptMessageParameters.FLIP_IMAGE) != 0;
    }

    public void onMessage(SayMessenger messenger, UserObject userObject, boolean prev, boolean next) {
        messenger.send(userObject, speakerTemplate, replaceTemplate, parameters,  message, prev, next);
    }
}
