package moe.maple.api.script.model.fieldset;

import moe.maple.api.script.model.MoeScript;
import moe.maple.api.script.model.fieldset.events.FSUserEvent;
import moe.maple.api.script.model.object.field.MobObject;
import moe.maple.api.script.model.object.user.UserObject;

public interface FieldSetScript extends MoeScript {

    int enter(UserObject<?> userObject, int fieldIndex);
    default int enter(UserObject<?> userObject) { return enter(userObject, 0); }

    void onDeath(UserObject<?> userObject);

    void onLeave(UserObject<?> userObject);

    void onMobDamaged(MobObject<?> mobObject, int damage);

    void onMobDeath(MobObject<?> mobObject);

    void onMobDeath(int mobId);

    void onTransfer(UserObject<?> userObject, int from, int to);

    void registerEnterEvent(FSUserEvent<?> event);
    
    void registerLeaveEvent(FSUserEvent<?> event);

    void registerTransferEvent(FSUserEvent<?> event);

    default void work() {
        update(System.currentTimeMillis());
    }

    /**
     * FieldSetScripts must be updated to pulse events and timers.
     * This should be called in a loop every x ms, with the current
     * delta time passed as a parameter.
     * @param delta  - Current milliseconds for update
     */
    void update(long delta);
}
