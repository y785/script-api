package moe.maple.api.script.model.action;

@FunctionalInterface
public interface ObjectScriptAction<T> extends ScriptAction {
    void act(T object);
}