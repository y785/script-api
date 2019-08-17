package moe.maple.api.script.model;

import moe.maple.api.script.model.action.BasicScriptAction;
import moe.maple.api.script.model.action.NumberScriptAction;
import moe.maple.api.script.model.action.ScriptAction;
import moe.maple.api.script.model.action.StringScriptAction;
import moe.maple.api.script.model.response.ScriptResponse;
import moe.maple.api.script.model.event.ScriptEvent;
import moe.maple.api.script.model.type.SpeakerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public abstract class FunScript implements MoeScript {

    private static final Logger log = LoggerFactory.getLogger( FunScript.class );

    protected ScriptAction nextAction;
    protected ScriptResponse nextResponse;
    protected LinkedList<ScriptEvent> startScriptEvents;
    protected LinkedList<ScriptEvent> endScriptEvents;

    public FunScript() {
        this.startScriptEvents = new LinkedList<>();
        this.endScriptEvents = new LinkedList<>();

    }

    @Override
    public abstract void work();

    @Override
    public void start() {
        this.startScriptEvents.forEach(event -> {
            event.act(this);
        });
        work();
    }

    @Override
    public void end() {
        this.endScriptEvents.forEach(event -> {
            event.act(this);
        });
    }

    @Override
    public void resume(SpeakerType type, Number action, Object response) {
        var act = nextAction;
        var resp = nextResponse;
        if (act != null || resp != null) {
            if (resp != null) {
                resp.response(type, action, response);
            } else {
                if (act instanceof BasicScriptAction) {
                    ((BasicScriptAction) act).act();
                } else if (act instanceof NumberScriptAction) {
                    ((NumberScriptAction) act).act((Number)response);
                } else if (act instanceof StringScriptAction) {
                    ((StringScriptAction) act).act((String)response);
                } else {
                    log.error("Couldn't process action: {}", act.getClass().getSimpleName());
                    this.end();
                }
            }
        } else {
            end();
        }
    }

    @Override
    public void setScriptAction(ScriptAction action) {
        this.nextAction = action;
    }

    @Override
    public void setScriptResponse(ScriptResponse response) {
        this.nextResponse = response;
    }

    @Override
    public void addStartEvent(ScriptEvent event) {
        this.startScriptEvents.add(event);
    }

    @Override
    public void addEndEvent(ScriptEvent event) {
        this.endScriptEvents.add(event);
    }

}
