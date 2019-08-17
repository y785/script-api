package moe.maple.api.script.model;

import moe.maple.api.script.model.action.ScriptAction;
import moe.maple.api.script.model.response.ScriptResponse;
import moe.maple.api.script.model.event.ScriptEvent;
import moe.maple.api.script.model.type.SpeakerType;

public interface MoeScript {

    boolean isDone();

    void work();

    void start();
    void end();
    void restart();

    void resume(SpeakerType type, Number action, Object response);

    // =================================================================================================================

    void setScriptAction(ScriptAction action);
    void setScriptResponse(ScriptResponse response);

    void addStartEvent(ScriptEvent event);
    void addEndEvent(ScriptEvent event);

    // =================================================================================================================

}
