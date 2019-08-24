package moe.maple.api.script.model.object.user;

import moe.maple.api.script.model.object.ScriptObject;

/**
 * Created on 8/24/2019.
 * @param <T> your PartyMember implementation
 */
public interface PartyMemberObject<T> extends ScriptObject<T> {
    int getId();
    int getChannelId();
    int getLevel();
    int getJob();
    int getSubJob();
    String getName();
}
