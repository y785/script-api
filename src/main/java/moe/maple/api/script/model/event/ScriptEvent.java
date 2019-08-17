package moe.maple.api.script.model.event;

import moe.maple.api.script.model.MoeScript;

@FunctionalInterface
public interface ScriptEvent {
    void act(MoeScript script);
}
