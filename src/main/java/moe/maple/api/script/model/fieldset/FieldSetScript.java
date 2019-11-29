package moe.maple.api.script.model.fieldset;

import moe.maple.api.script.model.MoeScript;
import moe.maple.api.script.model.fieldset.events.FSMobEvent;
import moe.maple.api.script.model.fieldset.events.FSUserEvent;
import moe.maple.api.script.model.object.PartyObject;
import moe.maple.api.script.model.object.field.DropObject;
import moe.maple.api.script.model.object.field.MobObject;
import moe.maple.api.script.model.object.user.UserObject;

/**
 * FieldSetScript is an implementation of {@code Events} from odin.
 * Nexon's implementation of FieldSet are hardcoded. Instead of doing this
 * server sided, scripting them is the way to go.
 * Unless specified otherwise, all of the events should be called AFTER the
 * event has happened.
 */
public interface FieldSetScript extends MoeScript {

    int enter(UserObject<?> userObject, int fieldIndex);
    default int enter(UserObject<?> userObject) { return enter(userObject, 0); }

    void onItemEnterField(DropObject<?, ?> dropObject);

    void onItemLeaveField(DropObject<?, ?> dropObject);

    void onMobDamaged(MobObject<?> mobObject, int damage);

    void onMobDeath(MobObject<?> mobObject);

    void onUserDeath(UserObject<?> userObject);

    void onUserDisconnect();

    void onUserDisbandParty();

    void onUserLeaveParty(UserObject<?> userObject);

    void onUserTransferField(UserObject<?> userObject, int from, int to);

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
