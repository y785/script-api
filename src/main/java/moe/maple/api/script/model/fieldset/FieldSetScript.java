package moe.maple.api.script.model.fieldset;

import moe.maple.api.script.model.MoeScript;
import moe.maple.api.script.model.fieldset.events.FSUserEvent;
import moe.maple.api.script.model.object.user.UserObject;

public interface FieldSetScript extends MoeScript {

    void onDeath(UserObject<?> userObject);

    int onEnter(UserObject<?> userObject, int fieldIndex);
    default int onEnter(UserObject<?> userObject) { return onEnter(userObject, 0); }

    void onLeave(UserObject<?> userObject);

    void onTransfer(UserObject<?> userObject, int from, int to);

    void registerEnterEvent(FSUserEvent<?> event);
    void registerLeaveEvent(FSUserEvent<?> event);

    void registerTransferEvent(FSUserEvent<?> event);

    /**
     * FieldSetScripts must be updated to pulse events and timers.
     * This should be called in a loop every x ms, with the current
     * delta time passed as a parameter.
     * @param delta  - Current milliseconds for update
     */
    void update(long delta);
}
