package moe.maple.api.script.model.action;

@FunctionalInterface
public interface StringScriptAction extends ScriptAction {
    void act(String object);
}