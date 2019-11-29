package moe.maple.api.script.model.fieldset.events;

import moe.maple.api.script.model.object.field.MobObject;

@FunctionalInterface
public interface FSMobEvent<Type> {
    void work(MobObject<Type> mobObject);
}
