package moe.maple.api.script.model.action;

@FunctionalInterface
public interface NumberScriptAction extends ScriptAction {
    void act(Number object);
}
