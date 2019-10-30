package moe.maple.api.script.model.fieldset;

import moe.maple.api.script.model.BaseScript;
import moe.maple.api.script.model.fieldset.events.FSUserEvent;
import moe.maple.api.script.model.object.FieldObject;
import moe.maple.api.script.model.object.user.UserObject;

import java.util.LinkedList;
import java.util.List;

public class AbstractFieldSetScript extends BaseScript implements FieldSetScript {

    protected List<FieldObject<?>> fields;

    protected List<FSUserEvent> onUserDeathEvents;
    protected List<FSUserEvent> onUserEnterEvents;
    protected List<FSUserEvent> onUserLeaveEvents;
    protected List<FSUserEvent> onUserTransferEvents;

    public AbstractFieldSetScript() {
        this.fields = new LinkedList<>();
        this.onUserDeathEvents = new LinkedList<>();
        this.onUserEnterEvents = new LinkedList<>();
        this.onUserLeaveEvents = new LinkedList<>();
        this.onUserTransferEvents = new LinkedList<>();
    }

    @Override
    public void onDeath(UserObject<?> userObject) {
        onUserDeathEvents.forEach(ev -> ev.work(userObject));
    }

    @Override
    public int onEnter(UserObject<?> userObject, int fieldIndex) {
        return FSEnter.SCRIPT_ERROR;
    }

    @Override
    public void onLeave(UserObject<?> userObject) {
        onUserLeaveEvents.forEach(ev -> ev.work(userObject));
    }

    @Override
    public void registerEnterEvent(FSUserEvent<?> event) {
        onUserEnterEvents.add(event);
    }

    @Override
    public void registerLeaveEvent(FSUserEvent<?> event) {
        onUserLeaveEvents.remove(event);
    }

    @Override
    public void registerTransferEvent(FSUserEvent<?> event) {
        onUserTransferEvents.remove(event);
    }

    @Override
    public void onTransfer(UserObject<?> userObject, int from, int to) {
        onUserTransferEvents.forEach(ev -> ev.work(userObject));
    }

    @Override
    protected void work() {
        // FieldSets don't necessarily have a need for work.
    }

    @Override
    public void update(long delta) {

    }
}
