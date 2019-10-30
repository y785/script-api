package moe.maple.api.script.model.fieldset.events;

import moe.maple.api.script.model.object.user.UserObject;

@FunctionalInterface
public interface FSUserEvent<Type> {
    void work(UserObject<Type> userObject);
}
