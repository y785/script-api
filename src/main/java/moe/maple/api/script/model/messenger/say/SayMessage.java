package moe.maple.api.script.model.messenger.say;

import moe.maple.api.script.model.type.ScriptMessageParameters;

/**
 * Created on 9/3/2019.
 */
public class SayMessage {
    private final int speakerType, speakerTemplate, replaceTemplate, parameters;
    private final String message;

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
}
