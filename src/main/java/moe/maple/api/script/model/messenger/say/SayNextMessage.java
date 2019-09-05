package moe.maple.api.script.model.messenger.say;

import moe.maple.api.script.model.object.user.UserObject;

/**
 * Created on 9/4/2019.
 */
public class SayNextMessage extends SayMessage {
    public SayNextMessage(int speakerType, int speakerTemplate, int replaceTemplate, int parameters, String message) {
        super(speakerType, speakerTemplate, replaceTemplate, parameters, message);
    }

    @Override
    public void onMessage(SayMessenger messenger, UserObject userObject, boolean prev, boolean next) {
        super.onMessage(messenger, userObject, prev, true);
    }

}
