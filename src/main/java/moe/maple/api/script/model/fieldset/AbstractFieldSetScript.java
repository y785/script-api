package moe.maple.api.script.model.fieldset;

import moe.maple.api.script.model.BaseScript;
import moe.maple.api.script.model.fieldset.events.FSUserEvent;
import moe.maple.api.script.model.object.FieldObject;
import moe.maple.api.script.model.object.field.DropObject;
import moe.maple.api.script.model.object.field.MobObject;
import moe.maple.api.script.model.object.user.UserObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractFieldSetScript extends BaseScript implements FieldSetScript {

    private static final Logger log = LoggerFactory.getLogger( AbstractFieldSetScript.class );

    protected List<FieldObject<?>> fields;

    public AbstractFieldSetScript() {
        this.fields = new LinkedList<>();
    }

    @Override
    public boolean containsField(int fieldId) {
        for (var field : fields)
            if (field.getId() == fieldId)
                return true;
        return false;
    }

    @Override
    public void onItemEnterField(DropObject<?, ?> dropObject) {
        log.debug("Item(Object: {}, Template: {}) has triggered the enter field({}) event.", dropObject.getObjectId(), dropObject.getItemId(), dropObject.getFieldId());
    }

    @Override
    public void onItemLeaveField(DropObject<?, ?> dropObject) {
        log.debug("Item(Object: {}, Template: {}) has triggered the leave field({}) event.", dropObject.getObjectId(), dropObject.getItemId(), dropObject.getFieldId());
    }

    @Override
    public void onMobDamaged(MobObject<?> mobObject, int damage) {
        log.debug("Mob(Object: {}, Template: {}) has triggered the damage event by receiving damage({}) on field({}).", mobObject.getObjectId(), mobObject.getId(), damage, mobObject.getFieldId());
    }

    @Override
    public void onMobDeath(MobObject<?> mobObject) {
        log.debug("Mob(Object: {}, Template: {}) has triggered the death event on field({}).", mobObject.getObjectId(), mobObject.getId(), mobObject.getFieldId());
    }

    @Override
    public void onUserDeath(UserObject<?> userObject) {
        log.debug("User({}) has triggered the death event.", userObject.getName());
    }

    @Override
    public void onUserDisconnect(UserObject<?> userObject) {
        log.debug("User({}) has triggered the disconnect event.", userObject.getName());
    }

    @Override
    public void onUserDisbandParty(UserObject<?> userObject) {
        log.debug("User({}) has triggered the disband party event.", userObject.getName());
    }

    @Override
    public void onUserLeaveParty(UserObject<?> userObject) {
        log.debug("User({}) has triggered the leave party event.", userObject.getName());
    }

    @Override
    public void onUserTransferField(UserObject<?> userObject, int from, int to) {
        log.debug("User({}) has triggered the transfer field event from({}) -> to({}).", userObject.getName(), from, to);
    }

    @Override
    public void update(long delta) {

    }
}
